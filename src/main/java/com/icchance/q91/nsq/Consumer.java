package com.icchance.q91.nsq;

import com.icchance.q91.common.constant.MessageConstant;
import com.icchance.q91.common.constant.NsqTopicEnum;
import com.sproutsocial.nsq.Subscriber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * <p>
 * NSQ消費者 訊息接收方
 * </p>
 * @author 6687353
 * @since 2023/9/19 09:40:03
 */
@Slf4j
@Component
public class Consumer implements ApplicationRunner {

    @Value("${nsq.lookup.host}")
    private String host;
    @Value("${nsq.lookup.port}")
    private Integer port;
    @Value("${nsq.lookup.enable}")
    private Integer enable;
    @Value("${nsq.channel}")
    private String channel;
    private final MessageHandler messageHandler;

    public Consumer(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    @Override
    public void run(ApplicationArguments args)  {
        if (MessageConstant.IS_READ_TRUE.equals(enable)) {
            Subscriber subscriber = new Subscriber(host + ":" + port);
            subscriber.subscribe(NsqTopicEnum.CHECK_ORDER.getValue(), channel, messageHandler::getCheckOrderConsumer);
            subscriber.subscribe(NsqTopicEnum.UPLOAD_CERT.getValue(), channel, messageHandler::getUploadCertConsumer);
            subscriber.subscribe(NsqTopicEnum.APPEAL_SUCCESS.getValue(), channel, messageHandler::getAppealSuccess);
            subscriber.subscribe(NsqTopicEnum.APPEAL_FAIL.getValue(), channel, messageHandler::getAppealFail);
        }
    }
}
