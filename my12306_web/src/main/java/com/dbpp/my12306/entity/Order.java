package com.dbpp.my12306.entity;

import java.sql.Timestamp;

public class Order {
	private int orderId;
	private int userId;
	private Timestamp createTime;
	private String orderStatus;
	private String kind;

	public Order(int userId, Timestamp createTime, String orderStatus, String kind) {
		this.userId = userId;
		this.createTime = createTime;
		this.orderStatus = orderStatus;
		this.kind = kind;
	}

	@Override
	public String toString() {
		return "Order{" +
				"orderId=" + orderId +
				", userId=" + userId +
				", createTime=" + createTime +
				", orderStatus='" + orderStatus + '\'' +
				", kind='" + kind + '\'' +
				'}';
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}
}
