package com.icchance.q91.controller;

import com.icchance.q91.annotation.UserLoginToken;
import com.icchance.q91.common.result.Result;
import com.icchance.q91.common.result.ResultSuper;
import com.icchance.q91.entity.dto.MessageDTO;
import com.icchance.q91.entity.model.Announcement;
import com.icchance.q91.entity.vo.MessageListVO;
import com.icchance.q91.entity.vo.MessageVO;
import com.icchance.q91.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
     * @param messageDTO MessageDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/17 15:59:25
     */
    @UserLoginToken
    @PostMapping("/announcement")
    public Result<List<Announcement>> getAnnouncement(@RequestBody MessageDTO messageDTO) {
        return SUCCESS_DATA.repData(messageService.getAnnouncement(messageDTO)).build();
    }

    /**
     * <p>
     * 取得會員未讀站內信數量
     * </p>
     * @param messageDTO MessageDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/18 13:34:19
     */
    @UserLoginToken
    @PostMapping("/private/unread")
    public Result<MessageVO> getUnreadPrivateMessageAmount(@RequestBody @Valid MessageDTO messageDTO) {
        return SUCCESS_DATA.repData(messageService.getUnreadPrivateMessageAmount(messageDTO)).build();
    }

    /**
     * <p>
     * 取得會員消息管理清單
     * </p>
     * @param messageDTO MessageDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/18 13:46:44
     */
    @UserLoginToken
    @PostMapping("/list")
    public Result<MessageListVO> getMessageList(@RequestBody MessageDTO messageDTO) {
        return SUCCESS_DATA.repData(messageService.getMessageList(messageDTO)).build();
    }

    /**
     * <p>
     * 會員站內信標記已讀
     * </p>
     * @param messageDTO MessageDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/18 13:59:27
     */
    @UserLoginToken
    @PostMapping("/private/notice")
    public Result<Void> setPrivateMessageNotice(@RequestBody @Valid MessageDTO messageDTO) {
        messageService.setPrivateMessageNotice(messageDTO);
        return SUCCESS;
    }

    /**
     * <p>
     * 會員刪除站內信
     * </p>
     * @param messageDTO MessageDTO
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/18 14:37:16
     */
    @UserLoginToken
    @DeleteMapping("/private/delete")
    public Result<Void> deletePrivateMessage(@RequestBody MessageDTO messageDTO) {
        messageService.deletePrivateMessage(messageDTO);
        return SUCCESS;
    }
}
