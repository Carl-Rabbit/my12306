package com.dbpp.my12306.service;

import com.dbpp.my12306.entity.RouteSchedule;
import com.dbpp.my12306.mapper.RouteScheduleMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteScheduleService {

	private final RouteScheduleMapper routeScheduleMapper;

	public RouteScheduleService(RouteScheduleMapper routeScheduleMapper) {
		this.routeScheduleMapper = routeScheduleMapper;
	}

	public RouteSchedule get(Long routeScheduleId){
		return routeScheduleMapper.selectByPrimaryKey(routeScheduleId);
	}

	public List<RouteSchedule> getAllOf(String trainCode) {
		return routeScheduleMapper.selectByTrainCode(trainCode);
	}

	public Integer add(RouteSchedule record) {
		return routeScheduleMapper.insert(record);
	}

	public int update(RouteSchedule recode) {
		return routeScheduleMapper.updateByPrimaryKeySelective(recode);
	}

	public Integer delete(Long routeScheduleId) {
		return routeScheduleMapper.deleteByPrimaryKey(routeScheduleId);
	}

	public Integer deleteAllOf(String trainCode, String departDate) {
		return routeScheduleMapper.deleteByCondition(trainCode, departDate);
	}

	public Integer generate(String departDate) {
		return routeScheduleMapper.generate(departDate);
	}
}
