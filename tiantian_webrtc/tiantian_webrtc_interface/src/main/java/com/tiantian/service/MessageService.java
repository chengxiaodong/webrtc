package com.tiantian.service;

import java.util.List;

import common.util.TianTianMessage;

/**
 * 为用户的消息提醒，提供存储和访问功能
 * @author 晓东
 *
 */
public interface MessageService {
	//根据uid找message 1=只返回message，并不会删除服务器里的message；2=返回消息并删除服务器里的message
	List<TianTianMessage> getMessage(String uid,int type);
	//保存message,type  响应状态码，3表示此条信息为 好友请求信息 响应状态码，4表示此条信息为 好友请求返回结果信息
	void setMessage(TianTianMessage message,int type);
	//删除所有已保存的message（需要较高权限）
	void clearMessage();
}
