package com.dbpp.my12306.entity;

public class BuyQuery {
	private Integer psgId;
	private String trainCode;
	private Integer fromIndex;
	private Integer toIndex;
	private String date;
	private String seatCls;
	private String seatType;
	private String prefer;

	public Integer getPsgId() {
		return psgId;
	}

	public void setPsgId(Integer psgId) {
		this.psgId = psgId;
	}

	public String getTrainCode() {
		return trainCode;
	}

	public void setTrainCode(String trainCode) {
		this.trainCode = trainCode;
	}

	public Integer getFromIndex() {
		return fromIndex;
	}

	public void setFromIndex(Integer fromIndex) {
		this.fromIndex = fromIndex;
	}

	public Integer getToIndex() {
		return toIndex;
	}

	public void setToIndex(Integer toIndex) {
		this.toIndex = toIndex;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getSeatCls() {
		return seatCls;
	}

	public void setSeatCls(String seatCls) {
		this.seatCls = seatCls;
	}

	public String getSeatType() {
		return seatType;
	}

	public void setSeatType(String seatType) {
		this.seatType = seatType;
	}

	public String getPrefer() {
		return prefer;
	}

	public void setPrefer(String prefer) {
		this.prefer = prefer;
	}

	@Override
	public String toString() {
		return "BuyQuery{" +
				"PsgId=" + psgId +
				", trainCode='" + trainCode + '\'' +
				", fromIndex=" + fromIndex +
				", toIndex=" + toIndex +
				", date='" + date + '\'' +
				", seatCls='" + seatCls + '\'' +
				", seatType='" + seatType + '\'' +
				", prefer='" + prefer + '\'' +
				'}';
	}
}
