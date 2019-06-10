package com.tiantian.portal.pojo;

import java.io.Serializable;

import com.tiantian.pojo.TbUser;

public class MessageBeanVo  implements Serializable{
	private Integer messageId;
	public Integer getMessageId() {
		return messageId;
	}
	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}
	//ouser:发送者
	private TbUser ouser;
	//创建的时间
	private String date;
	public TbUser getOuser() {
		return ouser;
	}
	public void setOuser(TbUser ouser) {
		this.ouser = ouser;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
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
	//消息的类型
	private int type;
	//消息
	private String message;
	//0未读 1 已读
	private int readed;
	public int getReaded() {
		return readed;
	}
	public void setReaded(int readed) {
		this.readed = readed;
	}
}
