package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.UserAchievmenst;

public interface UserAchievmentsRepo extends JpaRepository<UserAchievmenst,Long> {
	
	

}
