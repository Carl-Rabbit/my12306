package com.dbpp.my12306.entity;

public class User extends Role{

	public User(long id, char type, String name, String password) {
		super(id, type, name, password);
	}

	public User(char type, String name, String password) {
		super(type, name, password);
	}
}
