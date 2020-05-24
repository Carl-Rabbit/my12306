package com.dbpp.my12306.mapper;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
class TicketMapperTest {

    @Autowired
    private TicketMapper ticketMapper;

	@Test
	void buyTickets() {
		var ticketId = ticketMapper.buyTickets(1,
				new Integer[]{1},
				new String[]{"K848"},
				new Integer[]{1},
				new Integer[]{4},
				new String[]{"2020-05-29"},
				new String[]{"B"},
				new String[]{"Z"},
				new String[]{"F"});
		System.out.println(ticketMapper.selectTickets(ticketId.toArray(new Integer[0])));
	}

	@Test
	void selectTickets() {
		System.out.println(ticketMapper.selectTickets(new Integer[]{18, 19, 25}));
	}
}