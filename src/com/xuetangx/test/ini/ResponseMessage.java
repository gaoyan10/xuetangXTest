package com.xuetangx.test.ini;

public class ResponseMessage {
	public String message;
	public int code;
	public ResponseMessage(String msg, int code) {
		this.message = msg;
		this.code = code;
	}
}
