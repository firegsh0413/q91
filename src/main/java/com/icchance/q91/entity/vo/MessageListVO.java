package com.icchance.q91.entity.vo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MessageListVO {

    private List<AnnouncementVO> announcement;

    private List<PrivateMessageVO> privateMessage;


}
