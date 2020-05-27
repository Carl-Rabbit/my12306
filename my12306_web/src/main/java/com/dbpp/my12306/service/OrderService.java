package com.dbpp.my12306.service;

import com.dbpp.my12306.entity.Order;
import com.dbpp.my12306.mapper.OrderMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
	private final OrderMapper orderMapper;

	public OrderService(OrderMapper orderMapper) {
		this.orderMapper = orderMapper;
	}

	public Order getInfo(Integer orderId) {
		return orderMapper.selectByPrimaryKey(orderId);
	}

	public List<Order> getAll(Integer userId) { return orderMapper.selectByUserId(userId); }

	public Integer delete(Integer orderId) {
		return orderMapper.deleteByPrimaryKey(orderId);
	}
}
