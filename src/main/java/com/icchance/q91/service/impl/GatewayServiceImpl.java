package com.icchance.q91.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.icchance.q91.dao.FakeGatewayDB;
import com.icchance.q91.entity.dto.GatewayDTO;
import com.icchance.q91.entity.model.Gateway;
import com.icchance.q91.mapper.GatewayMapper;
import com.icchance.q91.service.GatewayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 會員收付款服務 介面實作
 * </p>
 * @author 6687353
 * @since 2023/7/31 09:46:46
 */
@Slf4j
@Service
public class GatewayServiceImpl extends ServiceImpl<GatewayMapper, Gateway> implements GatewayService {

    private final FakeGatewayDB fakeGatewayDB;
    public GatewayServiceImpl(FakeGatewayDB fakeGatewayDB) {
        this.fakeGatewayDB = fakeGatewayDB;
    }
    /**
     * <p>
     * 取得會員收付款訊息
     * </p>
     *
     * @param userId 用戶ID
     * @return java.util.List<com.icchance.q91.entity.model.Gateway>
     * @author 6687353
     * @since 2023/7/31 09:46:28
     */
    @Override
    public List<Gateway> getGatewayList(Integer userId) {
        //return this.list(Wrappers.<Gateway>lambdaQuery().eq(Gateway::getAccount, account));
        return fakeGatewayDB.getList();
    }

    /**
     * <p>
     * 增加會員收付款訊息
     * </p>
     *
     * @param gatewayDTO GatewayDTO
     * @author 6687353
     * @since 2023/7/31 09:47:13
     */
    @Override
    public void createGateway(GatewayDTO gatewayDTO) {
        Gateway gateway = new Gateway();
        BeanUtils.copyProperties(gatewayDTO, gateway);
        this.save(gateway);
    }

    /**
     * <p>
     * 刪除會員收付款訊息
     * </p>
     *
     * @param userId 使用者id
     * @param id 收付款資訊ID
     * @author 6687353
     * @since 2023/7/31 11:05:19
     */
    @Override
    public void deleteGateway(Integer userId, Integer id) {
        Gateway gateway = this.getById(id);
        if (userId.equals(gateway.getUserId())) {
            this.removeById(id);
        }
    }

    /**
     * <p>
     * 取得可用收款類型列表
     * </p>
     *
     * @param userId 用戶ID
     * @return java.util.List<java.lang.Integer>
     * @author 6687353
     * @since 2023/8/4 16:23:55
     */
    @Override
    public Set<Integer> getAvailableGateway(Integer userId) {
        List<Gateway> availableGateway = this.getGatewayList(userId);
        return Optional.ofNullable(availableGateway).map(set -> set.stream().map(Gateway::getType).collect(Collectors.toSet())).orElse(null);
    }
}
