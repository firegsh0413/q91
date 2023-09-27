package com.icchance.q91.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.icchance.q91.common.constant.OrderConstant;
import com.icchance.q91.common.constant.ResultCode;
import com.icchance.q91.common.error.ServiceException;
import com.icchance.q91.entity.dto.BaseDTO;
import com.icchance.q91.entity.dto.GatewayDTO;
import com.icchance.q91.entity.dto.PendingOrderDTO;
import com.icchance.q91.entity.dto.TransactionDTO;
import com.icchance.q91.entity.model.*;
import com.icchance.q91.entity.vo.OrderVO;
import com.icchance.q91.entity.vo.PendingOrderVO;
import com.icchance.q91.nsq.Producer;
import com.icchance.q91.service.*;
import com.icchance.q91.util.JwtUtil;
import com.icchance.q91.util.RedisKeyUtil;
import com.icchance.q91.util.RedissonLockUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 交易服務類實作
 * </p>
 * @author 6687353
 * @since 2023/8/18 16:58:11
 */
@Service
public class TransactionServiceImpl implements TransactionService {

    //private final UserService userService;
    private final GatewayService gatewayService;
    private final PendingOrderService pendingOrderService;
    private final OrderService orderService;
    private final OrderRecordService orderRecordService;
    private final UserBalanceService userBalanceService;
    private final JwtUtil jwtUtil;
    private final Producer producer;
    public TransactionServiceImpl(GatewayService gatewayService, PendingOrderService pendingOrderService,
                                  OrderService orderService, OrderRecordService orderRecordService, UserBalanceService userBalanceService,
                                  JwtUtil jwtUtil, Producer producer) {
        //this.userService = userService;
        this.gatewayService = gatewayService;
        this.pendingOrderService = pendingOrderService;
        this.orderService = orderService;
        this.orderRecordService = orderRecordService;
        this.userBalanceService = userBalanceService;
        this.jwtUtil = jwtUtil;
        this.producer = producer;
    }

    /**
     * <p>
     * 取得會員掛單訊息
     * </p>
     * @param baseDTO BaseDTO
     * @return java.util.List<com.icchance.q91.entity.vo.PendingOrderVO>
     * @author 6687353
     * @since 2023/8/18 16:58:29
     */
    @Override
    public List<PendingOrderVO> getPendingOrderList(BaseDTO baseDTO) {
        Integer userId = jwtUtil.parseUserId(baseDTO.getToken());
        List<PendingOrderVO> list = pendingOrderService.getList(userId);
        if (CollectionUtils.isEmpty(list)) {
            throw new ServiceException(ResultCode.NO_ORDER_EXIST);
        }
        return list;
    }

    /**
     * <p>
     * 取得會員掛單（我的賣單）詳細訊息
     * </p>
     * @param transactionDTO TransactionDTO
     * @return com.icchance.q91.entity.vo.PendingOrderVO
     * @author 6687353
     * @since 2023/8/18 16:58:42
     */
    @Override
    public PendingOrderVO getPendingOrderDetail(TransactionDTO transactionDTO) {
        Integer userId = jwtUtil.parseUserId(transactionDTO.getToken());
        PendingOrderVO detail = pendingOrderService.getDetail(userId, transactionDTO.getId());
        if (Objects.isNull(detail)) {
            throw new ServiceException(ResultCode.NO_ORDER_EXIST);
        }
        return detail;
    }

