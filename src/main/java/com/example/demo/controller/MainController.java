package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.repo.UserRepository;

@RestController
public class MainController {
	
	@Autowired
	private UserRepository repo;
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String show() {
		return "welcome in my fitst deployment ";
	}
}
