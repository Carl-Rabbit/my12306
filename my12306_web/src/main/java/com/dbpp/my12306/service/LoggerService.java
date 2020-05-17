package com.dbpp.my12306.service;

import com.dbpp.my12306.mapper.LoggerMapper;
import com.dbpp.my12306.utils.ResponseSet;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class LoggerService {

	private final LoggerMapper loggerMapper;


	public LoggerService(LoggerMapper loggerMapper) {
		this.loggerMapper = loggerMapper;
	}


	/**
	 * Logging the info
	 */
	public void info(Logger logger, String event, ResponseSet responseSet) {
		logger.info(event + " " + responseSet);
		loggerMapper.add(event, responseSet.toString());
	}
}
