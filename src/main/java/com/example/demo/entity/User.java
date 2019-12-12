package com.example.demo.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Entity
@Table(name = "1418user")
@Data
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	private String userid;

	private String name;

	private String idproof;

	private String intrest;

	private int age;

	private String DisplayName;

	private Boolean isEmailVerified;

	private Boolean isPhoneVerified;

	private String state;

	private String gender;

	private String pincode;

	private int childCode;

	private int Active_status;

	private String profession;

	private Boolean isAccountVerified;

	private String Country;

	private String email;

	private String phone;

	private String password;

	@CreationTimestamp
	private Date timestamp;

	private int role;

	private String address;

	private String city;

	private String schoolname;

	private String Achievments;

	private String hobbies;

	private String wanttobe;

	private String ideal;

	private String dob;

	private String otp;

	private String token;

	/*
	 * @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	 * 
	 * @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
	 * inverseJoinColumns = @JoinColumn(name = "role_id")) private Set<Role> roles;
	 */

	private String photoName;

	private String photoUrl;

	private Boolean isprivate;

	public User() {

	}

}