    /**
     * <p>
     * 取消掛單
     * </p>
     * @param transactionDTO TransactionDTO
     * @author 6687353
     * @since 2023/8/22 09:33:44
     */
    @Override
    public void cancelPendingOrder(TransactionDTO transactionDTO) {
        Integer userId = jwtUtil.parseUserId(transactionDTO.getToken());
        PendingOrderVO pendingOrderVO = pendingOrderService.getDetail(userId, transactionDTO.getId());
        if (Objects.isNull(pendingOrderVO)) {
            throw new ServiceException(ResultCode.NO_ORDER_EXIST);
        }
        // 掛單狀態為待確認或出售中才可取消
        if (!OrderConstant.PendingOrderStatusEnum.ON_SALE.code.equals(pendingOrderVO.getStatus())
            && !OrderConstant.PendingOrderStatusEnum.ON_CHECK.code.equals(pendingOrderVO.getStatus())) {
            throw new ServiceException(ResultCode.ORDER_STATUS_ERROR);
        }
        BigDecimal amount = pendingOrderVO.getAmount();
        pendingOrderService.updateStatus(transactionDTO.getId(), OrderConstant.PendingOrderStatusEnum.CANCEL.code);
        // 取消掛單 賣方錢包額度要回歸
        // 1.掛單狀態為掛賣中 賣單餘額->可售數量
        UserBalance sellerBalance = userBalanceService.getEntity(userId);
        if (OrderConstant.PendingOrderStatusEnum.ON_SALE.code.equals(pendingOrderVO.getStatus())
                || OrderConstant.PendingOrderStatusEnum.ON_CHECK.code.equals(pendingOrderVO.getStatus())) {
            sellerBalance.setPendingBalance(sellerBalance.getPendingBalance().subtract(amount));
            sellerBalance.setAvailableAmount(sellerBalance.getAvailableAmount().add(amount));
        }
        // 2.掛單狀態為有人下訂 交易中->可售數量
        else {
            sellerBalance.setTradingAmount(sellerBalance.getTradingAmount().subtract(amount));
            sellerBalance.setAvailableAmount(sellerBalance.getAvailableAmount().add(amount));
        }
        userBalanceService.updateEntity(sellerBalance);
        // 如果有買方 已下訂訂單取消
        if (Objects.nonNull(pendingOrderVO.getBuyerId())) {
            OrderVO orderVO = orderService.getDetail(pendingOrderVO.getBuyerId(), pendingOrderVO.getOrderId());
            orderService.updateStatus(pendingOrderVO.getOrderId(), OrderConstant.OrderStatusEnum.CANCEL.code);
            // 如果訂單狀態在等待上傳支付之後，錢包額度 交易中額度扣除
            if (OrderConstant.OrderStatusEnum.PAY_UPLOAD.code <= orderVO.getStatus()) {
                UserBalance buyerBalance = userBalanceService.getEntity(pendingOrderVO.getBuyerId());
                buyerBalance.setTradingAmount(buyerBalance.getTradingAmount().subtract(orderVO.getAmount()));
                userBalanceService.updateEntity(buyerBalance);
            }
        }
        // 解鎖
        if (RedissonLockUtil.isLocked(RedisKeyUtil.generateKey(pendingOrderVO.getId()))) {
            RedissonLockUtil.unlock(RedisKeyUtil.generateKey(pendingOrderVO.getId()));
        }
    }

    /**
     * <p>
     * 確認掛單已被下訂
     * （賣單第一階段狀態：買家已下單請賣家確認）
     * </p>
     * @param transactionDTO TransactionDTO
     * @author 6687353
     * @since 2023/8/22 11:25:58
     */
    @Override
    public void checkPendingOrder(TransactionDTO transactionDTO) {
        Integer userId = jwtUtil.parseUserId(transactionDTO.getToken());
        PendingOrderVO pendingOrderVO = pendingOrderService.getDetail(userId, transactionDTO.getId());
        if (Objects.isNull(pendingOrderVO)) {
            throw new ServiceException(ResultCode.NO_ORDER_EXIST);
        }
        // 掛單狀態為待確認才往下處理
        if (!OrderConstant.PendingOrderStatusEnum.ON_CHECK.code.equals(pendingOrderVO.getStatus())) {
            throw new ServiceException(ResultCode.ORDER_STATUS_ERROR);
        }
        pendingOrderService.check(userId, transactionDTO.getId());
        // 更新訂單狀態
        Order order = Order.builder()
                .id(pendingOrderVO.getOrderId())
                .status(OrderConstant.OrderStatusEnum.PAY_UPLOAD.code)
                .updateTime(LocalDateTime.now())
                .cutOffTime(LocalDateTime.now().plusMinutes(10)).build();
        orderService.updateById(order);
        // TODO 待優化
        OrderVO orderVO = orderService.getDetail(pendingOrderVO.getBuyerId(), pendingOrderVO.getOrderId());
        // 推送訂單等待上傳支付憑證倒數資訊
        producer.checkOrder(JSON.toJSONString(transactionDTO));
        // 掛單有人下訂 用戶錢包額度 賣單餘額->交易中
        UserBalance sellerBalance = userBalanceService.getEntity(userId);
        sellerBalance.setPendingBalance(sellerBalance.getPendingBalance().subtract(orderVO.getAmount()));
        sellerBalance.setTradingAmount(sellerBalance.getTradingAmount().add(orderVO.getAmount()));
        userBalanceService.updateEntity(sellerBalance);
        // 買方錢包額度 交易中+
        UserBalance buyerBalance = userBalanceService.getEntity(pendingOrderVO.getBuyerId());
        buyerBalance.setTradingAmount(buyerBalance.getTradingAmount().add(orderVO.getAmount()));
        userBalanceService.updateEntity(buyerBalance);
    }

