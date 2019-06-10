package com.tiantian.portal.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tiantian.service.SmsService;

import common.util.ResponseResult;

/**
 * 发送短信Controller
 * @author 晓东
 *
 */
@Controller
@RequestMapping("/sms")
public class SmsController {
	@Autowired SmsService smsService;
	
	@RequestMapping("/sendSms")
	@ResponseBody
	public ResponseResult sendSms(HttpSession session,String phoneNumber) {
		ResponseResult result = smsService.sendSmsVerifyCode(session.getId(), phoneNumber);
		return result;
	}
	
	@RequestMapping("/getVerifyCode")
	@ResponseBody
	public ResponseResult getVerifyCode(HttpSession session) {
		String verifyCode = smsService.getSmsVerifyCode(session.getId());
		if(null==verifyCode||"".equals(verifyCode)) {
			return ResponseResult.fail("短信验证码错误，或已过期，请重试！");
		}
		return ResponseResult.success(verifyCode);
	}
	
}















































