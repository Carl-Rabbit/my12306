package com.dbpp.my12306.utils;

public class Date {
	private short year;
	private byte month;
	private byte day;

	public Date(String str) {
		String[] arr = str.split("-");
		year = Short.parseShort(arr[0]);
		month = Byte.parseByte(arr[1]);
		day = Byte.parseByte(arr[2]);
	}

	public int getYear() {
		return year;
	}

	public int getMonth() {
		return month;
	}

	public int getDay() {
		return day;
	}

	@Override
	public String toString() {
		return year + "-" + month + "-" + day;
	}

}