    /**
     * <p>
     * 核實掛單並打幣
     * （賣單第二階段狀態：買家已付款請賣家核實並打幣）
     * </p>
     * @param transactionDTO TransactionDTO
     * @author 6687353
     * @since 2023/8/22 11:49:56
     */
    @Override
    public void verifyPendingOrder(TransactionDTO transactionDTO) {
        Integer userId = jwtUtil.parseUserId(transactionDTO.getToken());
        verifyPendingOrder(userId, transactionDTO.getId());
    }
    @Override
    public void verifyPendingOrder(Integer userId, Integer orderId) {
        PendingOrderVO pendingOrderVO = pendingOrderService.getDetail(userId, orderId);
        if (Objects.isNull(pendingOrderVO)) {
            throw new ServiceException(ResultCode.NO_ORDER_EXIST);
        }
        // 待優化
        OrderVO orderVO = orderService.getDetail(pendingOrderVO.getBuyerId(), pendingOrderVO.getOrderId());
        BigDecimal amount = orderVO.getAmount();
        // 判斷掛單狀態是否為交易中
        if (!OrderConstant.PendingOrderStatusEnum.ON_TRANSACTION.code.equals(pendingOrderVO.getStatus())) {
            throw new ServiceException(ResultCode.ORDER_STATUS_ERROR);
        }
        // 掛單有人下訂 用戶錢包額度 從交易中扣除
        UserBalance sellerBalance = userBalanceService.getEntity(userId);

        sellerBalance.setTradingAmount(sellerBalance.getTradingAmount().subtract(amount));
        userBalanceService.updateEntity(sellerBalance);
        // 打幣給買家 交易中->可售數量/錢包餘額
        UserBalance buyerBalance = userBalanceService.getEntity(pendingOrderVO.getBuyerId());
        buyerBalance.setTradingAmount(buyerBalance.getTradingAmount().subtract(amount));
        buyerBalance.setBalance(buyerBalance.getBalance().add(amount));
        buyerBalance.setAvailableAmount(buyerBalance.getAvailableAmount().add(amount));
        userBalanceService.updateEntity(buyerBalance);
        pendingOrderService.updateStatus(orderId, OrderConstant.PendingOrderStatusEnum.FINISH.code);
        // 更新訂單狀態
        orderService.updateStatus(pendingOrderVO.getOrderId(), OrderConstant.OrderStatusEnum.FINISH.code);
        // 解鎖
        if (RedissonLockUtil.isLocked(RedisKeyUtil.generateKey(pendingOrderVO.getId()))) {
            RedissonLockUtil.unlock(RedisKeyUtil.generateKey(pendingOrderVO.getId()));
        }
    }

    /**
     * <p>
     * 查詢會員訂單列表
     * </p>
     * @param baseDTO BaseDTO
     * @return java.util.List<com.icchance.q91.entity.vo.OrderVO>
     * @author 6687353
     * @since 2023/8/22 13:10:09
     */
    @Override
    public List<OrderVO> getOrderList(BaseDTO baseDTO) {
        Integer userId = jwtUtil.parseUserId(baseDTO.getToken());
        List<OrderVO> list = orderService.getList(userId);
        if (CollectionUtils.isEmpty(list)) {
            throw new ServiceException(ResultCode.NO_ORDER_EXIST);
        }
        return list;
    }

