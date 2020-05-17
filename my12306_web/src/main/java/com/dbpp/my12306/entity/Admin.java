package com.dbpp.my12306.entity;

public class Admin {
	private int adminId;
	private String adminName;
	private String password;
	private String kind;
	private String available;

	public Admin(int adminId, String adminName, String password, String kind, String available) {
		this.adminId = adminId;
		this.adminName = adminName;
		this.password = password;
		this.kind = kind;
		this.available = available;
	}

	public Admin(String adminName, String password, String kind) {
		this.adminName = adminName;
		this.password = password;
		this.kind = kind;
	}

	public int getAdminId() {
		return adminId;
	}

	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getAvailable() {
		return available;
	}

	public void setAvailable(String available) {
		this.available = available;
	}

	@Override
	public String toString() {
		return "Admin{" +
				"adminId=" + adminId +
				", adminName=" + adminName +
				", password='" + password + '\'' +
				", kind='" + kind + '\'' +
				", available='" + available + '\'' +
				'}';
	}
}
