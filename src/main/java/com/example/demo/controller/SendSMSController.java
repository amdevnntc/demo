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
				System.out.println("mail sent to Registered user ");
				repo.save(found);
				rsobj.setMessage("OTP sent to email address. This will be valid for 10 minutes.");
				rsobj.setHasError(false);
				rsobj.setObject(found);
			}
		} else {
			User found = repo.findByPhone(phone);
			if (found == null) {
				rsobj.setMessage("user does not exist !");
				rsobj.setHasError(true);
			} else {
				SendMessage.message(phone, "hey user Your OTP is " + random);
				System.out.println("otp send to registered mobile number");
				found.setOtp(random);
				repo.save(found);
				rsobj.setMessage("OTP sent to mobile number. This will be valid for 10 minutes.");
				rsobj.setHasError(false);
				rsobj.setObject(found);
			}
		}
		return rsobj;
	}

	@RequestMapping(value = "/authenticate_byotp", method = RequestMethod.POST)
	public @ResponseBody ResponseObject loginByOtp(@RequestParam("phone") String phone,
			@RequestParam("otp") String otp) {
		ResponseObject obj = new ResponseObject();
		if (phone.contains(".")) {
			User found = repo.findByEmail(phone);
			List<User> list = new ArrayList<>();
			if (found.getOtp() == null) {
				obj.setMessage("wrong credential");
				return obj;
			} else {
				Boolean isExist = found.getOtp().equals(otp);
				if (found != null && isExist.equals(true)) {
					list.add(found);
					obj.setObject(list);
					obj.setMessage("user Logged In Sucessfully ");
					found.setOtp(null);
					repo.save(found);
					return obj;
				} else {
					obj.setHasError(true);
					obj.setMessage("wrong credential ");
					return obj;
				}
			}
		} else {
			User found = repo.findByPhone(phone);
			List<User> list = new ArrayList<>();
			if (found.getOtp() == null) {
				obj.setMessage("wrong credential");
				return obj;
			} else {
				Boolean isExist = found.getOtp().equals(otp);
				if (found != null && isExist.equals(true)) {
					list.add(found);
					obj.setObject(list);
					obj.setMessage("user Logged In Sucessfully ");
					found.setOtp(null);
					repo.save(found);
					return obj;
				} else {
					obj.setHasError(true);
					obj.setMessage("wrong credential ");
					return obj;
				}
			}
		}
	}
}