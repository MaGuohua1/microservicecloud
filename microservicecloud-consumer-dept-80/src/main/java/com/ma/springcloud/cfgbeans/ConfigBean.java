package com.ma.springcloud.cfgbeans;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;

@Configuration
public class ConfigBean {//boot	-->spring	applicationContext.xml --- @Configuration配置 ConfigBean = applicationContext.xml

	@Bean
	@LoadBalanced	//Spring Cloud Ribbon是基于Netflix Ribbon实现的一套客户端 负载均衡的工具。
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	public IRule myRule() {
		return new RandomRule();//选择随机算法代替轮询算法。
	}
}
