package com.itemsharing.repository;

import com.itemsharing.model.Item;
import com.itemsharing.model.User;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by z00382545 on 9/19/17.
 */
@Transactional
public interface ItemRepository extends CrudRepository<Item, Long> {
    List<Item> findByUser(User user);
    Item findByName(String name);
}
