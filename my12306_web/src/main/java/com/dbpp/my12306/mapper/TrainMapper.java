package com.dbpp.my12306.mapper;

import com.dbpp.my12306.entity.Train;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainMapper {
    int deleteByPrimaryKey(String trainNo);

    int insert(Train record);

    int insertSelective(Train record);

    Train selectByPrimaryKey(String trainNo);

    int updateByPrimaryKeySelective(Train record);

    int updateByPrimaryKey(Train record);
}