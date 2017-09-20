package com.itemsharing.controller;

import com.itemsharing.config.ServiceConfig;
import com.itemsharing.model.Item;
import com.itemsharing.model.User;
import com.itemsharing.service.ItemService;
import com.itemsharing.util.TokenParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * Created by z00382545 on 9/19/17.
 */

@RestController
@RequestMapping("/v1/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ServiceConfig serviceConfig;

    @RequestMapping(method = RequestMethod.POST)
    public Item addItem(@RequestBody Item item, @RequestHeader("Authorization") String bearerToken) {
        String username = "";

        if(bearerToken != null) {
            String accessToken = bearerToken.replace("Bearer ", "");

            username = TokenParser.getUsername(accessToken, serviceConfig.getJwtSigningKey());
        }

        return itemService.addItemByUser(item, username);
    }

    @RequestMapping("/itemsByUser")
    public List<Item> getAllItemsByUser(@RequestHeader("Authorization") String bearerToken) {
        String username="";

        if(bearerToken != null) {
            String accessToken = bearerToken.replace("Bearer ", "");

            username = TokenParser.getUsername(accessToken, serviceConfig.getJwtSigningKey());
        }

        return itemService.getItemsByUsername(username);
    }

    @RequestMapping("/all")
    public List<Item> getAllItems() {
        return itemService.getAllItems();
    }

    @RequestMapping("/{id}")
    public Item getItemById(@PathVariable Long id) {
        return itemService.getItemById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Item updateItem(@PathVariable Long id, @RequestBody Item item) throws IOException{
        item.setId(id);
        return itemService.updateItem(item);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteItemById(@PathVariable Long id) throws IOException {
        itemService.deleteItemById(id);
    }

    @RequestMapping("/user/{username}")
    public User getUserByUsername(@PathVariable  String username) {
        return itemService.getUserByUsername(username);
    }
}
