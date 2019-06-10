package com.tiantian.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tiantian.pojo.TbBlack;
import com.tiantian.service.BlackService;

import common.exception.BlackAddException;
import common.exception.CancelBlackException;
import common.util.ResponseResult;

@Controller
@RequestMapping("/black")
public class BlackController {
	@Autowired
	private BlackService blackService;
	
	@RequestMapping("/isBlack")
	@ResponseBody
	public ResponseResult isBlack(Integer uid,Integer oid) {
		TbBlack blacker = blackService.isBlack(uid, oid);
		return blacker==null?ResponseResult.success(false):ResponseResult.success(false);
	}
	
	@RequestMapping("/insertBlack")
	@ResponseBody
	public ResponseResult insertBlack(Integer uid,Integer oid) {
		try {
			blackService.insertBlack(uid, oid);
			return ResponseResult.success("已添加到黑名单");
		} catch (BlackAddException e) {
			// TODO Auto-generated catch block
			return ResponseResult.fail("添加黑名单失败");
		}
		
	}
	
	@RequestMapping("/cancelBlack")
	@ResponseBody
	public ResponseResult cancelBlack(Integer id) {
		try {
			blackService.cancelBlack(id);
			return ResponseResult.success("已取消拉黑");
		} catch (CancelBlackException e) {
			// TODO Auto-generated catch block
			return ResponseResult.fail(e.getMessage());
		}
		
	}
}




























