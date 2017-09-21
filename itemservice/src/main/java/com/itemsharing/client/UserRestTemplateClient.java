package com.itemsharing.client;

import com.itemsharing.model.User;
import com.itemsharing.repository.UserRedisRepository;
import com.itemsharing.util.UserContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Created by z00382545 on 9/20/17.
 */

@Component
public class UserRestTemplateClient {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserRedisRepository userRedisRepo;

    private static final Logger logger = LoggerFactory.getLogger(UserRestTemplateClient.class);

    private User checkRedisCache(String username) {
        try {
            return userRedisRepo.findUser(username);
        }
        catch (Exception ex){
            logger.error("Error encountered while trying to retrieve user {} check Redis Cache.  Exception {}", username, ex);
            return null;
        }
    }

    private void cacheUserObject(User user) {
        try {
            userRedisRepo.saveUser(user);
        }catch (Exception ex){
            logger.error("Unable to cache user {} in Redis. Exception {}", user.getUsername(), ex);
        }
    }

    public User getUser(String username){
        logger.debug("In Item Service.getUser: {}", UserContext.getCorrelationId());

        User user = checkRedisCache(username);

        if (user!=null){
            logger.debug("I have successfully retrieved an user {} from the redis cache: {}", username, user);
            return user;
        }

        logger.debug("Unable to locate user from the redis cache: {}.", username);

        ResponseEntity<User> restExchange =
                restTemplate.exchange(
                        "http://localhost:8081/v1/user/{username}",
                        HttpMethod.GET,
                        null, User.class, username);

        /*Save the record from cache*/
        user = restExchange.getBody();

        if (user!=null) {
            cacheUserObject(user);
        }

        return user;
    }
}
