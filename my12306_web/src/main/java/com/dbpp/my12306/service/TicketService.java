package com.dbpp.my12306.service;

import com.dbpp.my12306.entity.Ticket;
import com.dbpp.my12306.mapper.TicketMapper;
import org.springframework.stereotype.Service;

@Service
public class TicketService {
	private final TicketMapper ticketMapper;

	public TicketService(TicketMapper ticketMapper) {
		this.ticketMapper = ticketMapper;
	}

	public Ticket getInfo(Integer ticketId) {
		return ticketMapper.selectByPrimaryKey(ticketId);
	}

	public Integer delete(Integer ticketId) {
		return ticketMapper.deleteByPrimaryKey(ticketId);
	}

	public Integer refund(Integer ticketId) {
		return ticketMapper.refund(ticketId);
	}
}
