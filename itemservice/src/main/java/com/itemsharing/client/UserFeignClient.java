package com.itemsharing.client;

import com.itemsharing.model.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by z00382545 on 9/20/17.
 */
@FeignClient("userservice")
public interface UserFeignClient {

    @RequestMapping(value = "/v1/user/{username}", consumes = "application/json")
    User getUserByUsername(@PathVariable("username") String username);
}
