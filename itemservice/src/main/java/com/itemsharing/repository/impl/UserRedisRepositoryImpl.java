package com.itemsharing.repository.impl;

import com.itemsharing.model.User;
import com.itemsharing.repository.UserRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

/**
 * Created by z00382545 on 9/20/17.
 */

@Repository
public class UserRedisRepositoryImpl implements UserRedisRepository{
    private static final String HASH_NAME ="user";

    private RedisTemplate<String, User> redisTemplate;
    private HashOperations hashOperations;

    public UserRedisRepositoryImpl(){
        super();
    }

    @Autowired
    private UserRedisRepositoryImpl(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init() {
        hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public void saveUser(User user) {
        hashOperations.put(HASH_NAME, user.getUsername(), user);
    }

    @Override
    public void updateUser(User user) {
        hashOperations.put(HASH_NAME, user.getUsername(), user);
    }

    @Override
    public void deleteUser(String username) {
        hashOperations.delete(HASH_NAME, username);
    }

    @Override
    public User findUser(String username) {
        return (User) hashOperations.get(HASH_NAME, username);
    }
}
