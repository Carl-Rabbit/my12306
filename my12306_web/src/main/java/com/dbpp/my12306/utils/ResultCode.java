package com.dbpp.my12306.utils;

/*
Refer from
https://blog.csdn.net/simpledate/article/details/56834657
 */
public enum ResultCode {

	/** 成功 */
	SUCCESS(200, "success"),

	/** 实例已创建 */
	CREATED(201, "created"),

	/** 没有结果 */
	NO_RESULT(204, "no result"),

	/** 数据已存在 */
	SUCCESS_IS_HAVE(208, "data already exits"),

	/** 操作失败 */
	FAIL(299, "operation failed"),

	/** AuthCode错误 */
	INVALID_AUTH(401, "unauthorized"),

	/** 系统错误 */
	SYS_ERROR(402, "system error"),

	/** 参数错误 */
	PARAMS_ERROR(403, "params error"),

	/** 参数限制错误 */
	CONS_ERROR(405, "constraints error"),

	/** 不支持或已经废弃 */
	NOT_SUPPORTED(410, "not supported"),

	/** 太频繁的调用 */
	TOO_FREQUENT(445, "too frequent"),

	/** 未知的错误 */
	UNKNOWN_ERROR(499, "unknown error"),

	/** 发生异常 */
	EXCEPTION(500, "exception occur");

	private int val;
	private String msg;

	ResultCode(int value, String msg){
		this.val = value;
		this.msg = msg;
	}

	public int getCode() {
		return val;
	}

	public String getMsg() {
		return msg;
	}


}
