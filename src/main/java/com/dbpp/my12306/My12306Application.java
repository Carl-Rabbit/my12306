package com.dbpp.my12306;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class My12306Application {

	public static void main(String[] args) {
		SpringApplication.run(My12306Application.class, args);
	}

}
