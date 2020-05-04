package com.dbpp.my12306.utils;

import com.dbpp.my12306.entity.Role;
import com.dbpp.my12306.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.PriorityQueue;

@Component
public class ConnHandler {

	@Autowired
	private UserService userService;

	private HashMap<String, Role> roleMap
			= new HashMap<>();
	private PriorityQueue<Long> connTimes
			= new PriorityQueue<>();

	/**
	 * Check the connect role.
	 * @return Object[2]. The first is the result code,
	 *              0: first connect
	 *              1: not first connect
	 *              -1: name wrong
	 *              -2: password wrong
	 *          second is the role object.
	 */
	public Object[] check(char type, String name, String password) {
		int code = 0;
		Role role = roleMap.get(name);

		if (role == null) {
			if (type == Role.USER) {
				role = userService.getByName(name);
			} else if (type == Role.ADMIN) {
				role = userService.getByName(name);
			}
			if (role == null)
				code = -1;
			else if (!role.getPassword().equals(password))
				code = -2;
		} else {

		}

		return new Object[]{code, role};
	}
}
