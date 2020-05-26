package com.dbpp.my12306.service;

import com.dbpp.my12306.entity.City;
import com.dbpp.my12306.mapper.CityMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CityService {

	private final CityMapper cityMapper;

	public CityService(CityMapper cityMapper) {
		this.cityMapper = cityMapper;
	}

	public List<City> get(Integer cityId, String cityName, String province){
		return cityMapper.select(cityId, cityName, province);
	}

	@Transactional
	public Integer add(City record) {
		return cityMapper.insert(record);
	}

	@Transactional
	public Integer delete(Integer cityId) {
		return cityMapper.deleteByPrimaryKey(cityId);
	}

	@Transactional
	public Integer deleteByName(String name) {
		return cityMapper.deleteByName(name);
	}

}
