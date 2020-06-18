package com.ma.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigClientRest {

	@Value("${spring.application.name}")
	private String applicationName;
	
	@Value("${eureka.client.service-url.defaultZone}")
	private String eurekaServers;
	
	@Value("${server.port}")
	private String port;
	
	@GetMapping("/config")
	public String getConfig() {
		System.out.println(toString());
		return toString();
	}

	@Override
	public String toString() {
		return "[applicationName=" + applicationName + ", eurekaServers=" + eurekaServers + ", port="
				+ port + "]";
	}
}
