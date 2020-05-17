package com.dbpp.my12306.mapper;

import com.dbpp.my12306.entity.Passenger;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PassengerMapper {
	int count();
	int countAllOf(int userId);
	Passenger getById(int id);
	List<Passenger> getAll(boolean seeDisable);
	List<Passenger> getAllOf(int userId, boolean seeDisable);
	List<Passenger> getAllOfByName(String userName, boolean seeDisable);
	int add(Passenger psg);
	int disable(int id);
	int delete(Integer id);
}
