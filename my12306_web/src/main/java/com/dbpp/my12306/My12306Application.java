package com.dbpp.my12306;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.dbpp.my12306.mapper")
public class My12306Application {
	public static void main(String[] args) {
		SpringApplication.run(My12306Application.class, args);
	}
}
