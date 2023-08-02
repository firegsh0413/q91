package com.icchance.q91.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.icchance.q91.entity.model.PendingOrder;
import com.icchance.q91.entity.vo.PendingOrderVO;
import com.icchance.q91.mapper.PendingOrderMapper;
import com.icchance.q91.service.PendingOrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PendingOrderServiceImpl extends ServiceImpl<PendingOrderMapper, PendingOrder> implements PendingOrderService {

    @Override
    public List<PendingOrderVO> getPendingOrderList(Integer userId) {
        return baseMapper.getPendingOrderList(userId);
    }
}
