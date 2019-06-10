package com.njit.vo;

import java.io.Serializable;

public class SocketMessageVo implements Serializable{
	private int type;
	private String message;
	private String data;
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
}
