package com.example.demo.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.constants.UserManagementConstant;
import com.example.demo.entity.ResponseObject;
import com.example.demo.entity.User;
import com.example.demo.repo.UserRepository;
import com.example.demo.service.UserService;
import com.example.demo.util.RandomAlphaNum;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private UserService service;

	@RequestMapping(value = "/userRegister", method = RequestMethod.POST)
	public @ResponseBody ResponseObject userRegister(@RequestBody User user) {
		String token = RandomAlphaNum.generateSessionKey(50);
		ResponseObject obj = new ResponseObject();
		User isExist = userRepo.findByEmail(user.getEmail());
		User isuseridExist = userRepo.findByuserid(user.getUserid());
		if (isExist == null && isuseridExist == null) {
			user.setIsAccountVerified(false);
			user.setIsEmailVerified(false);
			user.setIsPhoneVerified(false);
			user.setToken(token);
			service.save(user);
			obj.setHasError(false);
			obj.setMessage("User Registration Sucessfull!");
			obj.setObject(user);
			return obj;
		} else {
			obj.setHasError(true);
			obj.setMessage("user  Already Available in DataBase !");
			obj.setTimestamp(new Date());
			return obj;
		}
	}

	@RequestMapping(value = "/authenticateby_email", method = RequestMethod.POST)
	public @ResponseBody ResponseObject getUserByEmail(@RequestParam("email") String email,
			@RequestParam("password") String password) {
		ResponseObject obj = new ResponseObject();
		List<User> found = userRepo.getUserByEmail(email, password);
		if (found.isEmpty() != true) {
			obj.setHasError(false);
			obj.setMessage("User Login Successfull");
			obj.setStatus(200);
			obj.setObject(found);
			return obj;
		}
		obj.setHasError(true);
		obj.setMessage("user not found");
		return obj;
	}

	@RequestMapping(value = "/authenticateby_phone", method = RequestMethod.POST)
	public @ResponseBody ResponseObject getUserByPhone(@RequestParam("phone") String phone,
			@RequestParam("password") String password) {
		ResponseObject obj = new ResponseObject();
		List<User> found = userRepo.getUserByPhone(phone, password);
		System.out.println(found.isEmpty());
		if (found.isEmpty() != true) {
			obj.setHasError(false);
			obj.setMessage("User Login Successfull");
			obj.setStatus(200);
			obj.setObject(found);
			return obj;

		}
		obj.setHasError(true);
		obj.setMessage("user not found");
		return obj;
	}

	@RequestMapping(value = "/userPhoneExist")
	public @ResponseBody ResponseObject isUserExistByPhone(@RequestParam("phone") String phone) {

		ResponseObject obj = new ResponseObject();
		User foundUser = userRepo.findByPhone(phone);
		if (foundUser != null) {
			obj.setMessage("Phone Number Already exist");
			return obj;
		}
		obj.setMessage("unique phone number ");
		return obj;
	}

	@RequestMapping(value = "/userEmailExist", method = RequestMethod.GET)
	public @ResponseBody ResponseObject isUserExistByEmail(@RequestParam("email") String email) {

		ResponseObject obj = new ResponseObject();
		User foundUser = service.authenticateyEmail(email);

		if (foundUser != null) {
			obj.setHasError(true);
			obj.setMessage("Email id Already exist");
			obj.setObject(foundUser);
			obj.setTimestamp(new Date());
			return obj;
		} else {
			obj.setHasError(false);
			obj.setMessage("Email unique");
			obj.setObject(foundUser);
			return obj;
		}
	}

	@RequestMapping(value = "/getAlluser/{token}", method = RequestMethod.GET)
	public User getAlluser(@PathVariable("token") String token) {
		User user = userRepo.findBytoken(token);
		return user;

	}

	@SuppressWarnings("unused")
	@PostMapping("/uploadimage")
	public ResponseObject uploadprofileImage(@RequestParam("id") String id, @RequestParam("image") MultipartFile file)
			throws IOException {
		ResponseObject res = new ResponseObject();
		User uinfo = userRepo.findByid(Long.parseLong(id));
		String fname = uinfo.getUserid() + "-profile.png";
		uinfo.setPhotoName(fname);
		uinfo.setPhotoUrl(UserManagementConstant.server_url + "images?image=" + uinfo.getUserid() + "-profile.png"
				+ "&folder=profile-image");
		if (uinfo != null) {
			byte[] byte11 = file.getBytes();
			File dir11 = new File(UserManagementConstant.PROFILE_IMAGE_PATH);
			File serverfile = new File(dir11.getAbsoluteFile() + File.separator + fname);
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverfile));
			stream.write(byte11);
			stream.close();
			userRepo.save(uinfo);
			res.setMessage("profile image uploaded sucessfully");
			return res;
		} else {
			res.setMessage("user not found");
			return res;
		}
	}

	@RequestMapping(value = "/authenticateby_phonenum", method = RequestMethod.POST)
	public @ResponseBody ResponseObject getUserByPhone(@RequestParam("phone") String phone) {
		ResponseObject obj = new ResponseObject();
		User found = userRepo.findByPhone(phone);
		if (found != null) {
			obj.setHasError(false);
			obj.setMessage("User Login Successfull");
			obj.setStatus(200);
			obj.setObject(found);
			return obj;
		}
		obj.setHasError(true);
		obj.setMessage("user not found");
		return obj;
	}

	@PostMapping("/getbyuserid")
	public @ResponseBody ResponseObject findById(@RequestParam("userid") String userid,
			@RequestParam("password") String password) {
		ResponseObject res = new ResponseObject();
		List<User> found = userRepo.findByuserid(userid.trim(), password.trim());
		if (found.isEmpty() != true) {
			res.setMessage("user found");
			res.setObject(found);
			return res;

		} else {
			res.setHasError(true);
			res.setMessage("wrong  credential");
			return res;
		}
	}

	@RequestMapping(value = "/googleRegister", method = RequestMethod.POST)
	public @ResponseBody ResponseObject signupbygoogl(@RequestParam("name") String name,
			@RequestParam("email") String email, @RequestParam("photoUrl") String photoUrl,
			@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) {
		ResponseObject rsobj = new ResponseObject();
		User userbyemail = userRepo.findByEmail(email);
		if (userbyemail == null) {
			userbyemail.setName(name.trim());
			userbyemail.setPhotoUrl(photoUrl.trim());
			userbyemail.setEmail(email.trim());
			userRepo.save(userbyemail);
			rsobj.setMessage("user registered");
			rsobj.setObject(userbyemail);
			return rsobj;
		} else {
			rsobj.setMessage("user already exist");
			return rsobj;
		}
	}
}
