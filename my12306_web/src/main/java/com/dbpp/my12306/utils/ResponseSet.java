package com.dbpp.my12306.utils;

public class ResponseSet<T> {
	private int code;
	private String msg;
	private String detail;
	private T data;


	@Override
	public String toString() {
		return "ResponseSet{" +
				"code=" + code +
				", msg='" + msg + '\'' +
				", detail='" + detail + '\'' +
				", data=" + data +
				'}';
	}

	public int getCode() {
		return code;
	}

	public void setStatus(ResultCode resultCode) {
		this.code = resultCode.getVal();
		this.msg = resultCode.getMsg();
	}

	public String getMsg() {
		return msg;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