    /**
     * <p>
     * 查詢會員訂單詳情
     * </p>
     * @param transactionDTO TransactionDTO
     * @return com.icchance.q91.entity.vo.OrderVO
     * @author 6687353
     * @since 2023/8/11 11:55:14
     */
    @Override
    public OrderVO getOrderDetail(TransactionDTO transactionDTO) {
        Integer userId = jwtUtil.parseUserId(transactionDTO.getToken());
        OrderVO orderVO = orderService.getDetail(userId, transactionDTO.getId());
        if (Objects.isNull(orderVO)) {
            throw new ServiceException(ResultCode.NO_ORDER_EXIST);
        }
        return orderVO;
    }

    /**
     * <p>
     * 取消訂單
     * </p>
     * @param transactionDTO TransactionDTO
     * @author 6687353
     * @since 2023/8/22 11:03:16
     */
    @Override
    public void cancelOrder(TransactionDTO transactionDTO) {
        Integer userId = jwtUtil.parseUserId(transactionDTO.getToken());
        cancelOrder(userId, transactionDTO.getId());
    }

    @Override
    public void cancelOrder(Integer userId, Integer orderId) {
        OrderVO orderVO = orderService.getDetail(userId, orderId);
        if (Objects.isNull(orderVO)) {
            throw new ServiceException(ResultCode.NO_ORDER_EXIST);
        }
        // 訂單狀態為上傳支付或待確認
        if (!OrderConstant.OrderStatusEnum.ON_CHECK.code.equals(orderVO.getStatus())
            && !OrderConstant.OrderStatusEnum.PAY_UPLOAD.code.equals(orderVO.getStatus())) {
            throw new ServiceException(ResultCode.ORDER_STATUS_ERROR);
        }
        orderService.updateStatus(orderId, OrderConstant.OrderStatusEnum.CANCEL.code);
        // 取消訂單 買方錢包額度 交易中移除額度
        UserBalance buyerBalance = userBalanceService.getEntity(userId);
        buyerBalance.setTradingAmount(buyerBalance.getTradingAmount().subtract(orderVO.getAmount()));
        userBalanceService.updateEntity(buyerBalance);
        // 賣方掛單更新
        // TODO 訂單取消 對應掛單一起取消
        pendingOrderService.updateStatus(orderVO.getPendingOrderId(), OrderConstant.PendingOrderStatusEnum.CANCEL.code);
        // 錢包額度 交易中->賣單餘額
        UserBalance sellBalance = userBalanceService.getEntity(orderVO.getSellerId());
        sellBalance.setTradingAmount(sellBalance.getTradingAmount().subtract(orderVO.getAmount()));
        sellBalance.setPendingBalance(sellBalance.getPendingBalance().add(orderVO.getAmount()));
        userBalanceService.updateEntity(sellBalance);
        // 解鎖
        if (RedissonLockUtil.isLocked(RedisKeyUtil.generateKey(orderVO.getPendingOrderId()))) {
            RedissonLockUtil.unlock(RedisKeyUtil.generateKey(orderVO.getPendingOrderId()));
        }
    }

    /**
     * <p>
     * 會員錢包紀錄訊息
     * </p>
     * @param baseDTO BaseDTO
     * @return java.util.List<com.icchance.q91.entity.model.OrderRecord>
     * @author 6687353
     * @since 2023/8/22 14:38:13
     */
    @Override
    public List<OrderRecord> getRecord(BaseDTO baseDTO) {
        Integer userId = jwtUtil.parseUserId(baseDTO.getToken());
        List<OrderRecord> list = orderRecordService.list(Wrappers.<OrderRecord>lambdaQuery().eq(OrderRecord::getUserId, userId));
        if (CollectionUtils.isEmpty(list)) {
            throw new ServiceException(ResultCode.NO_DATA);
        }
        return list;
    }

