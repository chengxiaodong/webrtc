package com.tiantian.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tiantian.service.MessageService;

import common.constant.TianTianMessageConstant;
import common.util.TianTianMessage;

/**
 * 消息存储的具体实现类
 * @author 晓东
 *
 */
@Service
public class MessageServiceImpl implements MessageService{
	public static List<TianTianMessage> tianTianMessageList;
	static {
		tianTianMessageList=new ArrayList<TianTianMessage>();
		tianTianMessageList=	(List<TianTianMessage>)Collections.synchronizedList(tianTianMessageList);
	}
	@Override
	public   List<TianTianMessage> getMessage(String uid,int type) {
		// 根据uid查找message
		List result=new ArrayList<TianTianMessage>();
		
		//测试
		/*for(TianTianMessage message:tianTianMessageList) {
			if(message.getType()==6) {
				System.out.println("^^^^^^^^^^^^^^^视频请求消息^^^^^^^^^"+message);
			}
		}*/
		
		//会报java.util.ConcurrentModificationException错误
		/*for(TianTianMessage message:tianTianMessageList) {
			if(message.getOid().equals(uid)) {
				//添加到消息返回列表
				result.add(message);
				//从未读列表中删除
				tianTianMessageList.remove(message);
			}
		}*/
	//System.out.println("get message:"+"uid="+uid+"....");
		
			Iterator<TianTianMessage> iterator = tianTianMessageList.iterator();
			synchronized (iterator) {

				while(iterator.hasNext()){
	             TianTianMessage message = iterator.next();
	             System.out.println("foreach messageList:"+message);
	            if(message.getOid().equals(uid)) {
	            	//过滤掉超时的视频请求
	            	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
	            	Date date1=null;
					try {
						date1 = df.parse(message.getCreateDate());
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            	Date date2=new Date();
	            	int ss=date2.getSeconds()-date1.getSeconds();
	            	//添加到消息返回列表
	            	if(ss<30)
					result.add(message);
					//是否移除消息,6  3      4为视频请求消息只发一次；7视频请求后被拒绝
					if(type!=TianTianMessageConstant.MESSAGE_TYPE_FRIENDS_ADD_GET||message.getType()==6||message.getType()==7||message.getType()==3||message.getType()==4)
	                iterator.remove();   //注意这个地方
	            }
	        	}
			}
	//System.out.println("get message:"+"uid="+uid+" message size:"+result.size());
		
        
		return result;
	} 
/**
 * type  响应状态码，3表示此条信息为 好友请求信息 响应状态码，4表示此条信息为 好友请求返回结果信息
 * @param message
 * @param type
 */
	@Override
	public  void setMessage(TianTianMessage message,int type) {
		// 消息存储
		if(message.getType()!=0&&!StringUtils.isEmpty(message.getUid())&&!StringUtils.isEmpty(message.getOid())) {
			//过滤掉测试消息
		System.out.println("------------------MessageServiceImpl--------sendMessage:"+message);
		message.setType(type);
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
		message.setCreateDate(date);
		tianTianMessageList.add(message);
		}
	}

	@Override
	public void clearMessage() {
		//清空所有Message
		tianTianMessageList.clear();
	}

}
