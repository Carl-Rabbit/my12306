package com.dbpp.my12306.entity;

import java.util.Date;

public class TimeDetail {
    private Integer timeDetailId;

    private String trainCode;

    private Integer stationId;

    private Integer stationIndex;

    private Date arriveTime;

    private Date leaveTime;

    private Object runtime;

    private Integer mileage;

    public Integer getTimeDetailId() {
        return timeDetailId;
    }

    public void setTimeDetailId(Integer timeDetailId) {
        this.timeDetailId = timeDetailId;
    }

    public String getTrainCode() {
        return trainCode;
    }

    public void setTrainCode(String trainCode) {
        this.trainCode = trainCode == null ? null : trainCode.trim();
    }

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    public Integer getStationIndex() {
        return stationIndex;
    }

    public void setStationIndex(Integer stationIndex) {
        this.stationIndex = stationIndex;
    }

    public Date getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(Date arriveTime) {
        this.arriveTime = arriveTime;
    }

    public Date getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(Date leaveTime) {
        this.leaveTime = leaveTime;
    }

    public Object getRuntime() {
        return runtime;
    }

    public void setRuntime(Object runtime) {
        this.runtime = runtime;
    }

    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    @Override
    public String toString() {
        return "TimeDetail{" +
                "timeDetailId=" + timeDetailId +
                ", trainCode='" + trainCode + '\'' +
                ", stationId=" + stationId +
                ", stationIndex=" + stationIndex +
                ", arriveTime=" + arriveTime +
                ", leaveTime=" + leaveTime +
                ", runtime=" + runtime +
                ", mileage=" + mileage +
                '}';
    }
}