    /**
     * <p>
     * 取得會員收付款訊息
     * </p>
     *
     * @param baseDTO BaseDTO
     * @return java.util.List<com.icchance.q91.entity.model.Gateway>
     * @author 6687353
     * @since 2023/7/31 13:26:44
     */
    @Override
    public List<Gateway> getGatewayList(BaseDTO baseDTO) {
        Integer userId = jwtUtil.parseUserId(baseDTO.getToken());
        List<Gateway> gatewayList = gatewayService.getGatewayList(userId);
        if (CollectionUtils.isEmpty(gatewayList)) {
            throw new ServiceException(ResultCode.NO_DATA);
        }
        return gatewayList;
    }

    /**
     * <p>
     * 增加會員收付款訊息
     * </p>
     *
     * @param gatewayDTO GatewayDTO
     * @author 6687353
     * @since 2023/7/31 13:27:53
     */
    @Override
    public void createGateway(GatewayDTO gatewayDTO) {
        Integer userId = jwtUtil.parseUserId(gatewayDTO.getToken());
        gatewayDTO.setUserId(userId);
        gatewayService.createGateway(gatewayDTO);
    }

    /**
     * <p>
     * 刪除會員收付款訊息
     * </p>
     *
     * @param transactionDTO TransactionDTO
     * @author 6687353
     * @since 2023/7/31 13:29:00
     */
    @Override
    public void deleteGateway(TransactionDTO transactionDTO) {
        Integer userId = jwtUtil.parseUserId(transactionDTO.getToken());
        // 檢查是否有使用此交易渠道的掛單
        List<PendingOrder> pendingOrderList = pendingOrderService.getPendingOrderList(userId, transactionDTO.getId());
        if (CollectionUtils.isNotEmpty(pendingOrderList)) {
            throw new ServiceException(ResultCode.ORDER_IN_TRANSACTION);
        }
        gatewayService.deleteGateway(userId, transactionDTO.getId());
    }

    /**
     * <p>
     * 上傳支付憑證
     * </p>
     * @param transactionDTO TransactionDTO
     * @return com.icchance.q91.entity.dto.TransactionDTO
     * @author 6687353
     * @since 2023/8/22 15:48:13
     */
    @Override
    public TransactionDTO verifyOrder(TransactionDTO transactionDTO) {
        Integer userId = jwtUtil.parseUserId(transactionDTO.getToken());
        OrderVO orderVO = orderService.getDetail(userId, transactionDTO.getId());
        if (Objects.isNull(orderVO)) {
            throw new ServiceException(ResultCode.NO_ORDER_EXIST);
        }
        // 訂單狀態為上傳支付才處理
        if (!OrderConstant.OrderStatusEnum.PAY_UPLOAD.code.equals(orderVO.getStatus())) {
            throw new ServiceException(ResultCode.ORDER_STATUS_ERROR);
        }
        orderService.uploadCert(userId, orderVO.getId(), transactionDTO.getCert());
        // 已經支付
        producer.uploadCert(JSON.toJSONString(transactionDTO));
        // 訂單狀態更新
        PendingOrderDTO pendingOrderDTO = PendingOrderDTO.builder()
                .id(orderVO.getPendingOrderId())
                .status(OrderConstant.PendingOrderStatusEnum.ON_TRANSACTION.code)
                .cert(transactionDTO.getCert())
                .build();
        pendingOrderService.update(pendingOrderDTO);
        return transactionDTO;
    }

