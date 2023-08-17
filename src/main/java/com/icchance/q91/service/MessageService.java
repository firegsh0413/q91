package com.icchance.q91.service;

import com.icchance.q91.common.result.Result;

public interface MessageService {

    Result getAnnouncement(String token);

    Result getUnreadPrivateMessageAmount(String token);

    Result getMessageList(String token);

    Result setPrivateMessageNotice(String token, Integer id);

    Result deletePrivateMessage(String token, Integer id);
}
