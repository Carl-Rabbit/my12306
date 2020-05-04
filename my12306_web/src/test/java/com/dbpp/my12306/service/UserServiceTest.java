package com.dbpp.my12306.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
class UserServiceTest {

	@Autowired
	UserService userService;

	@Test
	void count() {
		System.out.println(userService.count());
	}

	@Test
	void getAll() {
	}

	@Test
	void getById() {
	}

	@Test
	void getByName() {
	}

	@Test
	void add() {
	}

	@Test
	void delete() {
	}
}