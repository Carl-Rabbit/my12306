package com.dbpp.my12306.entity;

public class Train {
    private String trainNo;

    private String trainKind;

    private String status;

    public String getTrainNo() {
        return trainNo;
    }

    public void setTrainNo(String trainNo) {
        this.trainNo = trainNo == null ? null : trainNo.trim();
    }

    public String getTrainKind() {
        return trainKind;
    }

    public void setTrainKind(String trainKind) {
        this.trainKind = trainKind == null ? null : trainKind.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    @Override
    public String toString() {
        return "Train{" +
                "trainNo='" + trainNo + '\'' +
                ", trainKind='" + trainKind + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}