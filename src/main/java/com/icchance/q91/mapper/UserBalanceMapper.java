package com.icchance.q91.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.icchance.q91.entity.model.UserBalance;
import com.icchance.q91.entity.vo.UserBalanceVO;

/**
 * <p>
 * 會員錢包mapper
 * </p>
 * @author 6687353
 * @since 2023/8/23 13:13:05
 */
public interface UserBalanceMapper extends BaseMapper<UserBalance> {

    UserBalanceVO getUserBalance(Integer userId);
}
