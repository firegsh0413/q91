package com.icchance.q91.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.icchance.q91.entity.dto.GatewayDTO;
import com.icchance.q91.entity.model.Gateway;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 會員收付款服務 介面
 * </p>
 * @author 6687353
 * @since 2023/7/31 09:46:46
 */
public interface GatewayService extends IService<Gateway> {

    /**
     * <p>
     * 取得會員收付款訊息
     * </p>
     * @param userId 用戶ID
     * @return java.util.List<com.icchance.q91.entity.model.Gateway>
     * @author 6687353
     * @since 2023/7/31 09:46:28
     */
    List<Gateway> getGatewayList(Integer userId);

    /**
     * <p>
     * 增加會員收付款訊息
     * </p>
     * @param gatewayDTO GatewayDTO
     * @author 6687353
     * @since 2023/7/31 09:47:13
     */
    void createGateway(GatewayDTO gatewayDTO);

    /**
     * <p>
     * 刪除會員收付款訊息
     * </p>
     * @param userId 使用者id
     * @param id 收付款資訊ID
     * @author 6687353
     * @since 2023/7/31 11:05:19
     */
    void deleteGateway(Integer userId, Integer id);

    /**
     * <p>
     * 取得可用收款類型列表
     * </p>
     * @param userId 用戶ID
     * @return java.util.List<java.lang.Integer>
     * @author 6687353
     * @since 2023/8/4 16:23:55
     */
    Set<Integer> getAvailableGateway(Integer userId);
}
