package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.UserAchievmenst;

public interface UserAchievmentsRepo extends JpaRepository<UserAchievmenst,Long> {
	
	@Query(value = "SELECT achievmenst FROM achievments ", nativeQuery = true)
	List<UserAchievmenst> findallachievments();

}
