package com.dbpp.my12306.entity;

public class User {
	private int userId;
	private String userName;
	private String password;
	private String phoneNo;
	private String realNameCertification;
	private String available;

	public User(int userId, String userName, String password, String phoneNo, String realNameCertification, String available) {
		this.userId = userId;
		this.userName = userName;
		this.password = password;
		this.phoneNo = phoneNo;
		this.realNameCertification = realNameCertification;
		this.available = available;
	}

	public User(String userName, String password, String phoneNo, String realNameCertification) {
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

	public String getRealNameCertification() {
		return realNameCertification;
	}

	public void setRealNameCertification(String realNameCertification) {
		this.realNameCertification = realNameCertification;
	}

	public String getAvailable() {
		return available;
	}

	public void setAvailable(String available) {
		this.available = available;
	}

	@Override
	public String toString() {
		return "User{" +
				"userId=" + userId +
				", userName='" + userName + '\'' +
				", password='" + password + '\'' +
				", phoneNo='" + phoneNo + '\'' +
				", realNameCertification=" + realNameCertification +
				'}';
	}
}
