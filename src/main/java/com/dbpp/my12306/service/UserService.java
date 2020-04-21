package com.dbpp.my12306.service;

import com.dbpp.my12306.entity.User;
import com.dbpp.my12306.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;

	public String msg;

	/**
	 * Get the number of users
	 * @return number of users
	 */
	public int count(){
		return userMapper.count();
	}

	/**
	 * Get all users
	 * @return the list of users and user info
	 */
	public List<User> getAll() {
		return userMapper.getAll();
	}

	/**
	 * Get user by id
	 * @param id user id
	 * @return the user object. Null if not exits.
	 */
	public User getById(long id) {
		return userMapper.getById(id);
	}

	/**
	 * Get user by name
	 * @param name user name
	 * @return the user object. Null if not exits.
	 */
	public User getByName(String name) {
		return userMapper.getByName(name);
	}

	/**
	 * Add user to the database
	 * @return the id of user.
	 */
	public long add(String name, String password) {
		long ret;
		try {
			ret = userMapper.add(new User(name, password));
		} catch (DuplicateKeyException e) {
			msg = e.getMessage();
			System.err.println(msg);
			return -1;
		}
		return ret;
	}

	/**
	 * Delete user from database
	 * @param id user id to delete
	 * @param name user name to delete
	 * @return delete or not
	 */
	public boolean delete(Long id, String name) {
		int ret = userMapper.delete(id, name);
		if (ret == 0) {
			msg = "No such user.";
			return false;
		}
		return true;
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
