package com.itemsharing;

import com.itemsharing.model.Item;
import com.itemsharing.model.Role;
import com.itemsharing.model.User;
import com.itemsharing.model.UserRole;
import com.itemsharing.service.ItemService;
import com.itemsharing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.sleuth.Sampler;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableCircuitBreaker
@EnableResourceServer
public class ItemserviceApplication implements CommandLineRunner{

	@Autowired
	private ItemService itemService;

	@Autowired
	private UserService userService;

	@Bean
	public Sampler defaultSampler() {
		return new AlwaysSampler();
	}

	public static void main(String[] args) {
		SpringApplication.run(ItemserviceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		User user = userService.findByUsername("jadams");

		Item item = new Item();
		item.setName("Item1");
		item.setItemCondition("New");
		item.setStatus("Active");
		item.setAddDate(new Date());
		item.setDescription("Here is a test description.");
		item.setUser(user);

		itemService.addItemByUser(item, user.getUsername());
		item.setId(null);
		item.setName("Item2");
		item.setItemCondition("Used");
		item.setStatus("Inactive");
		item.setAddDate(new Date());
		item.setDescription("Here is a test description for item 2.");
		item.setUser(user);

		itemService.addItemByUser(item, user.getUsername());
	}
}
