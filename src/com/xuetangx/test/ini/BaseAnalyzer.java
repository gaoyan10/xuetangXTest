package com.xuetangx.test.ini;


public interface BaseAnalyzer {
	public final int SUC = 0;
	public final int NET_ERR = -1;
	public final int NO_AUTH = -2;
	public Object analyseJson(String json, int code);
	public String createJson();
	public ResponseMessage connect();
}
