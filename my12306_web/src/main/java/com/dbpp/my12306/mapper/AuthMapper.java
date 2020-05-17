package com.dbpp.my12306.mapper;

import com.dbpp.my12306.entity.Admin;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthMapper {
	Admin getById(int id);
	Admin getByName(String name);
}
