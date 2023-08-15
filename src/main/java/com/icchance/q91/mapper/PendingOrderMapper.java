package com.icchance.q91.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.icchance.q91.entity.dto.MarketDTO;
import com.icchance.q91.entity.model.PendingOrder;
import com.icchance.q91.entity.vo.MarketVO;
import com.icchance.q91.entity.vo.PendingOrderVO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface PendingOrderMapper extends BaseMapper<PendingOrder> {
    
    List<PendingOrderVO> getPendingOrderList(@Param("userId") Integer userId);

    List<MarketVO> getMarketList(@Param("dto") MarketDTO marketDTO);

    MarketVO getPendingOrder(@Param("userId") Integer userId, @Param("orderId") Integer orderId);

}
