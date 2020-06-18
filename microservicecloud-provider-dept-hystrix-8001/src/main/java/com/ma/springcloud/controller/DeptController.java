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
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

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

	//一旦调用服务方法失败并抛出了错误信息后，会自动调用@HystrixCommand标注好的fallbackMethod调用类中的指定方法
	@GetMapping("/get/{id}")
	@HystrixCommand(fallbackMethod = "processHystrix_Get")
	private Dept get(@PathVariable("id") Long id) {
		Dept dept = service.get(id);
		if (dept == null) {
			throw new RuntimeException("该ID:"+id+"没有对应的信息");
		}
		return dept;
	}
	
	public Dept processHystrix_Get(@PathVariable("id") Long id) {
		return new Dept().setDeptno(id)
				.setDname("该ID:"+id+"没有对应的信息")
				.setDb_source("no this database in MySQL");
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
