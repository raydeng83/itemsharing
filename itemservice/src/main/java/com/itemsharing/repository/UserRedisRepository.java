package com.itemsharing.repository;

import com.itemsharing.model.User;

/**
 * Created by z00382545 on 9/20/17.
 */
public interface UserRedisRepository {
    void saveUser(User user);
    void updateUser(User user);
    void deleteUser(String username);
    User findUser(String username);
}
