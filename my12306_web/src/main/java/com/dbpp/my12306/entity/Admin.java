package com.dbpp.my12306.entity;

public class Admin extends Role{

	public Admin(long id, char type, String name, String password) {
		super(id, type, name, password);
	}

	public Admin(char type, String name, String password) {
		super(type, name, password);
	}
}
