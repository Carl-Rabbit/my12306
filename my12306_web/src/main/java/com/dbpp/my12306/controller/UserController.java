package com.dbpp.my12306.controller;

import com.dbpp.my12306.entity.User;
import com.dbpp.my12306.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
	@Autowired
	private UserService userService;

	Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

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

	@RequestMapping("/all_users")
	public String getAll() {
		List<User> list = userService.getAll();
		StringBuilder sb = new StringBuilder();
		for (User user : list) {
			sb.append(user).append("<br>");
		}
		sb.append("Total: ").append(list.size());
		return sb.toString();
	}

	@RequestMapping("/add_user")
	public String add(@RequestParam String name,
	                  @RequestParam String password) {
		var ret = userService.add('A', name, password);
		if (ret == 0) {
			return "Insert failed<br>" + userService.msg;
		} else {
			return "Insert completed. userId of new user: " + ret;
		}
	}

	@RequestMapping("/del_user")
	public String delete(@RequestParam(required = false) Long id,
						 @RequestParam(required = false) String name) {
		logger.info("delete user: " + id + " " + name);
		if (id == null && name == null) return "Empty argument";
		boolean success = userService.delete(id, name);
		if (!success) {
			return "Delete failed<br>" + userService.msg;
		} else {
			return "Delete completed.";
		}
	}
}
