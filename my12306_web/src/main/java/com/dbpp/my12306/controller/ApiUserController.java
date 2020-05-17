package com.dbpp.my12306.controller;

import com.dbpp.my12306.entity.User;
import com.dbpp.my12306.service.AuthService;
import com.dbpp.my12306.service.LoggerService;
import com.dbpp.my12306.service.UserService;
import com.dbpp.my12306.utils.ResponseSet;
import com.dbpp.my12306.utils.ResultCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiUserController {
	private final AuthService authService;
	private final UserService userService;
	private final LoggerService loggerService;

	private Logger logger = LogManager.getLogger(this.getClass().getName());

	public ApiUserController(AuthService authService, UserService userService, LoggerService loggerService) {
		this.authService = authService;
		this.userService = userService;
		this.loggerService = loggerService;
	}

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public ResponseSet<?> getInfo(HttpServletRequest request) {
		var auth = authService.getAuth(request);
		if (auth.getStatus() != ResultCode.SUCCESS.getCode()) {
			return auth;
		}

		String[] detail = auth.getData();
		var ret = userService.getByName(detail[0]);
		User data = ret.getData();
		if (data == null) {
			ret = new ResponseSet<>(ResultCode.NO_RESULT, "No such username", null);
		}
		if (data != null && !data.getPassword().equals(detail[1])) {
			ret = new ResponseSet<>(ResultCode.INVALID_AUTH, "Wrong password", null);
		}
		loggerService.info(logger, "ApiUserController.getInfo auth=" + detail[0], ret);
		return ret;
	}

	@RequestMapping(value = "/admin/user", method = RequestMethod.GET)
	public ResponseSet<?> getInfoAdmin(@RequestParam(required = false) Integer id,
	                                   @RequestParam(required = false) String name,
	                                   HttpServletRequest request) {
		var auth = authService.checkAdmin(request);
		if (auth.getStatus() != ResultCode.SUCCESS.getCode()) { return auth; }

		ResponseSet<User> ret;
		if (id != null) {
			ret = userService.getById(id);
		} else if (name != null) {
			ret = userService.getByName(name);
		} else {
			ret = new ResponseSet<>(ResultCode.PARAMS_ERROR, "Require at least one param", null);
		}
		loggerService.info(logger, "ApiUserController.getInfoAdmin id=" + id
				+ " name=" + name + " auth=" + auth.getData(), ret);
		return ret;
	}

	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public ResponseSet<?> register(@RequestBody Map<String, String> m) {
		ResponseSet<Integer> ret;
		String name = m.get("name");
		String password = m.get("password");
		if (name == null || password == null) {
			ret = new ResponseSet<>(ResultCode.PARAMS_ERROR, "Name and password are required.", null);
		} else {
			ret = userService.add(name, password, m.get("phone_no"),
					m.get("kind"), m.get("rnc"));
		}
		loggerService.info(logger, "ApiUserController.register", ret);
		return ret;
	}

	@RequestMapping(value = "/user", method = RequestMethod.PUT)
	public ResponseSet<?> update(@RequestBody Map<String, String> m,
	                             HttpServletRequest request) {
		var auth = authService.getAuth(request);
		if (auth.getStatus() != ResultCode.SUCCESS.getCode()) {
			return auth;
		}

		String[] detail = auth.getData();
		var ret = userService.update(detail[0], detail[1], m.get("new_pwd"), m.get("phone_no"),
				m.get("kind"), m.get("rnc"));
		loggerService.info(logger, "ApiUserController.update m=" + m + " request=" + request, ret);
		return ret;
	}

	@RequestMapping(value = "/admin/user", method = RequestMethod.DELETE)
	public ResponseSet<?> delete(@RequestParam(required = false) Integer id,
	                             @RequestParam(required = false) String name,
	                             HttpServletRequest request) {
		var auth = authService.checkAdmin(request);
		if (auth.getStatus() != ResultCode.SUCCESS.getCode()) { return auth; }

		ResponseSet<Integer> ret;
		if (id == null && name == null) {
			ret = new ResponseSet<>(ResultCode.PARAMS_ERROR, "Require at least one param", null);
		} else {
			ret = userService.delete(id, name);
		}
		loggerService.info(logger, "ApiUserController.delete id=" + id + " name=" + name, ret);
		return ret;
	}


	@RequestMapping(value = "/admin/users/num", method = RequestMethod.GET)
	public ResponseSet<?> count(HttpServletRequest request) {
		var auth = authService.checkAdmin(request);
		if (auth.getStatus() != ResultCode.SUCCESS.getCode()) { return auth; }

		var ret = userService.count();
		loggerService.info(logger, "ApiUserController.count", ret);
		return ret;
	}

	@RequestMapping("/admin/users")
	public ResponseSet<?> getAll(HttpServletRequest request) {
		var auth = authService.checkAdmin(request);
		if (auth.getStatus() != ResultCode.SUCCESS.getCode()) { return auth; }

		var ret = userService.getAll();
		loggerService.info(logger, "ApiUserController.getAll", ret);
		return ret;
	}

//	@RequestMapping("/hide")
//	public ResponseSet<Integer> delete(@RequestParam int id) {
//		var ret = userService.hide(id);
//		loggerService.info(logger, "com.dbpp.my12306.controller" +
//				".ApiUserController.delete id=" + id, ret);
//		return ret;
//	}
}
