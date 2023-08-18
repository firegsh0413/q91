package com.icchance.q91.dao;

import com.icchance.q91.entity.model.Announcement;
import com.icchance.q91.entity.vo.AnnouncementVO;
import com.icchance.q91.entity.vo.MessageListVO;
import com.icchance.q91.entity.vo.PrivateMessageVO;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FakeMessageDB {

    public Announcement getAnnouncement() {
        return Announcement.builder()
                .title("Hello World!")
                .content("Hello world!")
                .isNoticed(1)
                .build();
    }

    public MessageListVO getMessageList() {

        List<AnnouncementVO> announcementVOList = new ArrayList<>();
        List<PrivateMessageVO> privateMessageVOList = new ArrayList<>();
        AnnouncementVO announcementVO = AnnouncementVO.builder()
                .id(5)
                .title("公告A")
                .content("公告A內容")
                .createTime(LocalDateTime.now())
                .build();
        announcementVOList.add(announcementVO);
        announcementVO = AnnouncementVO.builder()
                .id(4)
                .title("公告B")
                .content("公告B內容")
                .createTime(LocalDateTime.now())
                .build();
        announcementVOList.add(announcementVO);
        PrivateMessageVO privateMessageVO = PrivateMessageVO.builder()
                .id(44)
                .isRead(0)
                .title("一般信息")
                .content("一般信息")
                .senderId(2)
                .senderUsername("johndoe")
                .type(1)
                .createTime(LocalDateTime.now())
                .build();
        privateMessageVOList.add(privateMessageVO);
        privateMessageVO = PrivateMessageVO.builder()
                .id(33)
                .isRead(1)
                .title("系统信息")
                .content("系统信息")
                .senderId(-1)
                .senderUsername("系统管理员")
                .type(2)
                .createTime(LocalDateTime.now())
                .build();
        privateMessageVOList.add(privateMessageVO);
        privateMessageVO = PrivateMessageVO.builder()
                .id(22)
                .isRead(1)
                .title("紧急信息")
                .content("紧急信息")
                .senderId(-1)
                .senderUsername("系统管理员")
                .type(3)
                .createTime(LocalDateTime.now())
                .build();
        privateMessageVOList.add(privateMessageVO);
        privateMessageVO = PrivateMessageVO.builder()
                .id(11)
                .isRead(0)
                .title("优惠通知")
                .content("优惠通知")
                .senderId(-1)
                .senderUsername("系统管理员")
                .type(4)
                .createTime(LocalDateTime.now())
                .build();
        privateMessageVOList.add(privateMessageVO);

        return MessageListVO.builder()
                .announcement(announcementVOList)
                .privateMessage(privateMessageVOList)
                .build();
    }
}
