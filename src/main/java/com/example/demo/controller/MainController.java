package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.ResponseObject;
import com.example.demo.entity.User;
import com.example.demo.repo.UserRepo;

@RestController
public class MainController {
	
	@Autowired
	private UserRepo repo;
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String show() {
		return "welcome in my fitst deployment ";
	}
	
	@RequestMapping(value="save",method=RequestMethod.POST)
	public ResponseObject savedata(@RequestParam("name") String name,@RequestParam("address")String address) {
		ResponseObject res = new ResponseObject();
	    User user = new User();
	    user.setName("devesh");
	    user.setAddress("mishra");
	   User saved =  repo.save(user);
	    if(saved!=null) {
	    	res.setHasError(false);
	    	res.setMessage("data saved");
	    	return res;
	    }
	    else {
	    	res.setMessage("some problem");
		return res;
	}	
	}
	
	@GetMapping("getAll")
	public List<User> showAll(){
			List<User> user =	repo.findAll();
			System.out.println(user);
			return user;
	}
}
