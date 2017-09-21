package com.itemsharing.service.impl;

import com.itemsharing.client.UserRestTemplateClient;
import com.itemsharing.model.Item;
import com.itemsharing.model.User;
import com.itemsharing.repository.ItemRepository;
import com.itemsharing.service.ItemService;
import com.itemsharing.service.UserService;
import com.itemsharing.util.UserContextHolder;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by z00382545 on 9/19/17.
 */
@Service
public class ItemServiceImpl implements ItemService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRestTemplateClient userRestTemplateClient;

    @Override
    public Item addItemByUser(Item item, String username) {
        Item localItem = itemRepository.findByName(item.getName());

        if(localItem != null) {
            LOG.info("Item with name {} already exist. Nothing will be done. ", item.getName());
            return null;
        } else {
            Date today = new Date();
            item.setAddDate(today);

            User user = userService.findByUsername(username);
            item.setUser(user);
            Item newItem = itemRepository.save(item);

            return newItem;
        }
    }

    @Override
    public List<Item> getAllItems() {
        return (List<Item>) itemRepository.findAll();
    }

    @Override
    public List<Item> getItemsByUsername(String username) {
        User user = userService.findByUsername(username);

        return (List<Item>) itemRepository.findByUser(user);
    }

    @Override
    public Item getItemById(Long id) {
        return itemRepository.findOne(id);
    }

    @Override
    public Item updateItem(Item item) throws IOException {
        Item localItem = getItemById(item.getId());

        if (localItem == null) {
            throw new IOException("Item was not found.");
        } else {
            localItem.setName(item.getName());
            localItem.setItemCondition(item.getItemCondition());
            localItem.setStatus(item.getStatus());
            localItem.setDescription(item.getDescription());

            return itemRepository.save(localItem);
        }
    }

    @Override
    public void deleteItemById(Long id) {
        itemRepository.delete(id);
    }

    @Override
//    @HystrixCommand(
//            fallbackMethod = "buildFallbackUser",
//            threadPoolKey = "itemByUserThreadPool",
//            threadPoolProperties = {@HystrixProperty(name="coreSize", value = "30"), @HystrixProperty(name="maxQueueSize", value = "10")},
//            commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value="10000")}
//            )
    public User getUserByUsername(String username) {
//        randomlyRunLong();
        LOG.debug("ItemService.getUserByUsername  Correlation id: {}", UserContextHolder.getContext().getCorrelationId());
//        User user = userService.findByUsername(username);
        User user = userRestTemplateClient.getUser(username);

        return user;
    }

    private void randomlyRunLong() {
        Random rand = new Random();
        int randomNum = rand.nextInt((3 - 1) + 1) + 1;
        if (randomNum == 3) sleep();
    }

    private void sleep() {
        try {
            Thread.sleep(11000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private User buildFallbackUser(String username) {
        User user = new User();
        user.setId(12319732L);
        user.setUsername("no username");

        return user;
    }

}