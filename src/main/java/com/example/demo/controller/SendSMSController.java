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
import com.example.demo.util.EmailUtil;
import com.example.demo.util.RandomText;

@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SendSMSController {

	@Autowired
	private UserRepository repo;

	@Autowired
	private EmailUtil emailutil;

	@SuppressWarnings("unused")
	@RequestMapping(value = "/getOtp", method = RequestMethod.POST)
	public @ResponseBody ResponseObject getOtp(@RequestParam("phone") String phone) {
		ResponseObject rsobj = new ResponseObject();
		String random = RandomText.generateRND();
		if (phone.contains(".")) {
			User found = repo.findByEmail(phone);
			String msg = "hello  User  Your 1418 login OTP is  " + random + " " + "\n"
					+ "in case any enquiry feel free to reach us " + "\n" + "sampark software solutions " + "\n"
					+ "Sector 32 Gurgaon ";
			if (found == null) {
				rsobj.setMessage("user does not exist !");
				rsobj.setHasError(true);
			} else {
				found.setOtp(random);
				emailutil.sendEmail(found.getEmail(), "1418 Incredible ", msg);
				repo.save(found);
				rsobj.setMessage("OTP sent to email address. This will be valid for 10 minutes.");
				rsobj.setHasError(false);
			}
		} else {
			User found = repo.findByPhone(phone);
			if (found == null) {
				rsobj.setMessage("user does not exist !");
				rsobj.setHasError(true);
			} else {
				SendMessage.message(phone, "hey user Your OTP is " + random);
				found.setOtp(random);
				repo.save(found);
				rsobj.setMessage("OTP sent to mobile number. This will be valid for 10 minutes.");
				rsobj.setHasError(false);
			}
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