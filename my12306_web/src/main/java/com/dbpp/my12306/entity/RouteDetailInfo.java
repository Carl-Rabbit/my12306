package com.dbpp.my12306.entity;

public class RouteDetailInfo {
	private Integer stationIndex;
	private String stationName;
	private String arriveTime;
	private String leaveTime;
	private String stayTime;

	public Integer getStationIndex() {
		return stationIndex;
	}

	public void setStationIndex(Integer stationIndex) {
		this.stationIndex = stationIndex;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getLeaveTime() {
		return leaveTime;
	}

	public void setLeaveTime(String leaveTime) {
		this.leaveTime = leaveTime;
	}

	public String getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
	}

	public String getStayTime() {
		return stayTime;
	}

	public void setStayTime(String stayTime) {
		this.stayTime = stayTime;
	}

	@Override
	public String toString() {
		return "RouteDetailInfo{" +
				"stationIndex=" + stationIndex +
				", stationName='" + stationName + '\'' +
				", leaveTime='" + leaveTime + '\'' +
				", arriveTime='" + arriveTime + '\'' +
				", stayTime='" + stayTime + '\'' +
				'}';
	}
}
