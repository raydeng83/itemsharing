package com.itemsharing.event;

import com.itemsharing.utility.UserContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * Created by z00382545 on 9/20/17.
 */
@Component
public class SimpleSourceBean {
    private Source source;

    private static final Logger logger = LoggerFactory.getLogger(SimpleSourceBean.class);

    @Autowired
    public SimpleSourceBean(Source source) {
        this.source = source;
    }

    public void publishUserChange(String action, String username) {
        logger.debug("Sending Kafka message {} for username: {}", action, username);

        UserChangeModel change = new UserChangeModel(
                UserChangeModel.class.getTypeName(),
                action,
                username,
                UserContext.getCorrelationId()
        );

        source.output().send(MessageBuilder.withPayload(change).build());
    }

}