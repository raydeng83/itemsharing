package com.itemsharing.repository;

import com.itemsharing.model.User;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * Created by z00382545 on 9/19/17.
 */
@Transactional
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
