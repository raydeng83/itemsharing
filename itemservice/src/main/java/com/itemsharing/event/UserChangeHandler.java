package com.itemsharing.event;

import com.itemsharing.repository.UserRedisRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;


@EnableBinding(CustomChannels.class)
public class UserChangeHandler {

    @Autowired
    private UserRedisRepository userRedisRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserChangeHandler.class);

    @StreamListener("inboundUserChanges")
    public void loggerSink(UserChangeModel userChange) {
        logger.debug("Received a message of type " + userChange.getType());
        switch(userChange.getAction()){
            case "GET":
                logger.debug("Received a GET event from the user service for username {}", userChange.getUsername());
                break;
            case "SAVE":
                logger.debug("Received a SAVE event from the user service for username {}", userChange.getUsername());
                break;
            case "UPDATE":
                logger.debug("Received a UPDATE event from the user service for username {}", userChange.getUsername());
                userRedisRepository.deleteUser(userChange.getUsername());
                break;
            case "DELETE":
                logger.debug("Received a DELETE event from the user service for username {}", userChange.getUsername());
                userRedisRepository.deleteUser(userChange.getUsername());
                break;
            default:
                logger.error("Received an UNKNOWN event from the user service of type {}", userChange.getType());
                break;

        }
    }

}
