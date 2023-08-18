package com.icchance.q91.controller;

import com.icchance.q91.annotation.UserLoginToken;
import com.icchance.q91.common.constant.ResultCode;
import com.icchance.q91.common.result.Result;
import com.icchance.q91.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 訊息功能控制類
 * </p>
 * @author 6687353
 * @since 2023/8/18 13:43:33
 */
@Slf4j
@RequestMapping(MessageController.PREFIX)
@RestController
public class MessageController extends BaseController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    static final String PREFIX = "/message";

    /**
     * <p>
     * 取得首頁公告跑馬燈訊息
     * </p>
     * @param token 令牌
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/17 15:59:25
     */
    @UserLoginToken
    @GetMapping("/announcement")
    public Result getAnnouncement(@RequestParam String token) {
        return messageService.getAnnouncement(token);
    }

    /**
     * <p>
     * 取得會員未讀站內信數量
     * </p>
     * @param token 令牌
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/18 13:34:19
     */
    @UserLoginToken
    @GetMapping("/private/unread")
    public Result getUnreadPrivateMessageAmount(@RequestParam String token) {
        return messageService.getUnreadPrivateMessageAmount(token);
    }

    /**
     * <p>
     * 取得會員消息管理清單
     * </p>
     * @param token 令牌
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/18 13:46:44
     */
    @UserLoginToken
    @GetMapping("/list")
    public Result getMessageList(@RequestParam String token) {
        return messageService.getMessageList(token);
    }

    /**
     * <p>
     * 會員站內信標記已讀
     * </p>
     * @param token 令牌
     * @param id  站內信uid
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/18 13:59:27
     */
    @UserLoginToken
    @PostMapping("/private/notice")
    public Result setPrivateMessageNotice(@RequestParam String token, @RequestParam Integer id) {
        return messageService.setPrivateMessageNotice(token, id);
    }

    /**
     * <p>
     * 會員刪除站內信
     * </p>
     * @param token 令牌
     * @param id  站內信uid
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/18 14:37:16
     */
    @UserLoginToken
    @DeleteMapping("/private/delete")
    public Result deletePrivateMessage(@RequestParam String token, @RequestParam Integer id) {
        return messageService.deletePrivateMessage(token, id);
    }
}
