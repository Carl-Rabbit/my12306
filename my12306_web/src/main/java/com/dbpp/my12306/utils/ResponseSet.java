package com.dbpp.my12306.utils;

public class ResponseSet<T> {
	private int status;
	private String message;
	private String detail;
	private T data;

	public ResponseSet() {
	}

	public ResponseSet(T data) {
		this.setStatus(ResultCode.SUCCESS);
		this.data = data;
	}

	public ResponseSet(ResultCode resultCode, String detail, T data) {
		this.setStatus(resultCode);
		this.detail = detail;
		this.data = data;
	}

	@Override
	public String toString() {
		return "ResponseSet{" +
				"status=" + status +
				", message='" + message + '\'' +
				", detail='" + detail + '\'' +
				", data=" + data +
				'}';
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(ResultCode resultCode) {
		this.status = resultCode.getCode();
		this.message = resultCode.getMsg();
	}

	public String getMessage() {
		return message;
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
