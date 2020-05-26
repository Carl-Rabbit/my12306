package com.dbpp.my12306.mapper;

import com.dbpp.my12306.entity.Ticket;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketMapper {
	int deleteByPrimaryKey(Integer ticketId);

	int insert(Ticket record);

	int insertSelective(Ticket record);

	Ticket selectByPrimaryKey(Integer ticketId);

	int updateByPrimaryKeySelective(Ticket record);

	int updateByPrimaryKey(Ticket record);

	List<Integer> buyTickets(@Param("userId") Integer userId,
	                        @Param("psgIdArr") Integer[] psgIdArr,
	                        @Param("trainCodeArr") String[] trainCodeArr,
	                        @Param("fromIndexArr") Integer[] fromIndexArr,
	                        @Param("toIndexArr") Integer[] toIndexArr,
	                        @Param("departDateArr") String[] departDateArr,
	                        @Param("seatClassArr") String[] seatClassArr,
	                        @Param("seatTypeArr") String[] seatTypeArr,
	                        @Param("preferArr") String[] preferArr);

	List<Ticket> selectTickets(@Param("ticketIdArr")Integer[] ticketIdArr);

	int refund(Integer ticketId);
}