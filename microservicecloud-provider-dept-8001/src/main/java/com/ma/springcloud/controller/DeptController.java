package com.ma.springcloud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ma.springcloud.entities.Dept;
import com.ma.springcloud.service.DeptService;

@RestController
@RequestMapping("/dept")
public class DeptController {

	@Autowired
	private DeptService service;
	
	@Autowired
	private DiscoveryClient client;

	@PostMapping("/add")
	private boolean add(@RequestBody Dept dept) {
		return service.add(dept);
	}

	@GetMapping("/get/{id}")
	private Dept get(@PathVariable("id") Long id) {
		return service.get(id);
	}

	@GetMapping("/list")
	private List<Dept> list() {
		return service.list();
	}
	
	@GetMapping("/discovery")
	public Object discovery() {
		List<String> services = client.getServices();
		System.out.println("********"+services);
		
		List<ServiceInstance> instances = client.getInstances("MICROSERVICECLOUD-DEPT");
		for (ServiceInstance element : instances) {
			System.out.println(element.getServiceId()+"\t"+element.getHost()+"\t"+element.getPort()+"\t"+element.getUri());
		}
		return client;
	}
}
