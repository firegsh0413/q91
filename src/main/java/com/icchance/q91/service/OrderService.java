package com.icchance.q91.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.icchance.q91.entity.dto.OrderDTO;
import com.icchance.q91.entity.model.Order;
import com.icchance.q91.entity.vo.OrderVO;

import java.util.List;

/**
 * <p>
 * 訂單服務類
 * </p>
 * @author 6687353
 * @since 2023/8/22 14:02:54
 */
public interface OrderService extends IService<Order> {

    /**
     * <p>
     * 查詢會員訂單列表
     * </p>
     * @param userId 用戶uid
     * @return java.util.List<com.icchance.q91.entity.vo.OrderVO>
     * @author 6687353
     * @since 2023/8/22 14:02:29
     */
    List<OrderVO> getList(Integer userId);

    /**
     * <p>
     * 查詢會員訂單詳情
     * </p>
     * @param userId 用戶uid
     * @param orderId 訂單uid
     * @return com.icchance.q91.entity.vo.OrderVO
     * @author 6687353
     * @since 2023/8/22 14:11:30
     */
    OrderVO getDetail(Integer userId, Integer orderId);

    /**
     * <p>
     * 建立訂單
     * </p>
     * @param orderDTO OrderDTO
     * @return com.icchance.q91.entity.model.Order
     * @author 6687353
     * @since 2023/8/25 18:02:57
     */
    Order create(OrderDTO orderDTO);

    /**
     * <p>
     * 上傳支付憑證
     * </p>
     * @param userId 用戶uid
     * @param orderId 訂單uid
     * @param cert 憑證base64
     * @return int
     * @author 6687353
     * @since 2023/8/22 15:56:31
     */
    int uploadCert(Integer userId, Integer orderId, String cert);

    /**
     * <p>
     * 更新訂單
     * </p>
     * @param orderDTO OrderDTO
     * @return int
     * @author 6687353
     * @since 2023/8/25 18:54:13
     */
    int update(OrderDTO orderDTO);

    /**
     * <p>
     * 更新訂單狀態
     * </p>
     * @param orderId 訂單uid
     * @param status  狀態代碼
     * @return int
     * @author 6687353
     * @since 2023/9/26 10:14:01
     */
    int updateStatus(Integer orderId, Integer status);

}
