package com.icchance.q91.service.impl;

import com.icchance.q91.common.constant.ResultCode;
import com.icchance.q91.common.result.Result;
import com.icchance.q91.dao.FakeMessageDB;
import com.icchance.q91.service.MessageService;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {

    private final FakeMessageDB fakeMessageDB;
    public MessageServiceImpl(FakeMessageDB fakeMessageDB) {
        this.fakeMessageDB = fakeMessageDB;
    }

    @Override
    public Result getAnnouncement(String token) {
        return Result.builder().resultCode(ResultCode.SUCCESS).resultMap(fakeMessageDB.getAnnouncement()).build();
    }

    @Override
    public Result getUnreadPrivateMessageAmount(String token) {
        return Result.builder().resultCode(ResultCode.SUCCESS).resultMap(1).build();
    }

    @Override
    public Result getMessageList(String token) {
        return Result.builder().resultCode(ResultCode.SUCCESS).resultMap(fakeMessageDB.getMessageList()).build();
    }

    @Override
    public Result setPrivateMessageNotice(String token, Integer id) {
        return Result.builder().resultCode(ResultCode.SUCCESS).build();
    }

    @Override
    public Result deletePrivateMessage(String token, Integer id) {
        return Result.builder().resultCode(ResultCode.SUCCESS).build();
    }
}
