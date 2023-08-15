package com.icchance.q91.service;

import com.icchance.q91.common.result.Result;

public interface MessageService {

    Result getAnnouncement(String userToken);

    Result getUnreadPrivateMessageAmount(String userToken);

    Result getMessageList(String userToken);

    Result setPrivateMessageNotice(String userToken, Integer id);

    Result deletePrivateMessage(String userToken, Integer id);
}
