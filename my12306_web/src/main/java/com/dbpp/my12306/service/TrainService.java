package com.dbpp.my12306.service;

import com.dbpp.my12306.entity.Train;
import com.dbpp.my12306.mapper.TrainMapper;
import org.springframework.stereotype.Service;

@Service
public class TrainService {

	private final TrainMapper trainMapper;

	public TrainService(TrainMapper trainMapper) {
		this.trainMapper = trainMapper;
	}

	public Train get(String trainNo){
		return trainMapper.selectByPrimaryKey(trainNo);
	}

	public Integer add(Train record) {
		return trainMapper.insert(record);
	}

	public Integer delete(String trainNo) {
		return trainMapper.deleteByPrimaryKey(trainNo);
	}
}
