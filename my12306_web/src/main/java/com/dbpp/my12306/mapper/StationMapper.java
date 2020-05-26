package com.dbpp.my12306.mapper;

import com.dbpp.my12306.entity.Station;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StationMapper {
    int deleteByPrimaryKey(Integer stationId);

    int insert(Station record);

    int insertSelective(Station record);

    Station selectByPrimaryKey(Integer stationId);

    int updateByPrimaryKeySelective(Station record);

    int updateByPrimaryKey(Station record);

    List<Station> select(Integer stationId, String stationName, String cityName);

    int deleteByName(String name);
}