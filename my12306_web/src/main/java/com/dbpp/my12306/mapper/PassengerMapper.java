package com.dbpp.my12306.mapper;

import com.dbpp.my12306.entity.Passenger;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PassengerMapper {
	int count();
	int countAllOf(int userId);
	Passenger getById(int id);
	List<Passenger> getAll();
	List<Passenger> getAllOf(int userId);
	int add(Passenger psg);
	int hide(int id);
	int delete(Integer id);
}
