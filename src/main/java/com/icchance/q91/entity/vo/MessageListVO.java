package com.icchance.q91.entity.vo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * 訊息VO
 * </p>
 * @author 6687353
 * @since 2023/9/6 14:14:43
 */
@Data
@Builder
public class MessageListVO {

    private List<AnnouncementVO> announcement;

    private List<PrivateMessageVO> privateMessage;


}
