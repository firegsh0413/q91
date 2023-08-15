package com.icchance.q91.controller;

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

    @GetMapping("/announcement")
    public Result getAnnouncement(@RequestParam String userToken) {
        return messageService.getAnnouncement(userToken);
    }

    @GetMapping("/private/unread")
    public Result getUnreadPrivateMessageAmount(@RequestParam String userToken) {
        return messageService.getUnreadPrivateMessageAmount(userToken);
    }

    @GetMapping("/list")
    public Result getMessageList(@RequestParam String userToken) {
        return messageService.getMessageList(userToken);
    }

    @PostMapping("/private/notice")
    public Result setPrivateMessageNotice(@RequestParam String userToken, @RequestParam Integer id) {
        return messageService.setPrivateMessageNotice(userToken, id);
    }

    @DeleteMapping("/private/delete")
    public Result deletePrivateMessage(@RequestParam String userToken, @RequestParam Integer id) {
        return messageService.deletePrivateMessage(userToken, id);
    }
}
