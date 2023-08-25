package com.icchance.q91.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.icchance.q91.entity.dto.MarketDTO;
import com.icchance.q91.entity.model.PendingOrder;
import com.icchance.q91.entity.vo.MarketVO;
import com.icchance.q91.entity.vo.PendingOrderVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 掛單mapper
 * </p>
 * @author 6687353
 * @since 2023/8/25 11:29:59
 */
public interface PendingOrderMapper extends BaseMapper<PendingOrder> {
    
    List<PendingOrderVO> getPendingOrderList(@Param("userId") Integer userId);

    PendingOrderVO getDetail(@Param("userId") Integer userId, @Param("orderId") Integer orderId);

    List<MarketVO> getMarketList(@Param("dto") MarketDTO marketDTO);

    MarketVO getMarketDetail(@Param("userId") Integer userId, @Param("orderId") Integer orderId);

}
