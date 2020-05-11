package com.dbpp.my12306.entity;

public class User {
	private int userId;
	private String userName;
	private String password;
	private String phoneNo;
	private char realNameCertification;

	public User(int userId, String userName, String password, String phoneNo, char realNameCertification) {
		this.userId = userId;
		this.userName = userName;
		this.password = password;
		this.phoneNo = phoneNo;
		this.realNameCertification = realNameCertification;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public char getRealNameCertification() {
		return realNameCertification;
	}

	public void setRealNameCertification(char realNameCertification) {
		this.realNameCertification = realNameCertification;
	}


}
