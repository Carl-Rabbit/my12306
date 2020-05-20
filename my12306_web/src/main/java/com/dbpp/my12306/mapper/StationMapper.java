package com.dbpp.my12306.mapper;

import com.dbpp.my12306.entity.Station;

public interface StationMapper {
    int deleteByPrimaryKey(Integer stationId);

    int insert(Station record);

    int insertSelective(Station record);

    Station selectByPrimaryKey(Integer stationId);

    int updateByPrimaryKeySelective(Station record);

    int updateByPrimaryKey(Station record);
}