package com.dbpp.my12306.mapper;

import com.dbpp.my12306.entity.RouteSchedule;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteScheduleMapper {
    int deleteByPrimaryKey(Long routeId);

    int insert(RouteSchedule record);

    int insertSelective(RouteSchedule record);

    RouteSchedule selectByPrimaryKey(Long routeId);

    int updateByPrimaryKeySelective(RouteSchedule record);

    int updateByPrimaryKey(RouteSchedule record);

    List<RouteSchedule> selectByTrainCode(String trainCode);

    int deleteByTrainCode(String trainCode);
}