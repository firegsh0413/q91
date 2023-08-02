package com.icchance.q91.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.icchance.q91.entity.dto.GatewayDTO;
import com.icchance.q91.entity.model.Gateway;

import java.util.List;
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
     * @param account 帳號
     * @return java.util.List<com.icchance.q91.entity.model.Gateway>
     * @author 6687353
     * @since 2023/7/31 09:46:28
     */
    List<Gateway> getGatewayList(String account);

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
}
