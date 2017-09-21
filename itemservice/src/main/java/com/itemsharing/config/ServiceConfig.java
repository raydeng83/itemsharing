package com.itemsharing.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by z00382545 on 9/19/17.
 */

@Component
public class ServiceConfig {

    @Value("${signing.key}")
    private String jwtSigningKey = "";

    @Value("${redis.server}")
    private String redisServer="";

    @Value("${redis.port}")
    private String redisPort="";

    public String getRedisServer(){
        return redisServer;
    }

    public Integer getRedisPort(){
        return new Integer( redisPort ).intValue();
    }

    public String getJwtSigningKey() {
        return jwtSigningKey;
    }
}
