package com.ma.springcloud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ma.springcloud.entities.Dept;
import com.ma.springcloud.service.DeptClientService;

@RestController
@RequestMapping("/consumer/dept")
public class DeptController_Consumer {
	@Autowired
	private DeptClientService service;
	
	@PostMapping("/add")
	public boolean add(Dept dept) {
		return service.add(dept);
	}

	@GetMapping("/get/{id}")
	public Dept get(@PathVariable("id") Long id) {
		return service.get(id);
	}
	
	@GetMapping("/list")
	public List<Dept> list() {
		return service.list();
	}
	
}
