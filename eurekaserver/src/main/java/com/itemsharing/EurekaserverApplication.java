package com.itemsharing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaServer
@RestController
public class EurekaserverApplication {
	@RequestMapping("/test")
	public String test() {
		return "Eureka Server Running...";
	}

	public static void main(String[] args) {
		SpringApplication.run(EurekaserverApplication.class, args);
	}
}
