package com.ma.springcloud.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ma.springcloud.entities.Dept;

@FeignClient(value = "MICROSERVICECLOUD-DEPT",fallbackFactory = DeptClientServiceFallbackFactory.class)
@RequestMapping("/dept")
public interface DeptClientService {

	@GetMapping("/get/{id}")
	public Dept get(@PathVariable("id") long id);
	
	@GetMapping("/list")
	public List<Dept> list();
	
	@PostMapping("/add")
	public boolean add(Dept dept);
	
}
