package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repo.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepo;

	@Override
	public int save(User user) {
		User obj = userRepo.save(user);
		if (obj != null) {
			return 1;
		}
		return 0;
	}

	@Override
	public User authenticateyEmail(String email) {
		User user = userRepo.findByEmail(email);
		return user;
	}

}