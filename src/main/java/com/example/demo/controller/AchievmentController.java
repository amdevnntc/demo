package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.UserAchievmenst;
import com.example.demo.repo.UserAchievmentsRepo;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AchievmentController {

	@Autowired
	private UserAchievmentsRepo userrepo;

	@GetMapping("/getachievments")
	public List<UserAchievmenst> getachievments() {
		System.out.println(userrepo.findAll());
		return userrepo.findAll();
	}

}
