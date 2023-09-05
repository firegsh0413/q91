package com.icchance.q91.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.icchance.q91.entity.dto.UserBalanceDTO;
import com.icchance.q91.entity.model.UserBalance;
import com.icchance.q91.entity.vo.UserBalanceVO;
import com.icchance.q91.mapper.UserBalanceMapper;
import com.icchance.q91.service.UserBalanceService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * 會員錢包服務類實作
 * </p>
 * @author 6687353
 * @since 2023/8/23 13:23:16
 */
@Service
public class UserBalanceServiceImpl extends ServiceImpl<UserBalanceMapper, UserBalance> implements UserBalanceService {

    private final UserBalanceMapper userBalanceMapper;
    public UserBalanceServiceImpl (UserBalanceMapper userBalanceMapper) {
        this.userBalanceMapper = userBalanceMapper;
    }

    /**
     * <p>
     * 取得錢包資訊
     * </p>
     * @param userId 用戶uid
     * @return com.icchance.q91.entity.vo.UserBalanceVO
     * @author 6687353
     * @since 2023/8/23 15:19:38
     */
    @Override
    public UserBalanceVO getInfo(Integer userId) {
        return userBalanceMapper.getUserBalance(userId);
    }

    /**
     * <p>
     * 取得錢包資訊
     * </p>
     * @param userId 用戶uid
     * @return com.icchance.q91.entity.model.UserBalance
     * @author 6687353
     * @since 2023/8/24 11:17:26
     */
    @Override
    public UserBalance getEntity(Integer userId) {
        return this.getOne(Wrappers.<UserBalance>lambdaQuery().eq(UserBalance::getUserId, userId));
    }

    /**
     * <p>
     * 新增錢包資訊
     * </p>
     * @param userBalanceDTO UserBalanceDTO
     * @return int
     * @author 6687353
     * @since 2023/8/23 15:03:28
     */
    @Override
    public int addEntity(UserBalanceDTO userBalanceDTO) {
        UserBalance userBalance = new UserBalance();
        BeanUtils.copyProperties(userBalanceDTO, userBalance);
        userBalance.setCreateTime(LocalDateTime.now());
        return baseMapper.insert(userBalance);
    }

    /**
     * <p>
     * 更新錢包資訊
     * </p>
     * @param userBalanceDTO UserBalanceDTO
     * @return int
     * @author 6687353
     * @since 2023/8/23 15:03:44
     */
    @Override
    public int updateInfo(UserBalanceDTO userBalanceDTO) {
        UserBalance userBalance = new UserBalance();
        BeanUtils.copyProperties(userBalanceDTO, userBalance);
        return baseMapper.updateById(userBalance);
    }

    /**
     * <p>
     * 更新錢包資訊
     * </p>
     * @param userBalance UserBalance
     * @return int
     * @author 6687353
     * @since 2023/8/24 13:18:45
     */
    @Override
    public int updateEntity(UserBalance userBalance) {
        userBalance.setUpdateTime(LocalDateTime.now());
        return baseMapper.updateById(userBalance);
    }

    /**
     * <p>
     * 模擬用戶儲值（內部使用）
     * </p>
     * @param userBalanceDTO UserBalanceDTO
     * @author 6687353
     * @since 2023/9/5 13:47:01
     */
    @Override
    public void update(UserBalanceDTO userBalanceDTO) {
        LambdaUpdateWrapper<UserBalance> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(UserBalance::getUserId, userBalanceDTO.getUserId())
                .set(UserBalance::getBalance, userBalanceDTO.getBalance())
                .set(UserBalance::getAvailableAmount, userBalanceDTO.getAvailableAmount())
                .set(UserBalance::getPendingBalance, userBalanceDTO.getPendingBalance())
                .set(UserBalance::getTradingAmount, userBalanceDTO.getTradingAmount())
                .set(UserBalance::getUpdateTime, LocalDateTime.now());
        this.userBalanceMapper.update(null, updateWrapper);
    }
}
