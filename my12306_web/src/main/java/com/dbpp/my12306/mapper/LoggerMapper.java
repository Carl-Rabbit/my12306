package com.dbpp.my12306.mapper;

import org.springframework.stereotype.Repository;

@Repository
public interface LoggerMapper {
	int add(String event, String result);
}
