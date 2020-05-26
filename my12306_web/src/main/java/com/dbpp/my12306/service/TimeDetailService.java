package com.dbpp.my12306.service;

import com.dbpp.my12306.entity.TimeDetail;
import com.dbpp.my12306.mapper.TimeDetailMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimeDetailService {

	private final TimeDetailMapper timeDetailMapper;

	public TimeDetailService(TimeDetailMapper timeDetailMapper) {
		this.timeDetailMapper = timeDetailMapper;
	}

	public TimeDetail get(Integer timeDetailId){
		return timeDetailMapper.selectByPrimaryKey(timeDetailId);
	}

	public List<TimeDetail> getAllOf(String trainCode) {
		return timeDetailMapper.selectByTrainCode(trainCode);
	}

	public Integer add(TimeDetail record) {
		return timeDetailMapper.insert(record);
	}

	public int update(TimeDetail recode) {
		return timeDetailMapper.updateByPrimaryKeySelective(recode);
	}

	public Integer deleteByIndexes(String trainCode, Integer[] indexes) {
		return timeDetailMapper.deleteByIndexes(trainCode, indexes);
	}

	public Integer deleteAllOf(String trainCode) {
		return timeDetailMapper.deleteByTrainCode(trainCode);
	}
}
