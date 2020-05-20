package com.dbpp.my12306.entity;

public class Ticket {
    private Integer ticketId;

    private String entrance;

    private Double ticketPrice;

    private Integer passengerId;

    private Integer orderId;

    private String ticketKind;

    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    public String getEntrance() {
        return entrance;
    }

    public void setEntrance(String entrance) {
        this.entrance = entrance == null ? null : entrance.trim();
    }

    public Double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(Double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public Integer getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(Integer passengerId) {
        this.passengerId = passengerId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getTicketKind() {
        return ticketKind;
    }

    public void setTicketKind(String ticketKind) {
        this.ticketKind = ticketKind == null ? null : ticketKind.trim();
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId=" + ticketId +
                ", entrance='" + entrance + '\'' +
                ", ticketPrice=" + ticketPrice +
                ", passengerId=" + passengerId +
                ", orderId=" + orderId +
                ", ticketKind='" + ticketKind + '\'' +
                '}';
    }
}