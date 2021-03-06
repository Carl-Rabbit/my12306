package com.dbpp.my12306.entity;

public class RouteSchedule {
    private Long routeId;

    private String trainCode;

    private String trainNo;

    private String departDate;

    private String status;

    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }

    public String getTrainCode() {
        return trainCode;
    }

    public void setTrainCode(String trainCode) {
        this.trainCode = trainCode == null ? null : trainCode.trim();
    }

    public String getTrainNo() {
        return trainNo;
    }

    public void setTrainNo(String trainNo) {
        this.trainNo = trainNo == null ? null : trainNo.trim();
    }

    public String getDepartDate() {
        return departDate;
    }

    public void setDepartDate(String departDate) {
        this.departDate = departDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    @Override
    public String toString() {
        return "RouteSchedule{" +
                "routeId=" + routeId +
                ", trainCode='" + trainCode + '\'' +
                ", trainNo='" + trainNo + '\'' +
                ", departDate=" + departDate +
                ", status='" + status + '\'' +
                '}';
    }
}