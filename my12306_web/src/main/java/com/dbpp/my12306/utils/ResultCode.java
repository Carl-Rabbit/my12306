package com.dbpp.my12306.utils;

/*
Refer from
https://blog.csdn.net/simpledate/article/details/56834657
 */
public enum ResultCode {

	/** 成功 */
	SUCCESS(200, "success"),

	/** 操作失败 */
	FAIL(205, "operation failed"),

	/** 数据已存在 */
	SUCCESS_IS_HAVE(208, "data already exits"),

	/** 没有结果 */
	NO_RESULT(911, "no result"),

	/** 没有登录 */
	NOT_LOGIN(600, "not login"),

	/** 发生异常 */
	EXCEPTION(401, "exception occur"),

	/** 系统错误 */
	SYS_ERROR(402, "system error"),

	/** 参数错误 */
	PARAMS_ERROR(403, "params error"),

	/** 不支持或已经废弃 */
	NOT_SUPPORTED(410, "not supported"),

	/** AuthCode错误 */
	INVALID_AUTHCODE(444, "invalid AuthCode"),

	/** 太频繁的调用 */
	TOO_FREQUENT(445, "too frequent"),

	/** 未知的错误 */
	UNKNOWN_ERROR(499, "unknown error"),

	/** 未设置方法 */
	NOT_METHOD(4004, "no match method");

	private int val;
	private String msg;

	ResultCode(int value, String msg){
		this.val = value;
		this.msg = msg;
	}

	public int getVal() {
		return val;
	}

	public String getMsg() {
		return msg;
	}


}
