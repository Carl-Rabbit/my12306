package com.dbpp.my12306.entity;

import java.math.BigDecimal;
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

	private BigDecimal awPrice;
	private BigDecimal azPrice;
	private BigDecimal bwPrice;
	private BigDecimal bzPrice;
	private BigDecimal cwPrice;
	private BigDecimal czPrice;

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

	public BigDecimal getAwPrice() {
		return awPrice;
	}

	public void setAwPrice(BigDecimal awPrice) {
		this.awPrice = awPrice;
	}

	public BigDecimal getAzPrice() {
		return azPrice;
	}

	public void setAzPrice(BigDecimal azPrice) {
		this.azPrice = azPrice;
	}

	public BigDecimal getBwPrice() {
		return bwPrice;
	}

	public void setBwPrice(BigDecimal bwPrice) {
		this.bwPrice = bwPrice;
	}

	public BigDecimal getBzPrice() {
		return bzPrice;
	}

	public void setBzPrice(BigDecimal bzPrice) {
		this.bzPrice = bzPrice;
	}

	public BigDecimal getCwPrice() {
		return cwPrice;
	}

	public void setCwPrice(BigDecimal cwPrice) {
		this.cwPrice = cwPrice;
	}

	public BigDecimal getCzPrice() {
		return czPrice;
	}

	public void setCzPrice(BigDecimal czPrice) {
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
