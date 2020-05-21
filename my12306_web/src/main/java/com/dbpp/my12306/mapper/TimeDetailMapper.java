package com.dbpp.my12306.mapper;

import com.dbpp.my12306.entity.TimeDetail;

public interface TimeDetailMapper {
    int deleteByPrimaryKey(Integer timeDetailId);

    int insert(TimeDetail record);

    int insertSelective(TimeDetail record);

    TimeDetail selectByPrimaryKey(Integer timeDetailId);

    int updateByPrimaryKeySelective(TimeDetail record);

    int updateByPrimaryKey(TimeDetail record);
}