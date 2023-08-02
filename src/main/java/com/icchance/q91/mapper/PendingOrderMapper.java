package com.icchance.q91.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.icchance.q91.entity.model.PendingOrder;
import com.icchance.q91.entity.vo.PendingOrderVO;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

public interface PendingOrderMapper extends BaseMapper<PendingOrder> {
    
    List<PendingOrderVO> getPendingOrderList(@Param("userId") Integer userId);
}
