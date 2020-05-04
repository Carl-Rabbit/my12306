package com.dbpp.my12306.mapper;

import com.dbpp.my12306.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
	int count();
	User getById(long id);
	User getByName(String name);
	List<User> getAll();
	long add(User user);
	int delete(Long id, String name);
}
