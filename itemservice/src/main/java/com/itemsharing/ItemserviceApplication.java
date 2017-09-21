package com.itemsharing;

import com.itemsharing.config.ServiceConfig;
import com.itemsharing.event.UserChangeModel;
import com.itemsharing.model.Item;
import com.itemsharing.model.User;
import com.itemsharing.service.ItemService;
import com.itemsharing.service.UserService;
import com.itemsharing.util.UserContextInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableCircuitBreaker
@EnableResourceServer
@EnableBinding(Sink.class)
public class ItemserviceApplication implements CommandLineRunner{

	private static final Logger logger = LoggerFactory.getLogger(ItemserviceApplication.class);

	@Value("${redis.server}")
	private String redisServer="";

	@Value("${redis.port}")
	private String redisPort="";

	@Autowired
	private ItemService itemService;

	@Autowired
	private UserService userService;

	@Autowired
	private ServiceConfig serviceConfig;

//	@LoadBalanced
	@Bean
	public RestTemplate getRestTemplate() {
		RestTemplate template = new RestTemplate();
		List interceptors = template.getInterceptors();
		if (interceptors == null) {
			template.setInterceptors(Collections.singletonList(new UserContextInterceptor()));
		} else {
			interceptors.add(new UserContextInterceptor());
			template.setInterceptors(interceptors);
		}

		return template;
	}

	@Bean
	public JedisConnectionFactory jedisConnectionFactory() {
		JedisConnectionFactory jedisConnFactory = new JedisConnectionFactory();
		jedisConnFactory.setHostName(redisServer);
		jedisConnFactory.setPort(new Integer(redisPort));
		return jedisConnFactory;
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
		template.setConnectionFactory(jedisConnectionFactory());
		return template;
	}

	@StreamListener(Sink.INPUT)
	public void loggerSink(
			UserChangeModel userChange
	){
		logger.debug("Received an event for username {}", userChange.getUsername());
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
