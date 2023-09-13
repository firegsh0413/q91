package com.icchance.q91.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.icchance.q91.common.constant.ResultCode;
import com.icchance.q91.common.error.ServiceException;
import com.icchance.q91.common.result.Result;
import com.icchance.q91.common.result.ResultSuper;
import com.icchance.q91.entity.dto.MessageDTO;
import com.icchance.q91.entity.model.Announcement;
import com.icchance.q91.entity.vo.AnnouncementVO;
import com.icchance.q91.entity.vo.MessageListVO;
import com.icchance.q91.entity.vo.PrivateMessageVO;
import com.icchance.q91.entity.vo.MessageVO;
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

    private final AnnouncementMapper announcementMapper;

    private final PrivateMessageMapper privateMessageMapper;

    private final JwtUtil jwtUtil;

    public MessageServiceImpl(AnnouncementMapper announcementMapper, PrivateMessageMapper privateMessageMapper,
                              JwtUtil jwtUtil) {
        this.announcementMapper = announcementMapper;
        this.privateMessageMapper = privateMessageMapper;
        this.jwtUtil = jwtUtil;
    }

    /**
     * <p>
     * 取得首頁公告跑馬燈訊息
     * </p>
     * @param messageDTO MessageDTO
     * @return java.util.List<com.icchance.q91.entity.model.Announcement>
     * @author 6687353
     * @since 2023/8/17 16:00:15
     */
    @Override
    public List<Announcement> getAnnouncement(MessageDTO messageDTO) {
        List<Announcement> announcementList = announcementMapper.selectList(Wrappers.<Announcement>lambdaQuery());
        if (CollectionUtils.isEmpty(announcementList)) {
            throw new ServiceException(ResultCode.NO_DATA);
        }
        return announcementList;
    }

    /**
     * <p>
     * 取得會員未讀站內信數量
     * </p>
     * @param messageDTO MessageDTO
     * @return com.icchance.q91.entity.vo.MessageVO
     * @author 6687353
     * @since 2023/8/18 13:43:09
     */
    @Override
    public MessageVO getUnreadPrivateMessageAmount(MessageDTO messageDTO) {
        Integer userId = jwtUtil.parseUserId(messageDTO.getToken());
        int privateMessageAmount = privateMessageMapper.getPrivateMessageAmount(userId, IS_READ_FALSE);
        if (0 == privateMessageAmount) {
            throw new ServiceException(ResultCode.NO_DATA);
        }
        return MessageVO.builder().count(privateMessageAmount).build();
    }

    /**
     * <p>
     * 取得會員消息管理清單
     * </p>
     * @param messageDTO MessageDTO
     * @return com.icchance.q91.entity.vo.MessageListVO
     * @author 6687353
     * @since 2023/8/18 13:47:12
     */
    @Override
    public MessageListVO getMessageList(MessageDTO messageDTO) {
        Integer userId = jwtUtil.parseUserId(messageDTO.getToken());
        List<PrivateMessageVO> privateMessages = privateMessageMapper.getPrivateMessageList(userId);
        List<Announcement> announcements = announcementMapper.selectList(Wrappers.lambdaQuery());
        List<AnnouncementVO> announcementVOList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(announcements)) {
            announcementVOList = announcements.stream().map(this::convertAnnouncementVO).collect(Collectors.toList());
        }
        return MessageListVO.builder()
                .privateMessage(privateMessages)
                .announcement(announcementVOList)
                .build();
    }

    /**
     * <p>
     * 會員站內信標記已讀
     * </p>
     * @param messageDTO MessageDTO
     * @author 6687353
     * @since 2023/8/18 13:59:55
     */
    @Override
    public void setPrivateMessageNotice(MessageDTO messageDTO) {
        Integer userId = jwtUtil.parseUserId(messageDTO.getToken());
        privateMessageMapper.updateIsRead(messageDTO.getId(), userId, IS_READ_TRUE, LocalDateTime.now());
    }

    /**
     * <p>
     * 會員刪除站內信
     * </p>
     * @param messageDTO MessageDTO
     * @author 6687353
     * @since 2023/8/18 14:37:56
     */
    @Override
    public void deletePrivateMessage(MessageDTO messageDTO) {
        Integer userId = jwtUtil.parseUserId(messageDTO.getToken());
        privateMessageMapper.delete(messageDTO.getId(), userId);
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
