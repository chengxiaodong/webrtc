package com.tiantian.portal.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.tiantian.application.listener.OnlineUserListener;
import com.tiantian.pojo.TbInform;
import com.tiantian.pojo.TbUser;
import com.tiantian.pojo.utils.TbUserWithBlackDto;
import com.tiantian.portal.pojo.MessageBeanVo;
import com.tiantian.service.BlackService;
import com.tiantian.service.FriendsService;
import com.tiantian.service.InformService;
import com.tiantian.service.MessageService;
import com.tiantian.service.UserService;

import common.util.ResponseResult;
import common.util.TianTianMessage;

/**
 * 实现页面的跳转Controller
 * @author 晓东
 *
 */
@Controller
@RequestMapping("/page")
public class PageController {
	@Autowired
	private UserService userService;
	@Autowired
	private FriendsService friendsService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private InformService informService;
	@Autowired
	private BlackService blackService;
	//用户基本信息展示页面
	@RequestMapping("/userInfo")
	public String userInfo(@RequestParam("uid")Integer uid,Model model) {
		TbUser user = userService.getUserById(uid);
		model.addAttribute("user", user);
		return "userInfo";
	}
	//在线好友展示页面
	@RequestMapping("/friendsOnline")
	public String friendsOnline(HttpSession session,Model model,@RequestParam(required=false)Integer uid,@RequestParam(required=false,defaultValue="1")Integer pageNum) {
		if(StringUtils.isEmpty(uid)) {
			TbUser user = (TbUser) session.getAttribute("user");
			uid=user.getId();
		}
		List<String> allFriendsId = friendsService.findAllFriendsId(uid);
		List<String> onlineFriends = OnlineUserListener.isOnline(allFriendsId, session);
		//过滤掉在黑名单中的好友
		for(int i=0;i<onlineFriends.size();i++) {
			String str=onlineFriends.get(i);
			if(blackService.isBlack(Integer.valueOf(str),uid)!=null) {
				//该oid是uid的黑名单用户
				//从在线好友列表中删除该用户
				onlineFriends.remove(i);
			}
		}
		//过滤掉在黑名单中的好友 end
		List<TbUser> friends=null;
		if(onlineFriends.size()>0) {
			friends=userService.findUsersByUids(onlineFriends);
		}
		
		//构造分页PageInfo   需要携带 currentPage totalPages
		//去取前5条
		List<TbUser> subList=null;
		if(friends!=null&&friends.size()>0) {
			if(friends.size()<=5) {
				subList=friends;
			}else {
				subList = friends.subList((pageNum-1)*5, pageNum*5);
			}
		}
		PageInfo<TbUser> pageInfo=new PageInfo<>(subList);
		pageInfo.setPages(((friends==null?0:friends.size())/5)+1);
		//初始化为第一页
		pageInfo.setPageNum(pageNum);
		model.addAttribute("uid", uid);
		model.addAttribute("pageInfo", pageInfo);
		return "friendsOnline";
	}
	//好友管理页面
	@RequestMapping("/friendsManage")
	public String friendsManage(HttpSession session,Model model,@RequestParam(required=false)Integer uid,@RequestParam(required=false,defaultValue="1")Integer pageNum) {
		if(StringUtils.isEmpty(uid)) {
			TbUser user = (TbUser) session.getAttribute("user");
			uid=user.getId();
		}
		//找出所有的好友信息

		//List<TbUserWithBlackDto> friends = friendsService.findAllFriendsWithBlack(uid);
		 PageInfo<TbUserWithBlackDto> pageInfo = friendsService.findAllFriendsWithBlack(uid, pageNum);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("uid", uid);
		return "friendsManage";
	}
	//基本信息修改页面
	@RequestMapping("/userInfoManager")
	public String userInfoManager() {
		return "userInfoManager";
	}
	//用户注册页面
	@RequestMapping("/register")
    public String register(){
    	
        return "register";

    }
	
	//用户登录页面
		@RequestMapping("/login")
	    public String login(){
	        return "login";
	    }
		
		//用户登录页面
	@RequestMapping("/friendSearch")
	public String friendSearch(@RequestParam("uid")Integer uid,Model model){
		List<TbUser> recommendFriends = friendsService.recommendFriends(uid);
		//推荐前十个
		if(recommendFriends.size()>10) {
			recommendFriends=recommendFriends.subList(0, 9);
		}
		if(recommendFriends!=null) {
			model.addAttribute("recommendFriends", recommendFriends);
		}
		 return "friendSearch";
	}
	
	//消息页面
	@RequestMapping("/message")
	public String message(@RequestParam(required=false)Integer uid,@RequestParam(defaultValue="2")int type,HttpSession session,Model model,@RequestParam(required=false,defaultValue="1")Integer pageNum){
		if(StringUtils.isEmpty(uid)){
			TbUser user=(TbUser) session.getAttribute("user");
			uid=user.getId();
		}
		//version 1不读数据库
		/*List<TianTianMessage> messageList = messageService.getMessage(uid,type);
		
		//vo转换
		List<MessageBeanVo> vos=new ArrayList<>();
		for(TianTianMessage message:messageList) {
			MessageBeanVo vo=new MessageBeanVo();
			vo.setType(message.getType());
			vo.setDate(message.getCreateDate());
			vo.setMessage(message.getMessage());
			//uid表示发送者的id，在vo中ouser表示发送者实体
			TbUser user = userService.getUserById(Integer.valueOf(message.getUid()));
			vo.setOuser(user);
			vos.add(vo);
		}*/
		//读mysql
		//List<TbInform> informs = informService.selectInformsByUid(Integer.valueOf(uid));
		PageInfo pageInfo = informService.selectInformsByUid(uid, pageNum);
		//将PageInfo中的list进行vo转换
		List<MessageBeanVo> vos=new ArrayList<>();
		for(TbInform inform:(List<TbInform>)pageInfo.getList()) {
			MessageBeanVo vo=new MessageBeanVo();
			vo.setType(inform.getType());
			vo.setDate(inform.getCreateDate());
			vo.setMessage(inform.getMessage());
			vo.setReaded(inform.getReaded());
			//uid表示发送者的id，在vo中ouser表示发送者实体  
			TbUser user = userService.getUserById(Integer.valueOf(inform.getOriginal()));
			vo.setOuser(user);
			vo.setMessageId(inform.getId());
			vos.add(vo);
		}
		pageInfo.setList(vos);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("uid", uid);
		return "message";
	}
		
	@RequestMapping("/vedio")
	public String vedio() {
		return "vedio";
	}
}
























