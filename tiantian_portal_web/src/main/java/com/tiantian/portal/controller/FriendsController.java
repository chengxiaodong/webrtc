package com.tiantian.portal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tiantian.pojo.TbUser;
import com.tiantian.service.FriendsService;

import common.exception.FriendDeleteException;
import common.util.ResponseResult;

@Controller
@RequestMapping("/friend")
public class FriendsController {
    @Autowired
    private FriendsService friendsService;
    /**
     * 
     * @param uid
     * @param oid
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseResult delete(@RequestParam("uid")int uid,@RequestParam("oid")int oid) {
    	try {
			friendsService.deleteFriend(uid, oid);
			return ResponseResult.success("删除成功！");
		} catch (FriendDeleteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseResult.fail("好友删除失败！");
		}
    }
    
    @RequestMapping("/recommend")
    @ResponseBody
    public ResponseResult recommend(@RequestParam(required=false)int uid) {
    	try {
			List<TbUser> recommendFriends = friendsService.recommendFriends(uid);
			return ResponseResult.success(recommendFriends);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseResult.fail("好友推荐失败！");
		}
    }
    
}
























