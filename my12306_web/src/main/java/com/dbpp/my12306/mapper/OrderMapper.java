package com.dbpp.my12306.mapper;

import com.dbpp.my12306.entity.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderMapper {
	int count();
	int countAllOf(int userId);
	Order getById(int id);
	List<Order> getAll();
	List<Order> getAllOf(int userId);
}
