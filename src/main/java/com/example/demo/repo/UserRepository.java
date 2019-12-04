package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Query(value = "SELECT * FROM users WHERE email = ?1 and password = ?2", nativeQuery = true)
	User getUserByEmail(String email, String password);

	@Query(value = "SELECT * FROM users WHERE phone = ?1 and password = ?2", nativeQuery = true)
	List<User> getUserByPhone(String phone, String password);

	@Query(value = "SELECT * FROM users WHERE userid = ?1 and password = ?2", nativeQuery = true)
	List<User> findByuserid(String userid, String password);

	User findBytoken(String token);

	User findByid(Long id);

	User findByPhone(String phone);

	User findByEmail(String email);

	User findByuserid(String userid);

	@Query(value = "SELECT * FROM users WHERE phone = ?1 and otp = ?2", nativeQuery = true)
	List<User> getUserByPhonewithotp(String phone, String otp);
	
	@Query(value = "SELECT * FROM users WHERE email = ?1 and otp = ?2", nativeQuery = true)
	List<User> getUserByEmailwithotp(String email, String otp);

}