    /**
     * <p>
     * 手動打款
     * </p>
     * @param transactionDTO TransactionDTO
     * @author 6687353
     * @since 2023/9/25 11:13:24
     */
    @Override
    public void manualPay(TransactionDTO transactionDTO) {
        Integer userId = jwtUtil.parseUserId(transactionDTO.getToken());
        // 買家進行申訴要求手動打款
        OrderVO orderVO = orderService.getDetail(userId, transactionDTO.getId());
        if (Objects.isNull(orderVO)) {
            throw new ServiceException(ResultCode.NO_ORDER_EXIST);
        }
        // 判斷訂單狀態是否為申訴中
        if (!OrderConstant.OrderStatusEnum.APPEAL.code.equals(orderVO.getStatus())) {
            throw new ServiceException(ResultCode.ORDER_STATUS_ERROR);
        }
        BigDecimal amount = orderVO.getAmount();
        PendingOrderVO pendingOrderVO = pendingOrderService.getDetail(orderVO.getSellerId(), orderVO.getPendingOrderId());
        // 掛單有人下訂 用戶錢包額度 從交易中扣除
        UserBalance sellerBalance = userBalanceService.getEntity(orderVO.getSellerId());
        sellerBalance.setTradingAmount(sellerBalance.getTradingAmount().subtract(amount));
        userBalanceService.updateEntity(sellerBalance);
        // 打幣給買家 交易中->可售數量/錢包餘額
        UserBalance buyerBalance = userBalanceService.getEntity(userId);
        buyerBalance.setTradingAmount(buyerBalance.getTradingAmount().subtract(amount));
        buyerBalance.setBalance(buyerBalance.getBalance().add(amount));
        buyerBalance.setAvailableAmount(buyerBalance.getAvailableAmount().add(amount));
        userBalanceService.updateEntity(buyerBalance);
        // 掛單狀態為手動打款
        pendingOrderService.updateStatus(pendingOrderVO.getId(), OrderConstant.PendingOrderStatusEnum.MANUAL_PAY.code);
        // 更新訂單狀態
        orderService.updateStatus(transactionDTO.getId(), OrderConstant.OrderStatusEnum.FINISH.code);
        // 解鎖
        if (RedissonLockUtil.isLocked(RedisKeyUtil.generateKey(pendingOrderVO.getId()))) {
            RedissonLockUtil.unlock(RedisKeyUtil.generateKey(pendingOrderVO.getId()));
        }
    }

    /**
     * <p>
     * 申訴失敗，客服取消訂單
     * </p>
     * @param transactionDTO TransactionDTO
     * @author 6687353
     * @since 2023/9/26 11:23:27
     */
    @Override
    public void appealFail(TransactionDTO transactionDTO) {
        Integer userId = jwtUtil.parseUserId(transactionDTO.getToken());
        OrderVO orderVO = orderService.getDetail(userId, transactionDTO.getId());
        if (Objects.isNull(orderVO)) {
            throw new ServiceException(ResultCode.NO_ORDER_EXIST);
        }
        // 訂單狀態為申訴中
        if (!OrderConstant.OrderStatusEnum.APPEAL.code.equals(orderVO.getStatus())) {
            throw new ServiceException(ResultCode.ORDER_STATUS_ERROR);
        }
        orderService.updateStatus(orderVO.getId(), OrderConstant.OrderStatusEnum.CANCEL.code);
        // 取消訂單 買方錢包額度 交易中移除額度
        UserBalance buyerBalance = userBalanceService.getEntity(userId);
        buyerBalance.setTradingAmount(buyerBalance.getTradingAmount().subtract(orderVO.getAmount()));
        userBalanceService.updateEntity(buyerBalance);
        // 賣方掛單更新
        // TODO 訂單取消 對應掛單一起取消
        pendingOrderService.updateStatus(orderVO.getPendingOrderId(), OrderConstant.PendingOrderStatusEnum.CANCEL.code);
        // 錢包額度 交易中->賣單餘額
        UserBalance sellBalance = userBalanceService.getEntity(orderVO.getSellerId());
        sellBalance.setTradingAmount(sellBalance.getTradingAmount().subtract(orderVO.getAmount()));
        sellBalance.setPendingBalance(sellBalance.getPendingBalance().add(orderVO.getAmount()));
        userBalanceService.updateEntity(sellBalance);
        // 解鎖
        if (RedissonLockUtil.isLocked(RedisKeyUtil.generateKey(orderVO.getPendingOrderId()))) {
            RedissonLockUtil.unlock(RedisKeyUtil.generateKey(orderVO.getPendingOrderId()));
        }
    }

}
