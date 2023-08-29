package com.icchance.q91.service;

import com.icchance.q91.common.result.Result;
import com.icchance.q91.entity.dto.MessageDTO;

/**
 * <p>
 * 訊息功能服務類
 * </p>
 * @author 6687353
 * @since 2023/8/18 13:43:49
 */
public interface MessageService {

    /**
     * <p>
     * 取得首頁公告跑馬燈訊息
     * </p>
     * @param messageDTO MessageDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/17 16:00:01
     */
    Result getAnnouncement(MessageDTO messageDTO);

    /**
     * <p>
     * 取得會員未讀站內信數量
     * </p>
     * @param messageDTO MessageDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/18 13:43:09
     */
    Result getUnreadPrivateMessageAmount(MessageDTO messageDTO);

    /**
     * <p>
     * 取得會員消息管理清單
     * </p>
     * @param messageDTO MessageDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/18 13:47:12
     */
    Result getMessageList(MessageDTO messageDTO);

    /**
     * <p>
     * 會員站內信標記已讀
     * </p>
     * @param messageDTO MessageDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/18 13:59:55
     */
    Result setPrivateMessageNotice(MessageDTO messageDTO);

    /**
     * <p>
     * 會員刪除站內信
     * </p>
     * @param messageDTO MessageDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/18 14:37:56
     */
    Result deletePrivateMessage(MessageDTO messageDTO);
}
