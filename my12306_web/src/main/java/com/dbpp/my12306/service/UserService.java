package com.dbpp.my12306.service;

import com.dbpp.my12306.entity.User;
import com.dbpp.my12306.mapper.UserMapper;
import com.dbpp.my12306.utils.ResponseSet;
import com.dbpp.my12306.utils.ResultCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

@Service
public class UserService {

	private final UserMapper userMapper;

	public UserService(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	/**
	 * Get the number of users
	 *
	 * @return number of users
	 */
	public ResponseSet<Integer> count() {
		ResponseSet<Integer> ret = new ResponseSet<>();
		try {
			ret.setStatus(ResultCode.SUCCESS);
			ret.setData(userMapper.count());
		} catch (Exception e) {
			ret.setData(-1);
			ret.setStatus(ResultCode.EXCEPTION);
			ret.setDetail(e.getMessage());
		}
		return ret;
	}

	/**
	 * Get all users
	 *
	 * @return the list of users and user info
	 */
	public ResponseSet<List<User>> getAll() {
		ResponseSet<List<User>> ret = new ResponseSet<>();
		try {
			ret.setData(userMapper.getAll());
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
	 * Get user by id
	 *
	 * @param id user id
	 * @return the user object. Null if not exits.
	 */
	public ResponseSet<User> getById(int id) {
		ResponseSet<User> ret = new ResponseSet<>();
		try {
			ret.setData(userMapper.getById(id));
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
	 * Get user by name
	 *
	 * @param name user name
	 * @return the user object. Null if not exits.
	 */
	public ResponseSet<User> getByName(String name) {
		ResponseSet<User> ret = new ResponseSet<>();
		try {
			ret.setData(userMapper.getByName(name));
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
	 * Add user to the database
	 *
	 * @return the info of user.
	 */
	@Transactional
	public ResponseSet<Integer> add(String name, String password, String phoneNo,
	                                String kind, String realNameCertification) {
		User user = new User(name, password, phoneNo, kind, realNameCertification);
		ResponseSet<Integer> ret = new ResponseSet<>();
		try {
			ret.setStatus(ResultCode.CREATED);
			userMapper.add(user);
			user.setAvailable("Y");
			if (user.getRealNameCertification() == null) {
				user.setRealNameCertification("Y");
			}
			ret.setData(user.getUserId());
		} catch (Exception e) {
			ret.setDetail(null);
			if (e.getCause().getMessage().contains("duplicate key")) {
				ret.setStatus(ResultCode.SUCCESS_IS_HAVE);
			} else if (e.getCause().getMessage().contains("row contains")) {
				ret.setStatus(ResultCode.CONS_ERROR);
			} else {
				ret.setStatus(ResultCode.EXCEPTION);
			}
			ret.setDetail(e.getCause().getMessage().split("Detail: ")[1]);
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
		}
		return ret;
	}

	/**
	 * Set user to be not available
	 *
	 * @param id user id to hide
	 * @return hide or not
	 */
	@Transactional
	public ResponseSet<Integer> hide(int id) {
		ResponseSet<Integer> ret = new ResponseSet<>();
		try {
			int r = userMapper.hide(id);
			if (r == 1) {
				ret.setDetail("Hide completed.");
				ret.setStatus(ResultCode.SUCCESS);
			} else {
				ret.setDetail("No such user.");
				ret.setStatus(ResultCode.FAIL);
			}
			ret.setData(r);
		} catch (Exception e) {
			ret.setData(null);
			ret.setStatus(ResultCode.EXCEPTION);
			ret.setDetail(e.getMessage());
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
		}
		return ret;
	}

	/**
	 * Delete user from database
	 *
	 * @param id   user id to delete
	 * @param name user name to delete
	 * @return delete or not
	 */
	@Transactional
	public ResponseSet<Integer> delete(Integer id, String name) {
		ResponseSet<Integer> ret = new ResponseSet<>();
		try {
			int r = userMapper.delete(id, name);
			if (r == 1) {
				ret.setDetail("Delete completed.");
				ret.setStatus(ResultCode.SUCCESS);
			} else {
				ret.setDetail("No such user.");
				ret.setStatus(ResultCode.FAIL);
			}
			ret.setData(r);
		} catch (Exception e) {
			ret.setData(null);
			ret.setStatus(ResultCode.EXCEPTION);
			ret.setDetail(e.getMessage());
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
		}
		return ret;
	}

	/**
	 * Update the user.
	 * If the user not exits, change nothing
	 *
	 * @return the fix info of user
	 */
	@Transactional
	public ResponseSet<Integer> update(String name, String oldPwd, String newPwd,
	                                   String newPhoneNo, String newKind, String newRnc) {
		User user = new User(name, newPwd, newPhoneNo, newKind, newRnc);
		ResponseSet<Integer> ret = new ResponseSet<>();
		try {
			if (userMapper.update(user, oldPwd) == 0) {
				ret.setStatus(ResultCode.INVALID_AUTH);
				ret.setDetail("The username or password is wrong");
			} else {
				ret.setStatus(ResultCode.SUCCESS);
			}
		} catch (Exception e) {
			ret.setDetail(null);
			ret.setStatus(ResultCode.EXCEPTION);
			ret.setDetail(e.getMessage());
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
		}
		return ret;
	}

}
