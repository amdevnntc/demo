package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.constants.SendMessage;
import com.example.demo.entity.ResponseObject;
import com.example.demo.entity.User;
import com.example.demo.repo.UserRepository;
import com.example.demo.util.RandomText;

@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SendSMSController {

	@Autowired
	private UserRepository repo;

	@RequestMapping(value = "/getOtp", method = RequestMethod.POST)
	public @ResponseBody ResponseObject getOtp(@RequestParam("phone") String phone) {
		ResponseObject rsobj = new ResponseObject();
		User found = repo.findByPhone(phone);
		if (found == null) {
			rsobj.setMessage("number not  Exist !");
			rsobj.setHasError(true);
		} else {
			String random = RandomText.generateRND();
			SendMessage.message(phone, "Your OTP is " + random);
			found.setOtp(random);
			repo.save(found);
			rsobj.setMessage("OTP sent to mobile number. This will be valid for 10 minutes.");
			rsobj.setHasError(false);
		}
		return rsobj;
	}

	@RequestMapping(value = "/authenticate_byotp", method = RequestMethod.POST)
	public @ResponseBody ResponseObject loginByOtp(@RequestParam("phone") String phone,
			@RequestParam("otp") String otp) {
		ResponseObject rsobj = new ResponseObject();
		List<Object> obj = new ArrayList<Object>();
		User found = repo.findByPhone(phone);
		Boolean exist = found.getOtp().equals(otp);
		if (found != null && exist.equals(true)) {
			obj.add(found);
			rsobj.setObject(obj);
			rsobj.setHasError(false);
			found.setOtp(null);
			repo.save(found);
			rsobj.setMessage("sucessfully Logged In");
			return rsobj;
		} else {
			rsobj.setMessage("wrong credential");
			rsobj.setHasError(true);
			return rsobj;
		}
	}

}