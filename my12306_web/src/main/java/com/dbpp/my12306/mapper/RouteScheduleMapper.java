package com.dbpp.my12306.mapper;

import com.dbpp.my12306.entity.RouteSchedule;

public interface RouteScheduleMapper {
    int deleteByPrimaryKey(Long routeId);

    int insert(RouteSchedule record);

    int insertSelective(RouteSchedule record);

    RouteSchedule selectByPrimaryKey(Long routeId);

    int updateByPrimaryKeySelective(RouteSchedule record);

    int updateByPrimaryKey(RouteSchedule record);
}