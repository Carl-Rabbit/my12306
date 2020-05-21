package com.dbpp.my12306.entity;

import java.sql.Date;
import java.sql.Time;

public class RouteInfo {
	private String trainCode;

	private String fromStation;

	private String toStation;

	private String fromTime;

	private String toTime;

	private Time runtime;

	private Integer mileage;

	private Date departDate;

	private String status;

	private Integer awRest;
	private Integer azRest;
	private Integer bwRest;
	private Integer bzRest;
	private Integer cwRest;
	private Integer czRest;

	private Integer awPrice;
	private Integer azPrice;
	private Integer bwPrice;
	private Integer bzPrice;
	private Integer cwPrice;
	private Integer czPrice;

	public String getTrainCode() {
		return trainCode;
	}

	public void setTrainCode(String trainCode) {
		this.trainCode = trainCode;
	}

	public String getFromStation() {
		return fromStation;
	}

	public void setFromStation(String fromStation) {
		this.fromStation = fromStation;
	}

	public String getToStation() {
		return toStation;
	}

	public void setToStation(String toStation) {
		this.toStation = toStation;
	}

	public String getFromTime() {
		return fromTime;
	}

	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}

	public String getToTime() {
		return toTime;
	}

	public void setToTime(String toTime) {
		this.toTime = toTime;
	}

	public Time getRuntime() {
		return runtime;
	}

	public void setRuntime(Time runtime) {
		this.runtime = runtime;
	}

	public Integer getMileage() {
		return mileage;
	}

	public void setMileage(Integer mileage) {
		this.mileage = mileage;
	}

	public Date getDepartDate() {
		return departDate;
	}

	public void setDepartDate(Date departDate) {
		this.departDate = departDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getAwRest() {
		return awRest;
	}

	public void setAwRest(Integer awRest) {
		this.awRest = awRest;
	}

	public Integer getAzRest() {
		return azRest;
	}

	public void setAzRest(Integer azRest) {
		this.azRest = azRest;
	}

	public Integer getBwRest() {
		return bwRest;
	}

	public void setBwRest(Integer bwRest) {
		this.bwRest = bwRest;
	}

	public Integer getBzRest() {
		return bzRest;
	}

	public void setBzRest(Integer bzRest) {
		this.bzRest = bzRest;
	}

	public Integer getCwRest() {
		return cwRest;
	}

	public void setCwRest(Integer cwRest) {
		this.cwRest = cwRest;
	}

	public Integer getCzRest() {
		return czRest;
	}

	public void setCzRest(Integer czRest) {
		this.czRest = czRest;
	}

	public Integer getAwPrice() {
		return awPrice;
	}

	public void setAwPrice(Integer awPrice) {
		this.awPrice = awPrice;
	}

	public Integer getAzPrice() {
		return azPrice;
	}

	public void setAzPrice(Integer azPrice) {
		this.azPrice = azPrice;
	}

	public Integer getBwPrice() {
		return bwPrice;
	}

	public void setBwPrice(Integer bwPrice) {
		this.bwPrice = bwPrice;
	}

	public Integer getBzPrice() {
		return bzPrice;
	}

	public void setBzPrice(Integer bzPrice) {
		this.bzPrice = bzPrice;
	}

	public Integer getCwPrice() {
		return cwPrice;
	}

	public void setCwPrice(Integer cwPrice) {
		this.cwPrice = cwPrice;
	}

	public Integer getCzPrice() {
		return czPrice;
	}

	public void setCzPrice(Integer czPrice) {
		this.czPrice = czPrice;
	}

	@Override
	public String toString() {
		return "RouteInfo{" +
				"trainCode='" + trainCode + '\'' +
				", fromStation='" + fromStation + '\'' +
				", toStation='" + toStation + '\'' +
				", fromTime='" + fromTime + '\'' +
				", toTime='" + toTime + '\'' +
				", runtime=" + runtime +
				", mileage=" + mileage +
				", departDate=" + departDate +
				", status='" + status + '\'' +
				'}';
	}
}
