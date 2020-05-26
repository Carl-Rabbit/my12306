package com.dbpp.my12306.entity;

import java.math.BigDecimal;

public class Ticket {
    private Integer ticketId;

    private String entrance;

    private BigDecimal ticketPrice;

    private Integer passengerId;

    private Integer orderId;

    private String ticketKind;

    private String seatInfo;

    private String status;

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

    public BigDecimal getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(BigDecimal ticketPrice) {
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

    public String getSeatInfo() {
        return seatInfo;
    }

    public void setSeatInfo(String seatInfo) {
        if (seatInfo == null) { return; }
        String[] s = seatInfo.substring(1, seatInfo.length() - 1).split(",");

        String cls = "";
        String type = "";
        switch (s[5]) {
            case "A": cls = "特等"; break;
            case "B": cls = "一等"; break;
            case "C": cls = "二等"; break;
        }
        switch (s[6]) {
            case "W": type = "卧票"; break;
            case "Z": type = "坐票"; break;
        }

        if (!"\" \"".equals(s[4])) {
            this.seatInfo = String.format("%s车厢 %s%s %s %s",
                    s[2], s[3], s[4], cls, type);
        } else {
            this.seatInfo = String.format("%s车厢 %s号 %s %s",
                    s[2], s[3], cls, type);
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
                ", seatInfo='" + seatInfo + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}