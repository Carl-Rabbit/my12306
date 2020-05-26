package com.dbpp.my12306.service;

import com.dbpp.my12306.entity.Station;
import com.dbpp.my12306.mapper.StationMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationService {

	private final StationMapper stationMapper;

	public StationService(StationMapper stationMapper) {
		this.stationMapper = stationMapper;
	}

	public List<Station> get(Integer stationId, String stationName, String cityName){
		return stationMapper.select(stationId, stationName, cityName);
	}

	public Integer add(Station record) {
		return stationMapper.insert(record);
	}

	public Integer delete(Integer cityId) {
		return stationMapper.deleteByPrimaryKey(cityId);
	}

	public Integer deleteByName(String name) {
		return stationMapper.deleteByName(name);
	}

}
