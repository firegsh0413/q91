package com.icchance.q91.controller;

import com.icchance.q91.annotation.UserLoginToken;
import com.icchance.q91.common.constant.ResultCode;
import com.icchance.q91.common.result.Result;
import com.icchance.q91.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping(MessageController.PREFIX)
@RestController
public class MessageController extends BaseController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    static final String PREFIX = "/message";

    @UserLoginToken
    @GetMapping("/announcement")
    public Result getAnnouncement(@RequestParam String token) {
        return messageService.getAnnouncement(token);
    }

    @UserLoginToken
    @GetMapping("/private/unread")
    public Result getUnreadPrivateMessageAmount(@RequestParam String token) {
        return messageService.getUnreadPrivateMessageAmount(token);
    }

    @UserLoginToken
    @GetMapping("/list")
    public Result getMessageList(@RequestParam String token) {
        return messageService.getMessageList(token);
    }

    @UserLoginToken
    @PostMapping("/private/notice")
    public Result setPrivateMessageNotice(@RequestParam String token, @RequestParam Integer id) {
        return messageService.setPrivateMessageNotice(token, id);
    }

    @UserLoginToken
    @DeleteMapping("/private/delete")
    public Result deletePrivateMessage(@RequestParam String token, @RequestParam Integer id) {
        return messageService.deletePrivateMessage(token, id);
    }
}
