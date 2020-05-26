package com.dbpp.my12306.entity;

public class BuyRet {
	private Integer ticketId;
	private Integer mileage;

	public Integer getTicketId() {
		return ticketId;
	}

	public void setTicketId(Integer ticketId) {
		this.ticketId = ticketId;
	}

	public Integer getMileage() {
		return mileage;
	}

	public void setMileage(Integer mileage) {
		this.mileage = mileage;
	}

	@Override
	public String toString() {
		return "BuyRet{" +
				"ticketId=" + ticketId +
				", mileage=" + mileage +
				'}';
	}
}
