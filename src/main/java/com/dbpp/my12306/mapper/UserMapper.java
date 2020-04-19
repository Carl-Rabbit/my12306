package com.dbpp.my12306.mapper;

import com.dbpp.my12306.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
	int count();
	User getById(long id);
	User getByName(String name);
}
