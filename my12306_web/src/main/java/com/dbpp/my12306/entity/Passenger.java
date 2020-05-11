package com.dbpp.my12306.entity;

public class Passenger {
	private int passengerId;
	private int userId;
	private String firstName;
	private String lastName;
	private String kind;
	private String gender;
	private String available;
	private String idNo;

	public Passenger(int passengerId, int userId, String firstName, String lastName,
	                 String kind, String gender, String available, String idNo) {
		this.passengerId = passengerId;
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.kind = kind;
		this.gender = gender;
		this.available = available;
		this.idNo = idNo;
	}


	public Passenger(int userId, String firstName, String lastName,
	                 String kind, String gender, String idNo) {
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.kind = kind;
		this.gender = gender;
		this.idNo = idNo;
	}

	@Override
	public String toString() {
		return "Passenger{" +
				"passengerId=" + passengerId +
				", userId=" + userId +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", kind='" + kind + '\'' +
				", gender='" + gender + '\'' +
				", status='" + available + '\'' +
				", idNo='" + idNo + '\'' +
				'}';
	}


	public int getPassengerId() {
		return passengerId;
	}

	public void setPassengerId(int passengerId) {
		this.passengerId = passengerId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAvailable() {
		return available;
	}

	public void setAvailable(String available) {
		this.available = available;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
}
