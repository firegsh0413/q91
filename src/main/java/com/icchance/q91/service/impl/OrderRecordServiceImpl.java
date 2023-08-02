package com.icchance.q91.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.icchance.q91.entity.model.OrderRecord;
import com.icchance.q91.service.OrderRecordService;
import com.icchance.q91.mapper.OrderRecordMapper;
import org.springframework.stereotype.Service;

/**
* @author 6687353
* @description 针对表【order_record】的数据库操作Service实现
* @createDate 2023-08-02 15:03:05
*/
@Service
public class OrderRecordServiceImpl extends ServiceImpl<OrderRecordMapper, OrderRecord>
    implements OrderRecordService{

}




