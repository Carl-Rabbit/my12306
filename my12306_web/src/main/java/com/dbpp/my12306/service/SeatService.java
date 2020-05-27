package com.dbpp.my12306.service;

import com.dbpp.my12306.entity.Seat;
import com.dbpp.my12306.mapper.SeatMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatService {

	private final SeatMapper seatMapper;

	public SeatService(SeatMapper seatMapper) {
		this.seatMapper = seatMapper;
	}

	public List<Seat> getAllOf(String trainNo){
		return seatMapper.selectByTrainNo(trainNo);
	}

	public Integer add(Seat record) {
		return seatMapper.insert(record);
	}

	public int update(Seat recode) {
		return seatMapper.updateByPrimaryKeySelective(recode);
	}

	public Integer deleteById(Long seatId) {
		return seatMapper.deleteByPrimaryKey(seatId);
	}

	public Integer deleteAllOf(String trainNo) {
		return seatMapper.deleteByTrainNo(trainNo);
	}

	public Integer generateSeat(String trainNo, String trainCode) {
		return seatMapper.generateSeat(trainNo, trainCode);
	}
}
