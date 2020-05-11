package com.dbpp.my12306.mapper;

import com.dbpp.my12306.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
	int count();
	User getById(int id);
	User getByName(String name);
	List<User> getAll();
	int add(User user);
	int hide(int id);
	int delete(Integer id, String name);
}
