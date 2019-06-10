package com.tiantian.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tiantian.service.InformService;

import common.util.ResponseResult;

@Controller
@RequestMapping("/inform")
public class InfromController {
	@Autowired
	private InformService informService;
	
	@RequestMapping("/mark")
	@ResponseBody
	public ResponseResult mark(Integer messageId,Integer readed) {
		informService.mark(messageId, readed);
		return ResponseResult.success("消息标记为已读成功！");
	}
}
