package com.dbpp.my12306.controller;

import com.dbpp.my12306.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	@Autowired
	private UserService userService;

	@RequestMapping("/user_cnt")
	public String count() {
		return String.valueOf(userService.count());
	}

	@RequestMapping("/get_user/{id}")
	public String getById(@PathVariable long id) {
		var ret = userService.getById(id);
		return (ret == null) ? "null" : ret.toString() ;
	}

	@RequestMapping("/get_user")
	public String getByName(String name) {
		var ret = userService.getByName(name);
		return (ret == null) ? "null" : ret.toString() ;
	}
}
