package com.ma.springcloud.service;

import java.util.List;

import com.ma.springcloud.entities.Dept;

public interface DeptService {
	public boolean add(Dept dept);

	public Dept get(Long id);

	public List<Dept> list();
}
