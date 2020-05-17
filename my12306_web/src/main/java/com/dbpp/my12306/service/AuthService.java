package com.dbpp.my12306.service;

import com.dbpp.my12306.entity.Admin;
import com.dbpp.my12306.entity.User;
import com.dbpp.my12306.mapper.AuthMapper;
import com.dbpp.my12306.mapper.UserMapper;
import com.dbpp.my12306.utils.ResponseSet;
import com.dbpp.my12306.utils.ResultCode;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class AuthService {

	private final AuthMapper authMapper;
	private final UserMapper userMapper;

	public AuthService(AuthMapper authMapper, UserMapper userMapper) {
		this.authMapper = authMapper;
		this.userMapper = userMapper;
	}

	public ResponseSet<String[]> getAuth(HttpServletRequest request) {
		ResponseSet<String[]> ret = new ResponseSet<>();
		String auth = request.getHeader("authorization");
		if (auth == null) {
			ret.setStatus(ResultCode.INVALID_AUTH);
			ret.setDetail("No authentication information.");
		} else {
			ret.setStatus(ResultCode.SUCCESS);
			ret.setData(auth.split(":"));
		}
		return ret;
	}

	/**
	 * Get admin by id
	 *
	 * @param id admin id
	 * @return the admin object. Null if not exits.
	 */
	public ResponseSet<Admin> getById(int id) {
		ResponseSet<Admin> ret = new ResponseSet<>();
		try {
			ret.setData(authMapper.getById(id));
			ret.setStatus(ret.getData() != null ?
					ResultCode.SUCCESS : ResultCode.NO_RESULT);
		} catch (Exception e) {
			ret.setData(null);
			ret.setStatus(ResultCode.EXCEPTION);
			ret.setDetail(e.getMessage());
		}
		return ret;
	}

	/**
	 * Get admin by name
	 *
	 * @param name admin name
	 * @return the admin object. Null if not exits.
	 */
	public ResponseSet<Admin> getByName(String name) {
		ResponseSet<Admin> ret = new ResponseSet<>();
		try {
			ret.setData(authMapper.getByName(name));
			ret.setStatus(ret.getData() != null ?
					ResultCode.SUCCESS : ResultCode.NO_RESULT);
		} catch (Exception e) {
			ret.setData(null);
			ret.setStatus(ResultCode.EXCEPTION);
			ret.setDetail(e.getMessage());
		}
		return ret;
	}

	/**
	 * check admin from request
	 *
	 * @param request request object
	 * @return the admin object. Null if not exits.
	 */
	public ResponseSet<Admin> checkAdmin(HttpServletRequest request) {
		ResponseSet<Admin> ret = new ResponseSet<>();
		String auth = request.getHeader("authorization");
		if (auth == null) {
			ret.setStatus(ResultCode.INVALID_AUTH);
			ret.setDetail("No authentication information.");
			return ret;
		}
		String[] detail = auth.split(":");
		try {
			Admin data = authMapper.getByName(detail[0]);
			if (data == null) {
				ret = new ResponseSet<>(ResultCode.INVALID_AUTH, "No such admin", null);
			}
			if (data != null) {
				if (!data.getPassword().equals(detail[1])) {
					ret = new ResponseSet<>(ResultCode.INVALID_AUTH, "Wrong password", null);
				} else if (!"Y".equals(data.getAvailable())) {
					ret = new ResponseSet<>(ResultCode.FAIL, "This admin account is disable", null);
				} else {
					ret = new ResponseSet<>(ResultCode.SUCCESS, null, data);
				}
			}
		} catch (Exception e) {
			ret.setData(null);
			ret.setStatus(ResultCode.EXCEPTION);
			ret.setDetail(e.getMessage());
		}
		return ret;
	}

	/**
	 * check normal user from request
	 *
	 * @param request request object
	 * @return the user object. Null if not exits.
	 */
	public ResponseSet<User> checkUser(HttpServletRequest request) {
		ResponseSet<User> ret = new ResponseSet<>();
		String auth = request.getHeader("authorization");
		if (auth == null) {
			ret.setStatus(ResultCode.INVALID_AUTH);
			ret.setDetail("No authentication information.");
			return ret;
		}
		String[] detail = auth.split(":");
		try {
			User data = userMapper.getByName(detail[0]);
			if (data == null) {
				ret = new ResponseSet<>(ResultCode.INVALID_AUTH, "No such user", null);
			}
			if (data != null) {
				if (!data.getPassword().equals(detail[1])) {
					ret = new ResponseSet<>(ResultCode.INVALID_AUTH, "Wrong password", null);
				} else if (!"Y".equals(data.getAvailable())) {
					ret = new ResponseSet<>(ResultCode.FAIL, "This user account is disable", null);
				} else {
					ret = new ResponseSet<>(ResultCode.SUCCESS, null, data);
				}
			}
		} catch (Exception e) {
			ret.setData(null);
			ret.setStatus(ResultCode.EXCEPTION);
			ret.setDetail(e.getMessage());
		}
		return ret;
	}
}
