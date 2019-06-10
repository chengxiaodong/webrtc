package com.tiantian.service;

import java.util.List;

import common.util.ResponseResult;

public interface SmsService {
    //使用JSESSIONID来最为session的识别标识,phoneNumber为信息发送的目标手机号码。
	ResponseResult sendSmsVerifyCode(String JSESSIONID,String phoneNumber);
	String getSmsVerifyCode(String JSESSIONID);
	
}
