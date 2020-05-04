package com.dbpp.my12306.entity;

import java.util.Objects;

public abstract class Role {
	public static final char USER = 'N';
	public static final char ADMIN = 'A';

	private long id;
	private char type;
	private String name;
	private String password;

	public Role(long id, char type, String name, String password) {
		this.id = id;
		this.type = type;
		this.name = name;
		this.password = password;
	}

	public Role(char type, String name, String password) {
		this.type = type;
		this.name = name;
		this.password = password;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public char getType() {
		return type;
	}

	public void setType(char type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return this.getClass().getName() + "{" +
				"id=" + id +
				", type=" + type +
				", name='" + name + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Role)) return false;
		Role role = (Role) o;
		return id == role.id &&
				type == role.type &&
				name.equals(role.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, type, name);
	}
}
