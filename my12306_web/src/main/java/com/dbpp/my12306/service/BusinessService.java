package com.dbpp.my12306.service;

import com.dbpp.my12306.entity.Order;
import com.dbpp.my12306.entity.RouteInfo;
import com.dbpp.my12306.entity.Ticket;
import com.dbpp.my12306.mapper.BusinessMapper;
import com.dbpp.my12306.mapper.OrderMapper;
import com.dbpp.my12306.mapper.TicketMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessService {

	private final BusinessMapper businessMapper;
	private final TicketMapper ticketMapper;
	private final OrderMapper orderMapper;

	public BusinessService(BusinessMapper businessMapper, TicketMapper ticketMapper, OrderMapper orderMapper) {
		this.businessMapper = businessMapper;
		this.ticketMapper = ticketMapper;
		this.orderMapper = orderMapper;
	}

	public List<RouteInfo> listRoutes(String fromStr, String toStr, String departDate){
		return businessMapper.listRoutes(fromStr, toStr, departDate);
	}

	public Object[] buyTickets(Integer userId,
	                               Integer[] psgIdArr,
	                               String[] trainCodeArr,
	                               Integer[] fromIndexArr,
	                               Integer[] toIndexArr,
	                               String[] departDateArr,
	                               String[] seatClassArr,
	                               String[] seatTypeArr,
	                               String[] preferArr) {

		List<Integer> idArr = ticketMapper.buyTickets(userId, psgIdArr, trainCodeArr, fromIndexArr,
				toIndexArr, departDateArr, seatClassArr, seatTypeArr, preferArr);
		List<Ticket> ticketArr = ticketMapper.selectTickets(idArr.toArray(new Integer[0]));
		Order order = (ticketArr.size() == 0) ?
				null : orderMapper.selectByPrimaryKey(ticketArr.get(0).getOrderId());
		return new Object[]{order, idArr, ticketArr};
	}
}
