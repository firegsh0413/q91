package com.icchance.q91.nsq;


import com.icchance.q91.common.constant.NsqTopicEnum;
import com.sproutsocial.nsq.Publisher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * NSQ生產者 訊息發送方
 * </p>
 * @author 6687353
 * @since 2023/9/19 11:34:28
 */
@Component
public class Producer {
    @Value("${nsq.produce.host}")
    private String host;

    @Value("${nsq.produce.port}")
    private Integer port;

    @Value("${nsq.timeout}")
    private Integer timeout;

    /**
     * <p>
     * 訂單賣家確認後 推送買家必須上傳支付訊息
     * </p>
     * @param body  訊息主體
     * @author 6687353
     * @since 2023/9/20 14:16:48
     */
    public void checkOrder(String body) {
        Publisher publisher = new Publisher(host + ":" + port);
        publisher.publishDeferred(NsqTopicEnum.CHECK_ORDER.getValue(), body.getBytes(), timeout, TimeUnit.SECONDS);
    }

    /**
     * <p>
     * 掛單買家支付後 推送賣家必須打幣訊息
     * </p>
     * @param body 訊息主體
     * @author 6687353
     * @since 2023/9/20 14:17:39
     */
    public void uploadCert(String body) {
        Publisher publisher = new Publisher(host + ":" + port);
        publisher.publishDeferred(NsqTopicEnum.UPLOAD_CERT.getValue(), body.getBytes(), timeout, TimeUnit.SECONDS);
    }
}
