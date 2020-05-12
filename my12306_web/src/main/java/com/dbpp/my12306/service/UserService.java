package com.dbpp.my12306.service;

import com.dbpp.my12306.entity.User;
import com.dbpp.my12306.mapper.UserMapper;
import com.dbpp.my12306.utils.ResponseSet;
import com.dbpp.my12306.utils.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;

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
			ret.setStatus(ResultCode.SUCCESS);
			ret.setData(userMapper.getByName(name));
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
	 * @return the id of user.
	 */
	@Transactional
	public ResponseSet<Integer> add(String name, String password, String phoneNo,
	                                String realNameCertification) {
		User user = new User(name, password, phoneNo, realNameCertification);
		ResponseSet<Integer> ret = new ResponseSet<>();
		try {
			ret.setStatus(ResultCode.SUCCESS);
			userMapper.add(user);
			ret.setData(user.getUserId());
		} catch (Exception e) {
			ret.setDetail(null);
			ret.setStatus(ResultCode.EXCEPTION);
			ret.setDetail(e.getMessage());
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
		}
		return ret;
	}

	/**
	 * Set user to be not available
	 *
	 * @param id   user id to hide
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

//	/**
//	 * Update the user.
//	 * If the user not exits, change nothing
//	 * @param user update this user
//	 * @return the pk of user
//	 */
//	long update(User user);
//
//	/**
//	 * Check exist
//	 * @param id user id to check
//	 * @return exist or not
//	 */
//	boolean exist(int id);
}
