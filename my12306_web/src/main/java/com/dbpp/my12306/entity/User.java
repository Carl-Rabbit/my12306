package com.dbpp.my12306.entity;

public class User {
	private int userId;
	private String userName;
	private String password;
	private String phoneNo;
	private String kind;
	private String available;
	private String realNameCertification;

	public User(int userId, String userName, String password, String phoneNo,
	            String kind, String available, String realNameCertification) {
		this.userId = userId;
		this.userName = userName;
		this.password = password;
		this.phoneNo = phoneNo;
		this.kind = kind;
		this.available = available;
		this.realNameCertification = realNameCertification;
	}

	public User(String userName, String password, String phoneNo, String kind,
	            String realNameCertification) {
		this.userName = userName;
		this.password = password;
		this.phoneNo = phoneNo;
		this.kind = kind;
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

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
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
