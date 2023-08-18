package com.icchance.q91.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.icchance.q91.common.constant.MessageConstant;
import com.icchance.q91.common.constant.ResultCode;
import com.icchance.q91.common.result.Result;
import com.icchance.q91.dao.FakeMessageDB;
import com.icchance.q91.entity.model.Announcement;
import com.icchance.q91.entity.model.PrivateMessage;
import com.icchance.q91.entity.vo.AnnouncementVO;
import com.icchance.q91.entity.vo.MessageListVO;
import com.icchance.q91.entity.vo.PrivateMessageVO;
import com.icchance.q91.mapper.AnnouncementMapper;
import com.icchance.q91.mapper.PrivateMessageMapper;
import com.icchance.q91.service.MessageService;
import com.icchance.q91.util.JwtUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.icchance.q91.common.constant.MessageConstant.IS_READ_FALSE;
import static com.icchance.q91.common.constant.MessageConstant.IS_READ_TRUE;

/**
 * <p>
 * 訊息功能服務類實作
 * </p>
 * @author 6687353
 * @since 2023/8/18 13:44:02
 */
@Service
public class MessageServiceImpl implements MessageService {

    private final FakeMessageDB fakeMessageDB;

    private final AnnouncementMapper announcementMapper;

    private final PrivateMessageMapper privateMessageMapper;

    private final JwtUtil jwtUtil;

    public MessageServiceImpl(FakeMessageDB fakeMessageDB, AnnouncementMapper announcementMapper, PrivateMessageMapper privateMessageMapper,
                              JwtUtil jwtUtil) {
        this.fakeMessageDB = fakeMessageDB;
        this.announcementMapper = announcementMapper;
        this.privateMessageMapper = privateMessageMapper;
        this.jwtUtil = jwtUtil;
    }

    /**
     * <p>
     * 取得首頁公告跑馬燈訊息
     * </p>
     * @param token 令牌
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/17 16:00:15
     */
    @Override
    public Result getAnnouncement(String token) {
        return Result.builder().resultCode(ResultCode.SUCCESS)
                .resultMap(announcementMapper.selectList(Wrappers.<Announcement>lambdaQuery()))
                .build();
        //return Result.builder().resultCode(ResultCode.SUCCESS).resultMap(fakeMessageDB.getAnnouncement()).build();
    }

    /**
     * <p>
     * 取得會員未讀站內信數量
     * </p>
     * @param token 令牌
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/18 13:43:09
     */
    @Override
    public Result getUnreadPrivateMessageAmount(String token) {
        Integer userId = jwtUtil.parseUserId(token);
        return Result.builder().resultCode(ResultCode.SUCCESS).resultMap(privateMessageMapper.getPrivateMessageAmount(userId, IS_READ_FALSE)).build();
    }

    /**
     * <p>
     * 取得會員消息管理清單
     * </p>
     * @param token 令牌
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/18 13:47:12
     */
    @Override
    public Result getMessageList(String token) {
        Integer userId = jwtUtil.parseUserId(token);
        List<PrivateMessageVO> privateMessages = privateMessageMapper.getPrivateMessageList(userId);
        List<Announcement> announcements = announcementMapper.selectList(Wrappers.lambdaQuery());
        List<AnnouncementVO> announcementVOList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(announcements)) {
            announcementVOList = announcements.stream().map(this::convertAnnouncementVO).collect(Collectors.toList());
        }
        MessageListVO messageListVO = MessageListVO.builder()
                .privateMessage(privateMessages)
                .announcement(announcementVOList)
                .build();
        //return Result.builder().resultCode(ResultCode.SUCCESS).resultMap(fakeMessageDB.getMessageList()).build();
        return Result.builder().resultCode(ResultCode.SUCCESS).resultMap(messageListVO).build();
    }

    /**
     * <p>
     * 會員站內信標記已讀
     * </p>
     * @param token 令牌
     * @param id  站內信uid
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/18 13:59:55
     */
    @Override
    public Result setPrivateMessageNotice(String token, Integer id) {
        Integer userId = jwtUtil.parseUserId(token);
        privateMessageMapper.updateIsRead(id, userId, IS_READ_TRUE, LocalDateTime.now());
        return Result.builder().resultCode(ResultCode.SUCCESS).build();
    }

    /**
     * <p>
     * 會員刪除站內信
     * </p>
     * @param token 令牌
     * @param id  站內信uid
     * @return com.icchance.q91.common.result.Result
     * @author 6687353
     * @since 2023/8/18 14:37:56
     */
    @Override
    public Result deletePrivateMessage(String token, Integer id) {
        Integer userId = jwtUtil.parseUserId(token);
        privateMessageMapper.delete(id, userId);
        return Result.builder().resultCode(ResultCode.SUCCESS).build();
    }

    private AnnouncementVO convertAnnouncementVO(Announcement announcement) {
        return AnnouncementVO.builder()
                .id(announcement.getId())
                .title(announcement.getTitle())
                .content(announcement.getContent())
                .createTime(announcement.getCreateTime())
                .build();
    }
}
