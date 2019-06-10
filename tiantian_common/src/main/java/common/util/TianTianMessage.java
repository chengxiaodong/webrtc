package common.util;

import java.io.Serializable;
import java.util.Date;

/**
 * type
 * 3表示此条信息为 好友请求信息
 * 4表示此条信息为 好友请求返回结果信息
 * 5表示此条信息为 普通信息
 * 6表示视频请求消息
 * @author 晓东
 *
 */
public class TianTianMessage implements Serializable{
	//来源
	private String uid;
	//目标
	private String oid;
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	//消息类型
	private String message;
	
	
	private int type;
	public int getType() {
		return type;
	}
	@Override
	public String toString() {
		return "TianTianMessage [uid=" + uid + ", oid=" + oid + ", message=" + message + ", type=" + type
				+ ", createDate=" + createDate + ", protect=" + protect + ", getUid()=" + getUid() + ", getOid()="
				+ getOid() + ", getMessage()=" + getMessage() + ", getType()=" + getType() + ", getCreateDate()="
				+ getCreateDate() + ", getProtect()=" + getProtect() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}
	public void setType(int type) {
		this.type = type;
	}
	public String createDate;
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	//暂留字段
	private Object protect;
	public Object getProtect() {
		return protect;
	}
	public void setProtect(Object protect) {
		this.protect = protect;
	}
}
