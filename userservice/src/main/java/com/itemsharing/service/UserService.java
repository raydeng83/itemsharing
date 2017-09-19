package com.itemsharing.service;

import com.itemsharing.model.User;

/**
 * Created by z00382545 on 9/19/17.
 */
public interface UserService {
    User createUser(User user);

    User getUserByUsername(String username);
}
