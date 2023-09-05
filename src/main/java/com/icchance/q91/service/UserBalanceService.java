package com.icchance.q91.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.icchance.q91.entity.dto.UserBalanceDTO;
import com.icchance.q91.entity.model.UserBalance;
import com.icchance.q91.entity.vo.UserBalanceVO;

/**
 * <p>
 * 會員錢包服務類介面
 * </p>
 * @author 6687353
 * @since 2023/8/23 13:22:59
 */
public interface UserBalanceService extends IService<UserBalance> {

    /**
     * <p>
     * 取得錢包資訊
     * </p>
     * @param userId 用戶uid
     * @return com.icchance.q91.entity.vo.UserBalanceVO
     * @author 6687353
     * @since 2023/8/23 15:19:38
     */
    UserBalanceVO getInfo(Integer userId);

    /**
     * <p>
     * 取得錢包資訊
     * </p>
     * @param userId 用戶uid
     * @return com.icchance.q91.entity.model.UserBalance
     * @author 6687353
     * @since 2023/8/24 11:17:26
     */
    UserBalance getEntity(Integer userId);

    /**
     * <p>
     * 新增錢包資訊
     * </p>
     * @param userBalanceDTO UserBalanceDTO
     * @return int
     * @author 6687353
     * @since 2023/8/23 15:03:28
     */
    int addEntity(UserBalanceDTO userBalanceDTO);

    /**
     * <p>
     * 更新錢包資訊
     * </p>
     * @param userBalanceDTO UserBalanceDTO
     * @return int
     * @author 6687353
     * @since 2023/8/23 15:03:44
     */
    int updateInfo(UserBalanceDTO userBalanceDTO);

    /**
     * <p>
     * 更新錢包資訊
     * </p>
     * @param userBalance UserBalance
     * @return int
     * @author 6687353
     * @since 2023/8/24 13:18:45
     */
    int updateEntity(UserBalance userBalance);

    void update(UserBalanceDTO userBalanceDTO);
}
