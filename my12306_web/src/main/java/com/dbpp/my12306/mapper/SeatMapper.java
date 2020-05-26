package com.dbpp.my12306.mapper;

import com.dbpp.my12306.entity.Seat;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatMapper {
    int deleteByPrimaryKey(Long seatId);

    int insert(Seat record);

    int insertSelective(Seat record);

    Seat selectByPrimaryKey(Long seatId);

    int updateByPrimaryKeySelective(Seat record);

    int updateByPrimaryKey(Seat record);

    List<Seat> selectByTrainNo(String trainNo);

    int deleteByTrainNo(String trainNo);
}