package com.dbpp.my12306.controller;

import com.dbpp.my12306.entity.User;
import com.dbpp.my12306.service.UserService;
import com.dbpp.my12306.utils.ResponseSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class ApiUserController {
	@Autowired
	private UserService userService;

	@RequestMapping("/count")
	public ResponseSet<Integer> count() {
		return userService.count();
	}

	@RequestMapping("/get-by-id")
	public ResponseSet<User> getById(@RequestParam int id) {
		return userService.getById(id);
	}

	@RequestMapping("/get-by-name")
	public ResponseSet<User> getByName(String name) {
		return userService.getByName(name);
	}

	@RequestMapping("/get-all")
	public ResponseSet<List<User>> getAll() {
		return userService.getAll();
	}

	@RequestMapping("/add")
	public ResponseSet<Integer>
	add(@RequestParam String name,
	    @RequestParam String password,
	    @RequestParam(value = "phone-no", required = false) String phoneNo,
	    @RequestParam(value = "rnc", defaultValue = "Y") String realNameCertification) {
		return userService.add(name, password, phoneNo, realNameCertification);
	}

	@RequestMapping("/hide")
	public ResponseSet<Integer> delete(@RequestParam int id) {
		return userService.hide(id);
	}

	@RequestMapping("/delete")
	public ResponseSet<Integer> delete(@RequestParam(required = false) Integer id,
	                                   @RequestParam(required = false) String name) {
		return userService.delete(id, name);
	}
}
