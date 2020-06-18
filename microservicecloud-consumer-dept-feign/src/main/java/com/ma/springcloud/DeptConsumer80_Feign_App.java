package com.ma.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.ma.springcloud"})
@ComponentScan("com.ma.springcloud")
public class DeptConsumer80_Feign_App {
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(DeptConsumer80_Feign_App.class, args);
	}
	
}
