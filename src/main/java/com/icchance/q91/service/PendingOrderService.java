package com.icchance.q91.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.icchance.q91.entity.model.PendingOrder;
import com.icchance.q91.entity.vo.PendingOrderVO;

import java.util.List;

public interface PendingOrderService extends IService<PendingOrder> {

    List<PendingOrderVO> getPendingOrderList(Integer userId);
}
