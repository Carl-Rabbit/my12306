package com.dbpp.my12306.service;

import com.dbpp.my12306.entity.User;
import com.dbpp.my12306.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;

	/**
	 * Get the number of users
	 * @return number of users
	 */
	public int count(){
		return userMapper.count();
	}

//	/**
//	 * Get all users
//	 * @return the list of users and user info
//	 */
//	List<Map<String, Object>> getAll();

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

//	/**
//	 * Add user to the database
//	 * @param user user object to add
//	 * @return the pk of user
//	 */
//	long add(User user);
//
//	/**
//	 * Delete user from database
//	 * @param id user id to delete
//	 * @return the pk of user.
//	 */
//	long delete(int id);
//
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
