package com.tiantian.portal.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tiantian.application.listener.OnlineUserListener;
import com.tiantian.pojo.TbFriends;
import com.tiantian.pojo.TbInform;
import com.tiantian.pojo.TbUser;
import com.tiantian.service.FriendsService;
import com.tiantian.service.InformService;
import com.tiantian.service.MessageService;
import com.tiantian.service.UserService;

import common.exception.TbFriendExsitException;
import common.util.FastJsonUtils;
import common.util.ResponseResult;
import common.util.TianTianMessage;

/**
 * 浏览器与服务器Message传输Controller
 * 
 * @author 晓东
 *
 */
@Controller
@RequestMapping("/message")
public class TianTianMessageController {
	@Autowired
	private MessageService messageService;
	@Autowired
	private FriendsService friendsService;
	@Autowired
	private UserService userService;
	@Autowired
	private InformService informService;
	/**
	 * 
	 * @param uid
	 * @param type      1=只返回message，并不会删除服务器里的message；2=返回消息并删除服务器里的message
	 * @param session
	 * @return
	 */
	@RequestMapping("/getMessage")
	@ResponseBody
	public ResponseResult getMessage(@RequestParam(required=false)String uid,@RequestParam(defaultValue="1")int type,HttpSession session) {
		if(StringUtils.isEmpty(uid)){
			TbUser user=(TbUser) session.getAttribute("user");
			try {
				uid=user.getId()+"";
			}catch (Exception e) {
				//
				System.out.println("还未登录不能发消息！");
			}
		}
		//type表明是否获取消息后删除消息
		List<TianTianMessage> messageList = messageService.getMessage(uid,type);
		return ResponseResult.success(messageList);
	}
	/**
	 * 
	 * @param message
	 * @param session
	 * @param type 响应状态码，3表示此条信息为 好友请求信息 响应状态码，4表示此条信息为 好友请求返回结果信息 5表示为普通消息6视频请求信息
	 * @return
	 * @throws TbFriendExsitException 
	 * 
	 * 
	 * 
	 * jsonp 跨域请求
	 * 	@ResponseBody
	public String getUserByToken(@PathVariable("token")String token,String callback) {
		TaotaoResult taotaoResult = userService.getUserByToken(token);
		//判断是否为jsonp请求
				if(!StringUtils.isEmpty(callback)) {
					return callback+"("+JsonUtils.objectToJson(taotaoResult)+");";
				}
		return JsonUtils.objectToJson(taotaoResult);
	}
	 */
	@RequestMapping(value="/sendMessage",
			//以文件的格式返回数据
			produces=MediaType.APPLICATION_JSON_UTF8_VALUE
			)
	@ResponseBody
	public ResponseResult sendMessage(TianTianMessage message,HttpSession session,
			@RequestParam(defaultValue="5")int type,@RequestParam(required=false)boolean accept,String callback)  {
		if(StringUtils.isEmpty(message.getUid())||StringUtils.isEmpty(message.getOid())) {
			//过滤测试信息
			return null;
		}
		
		//无uid，设置值为当前登录的user id
		if(message!=null&&StringUtils.isEmpty(message.getUid())) {
			TbUser user=(TbUser) session.getAttribute("user");
			if(user!=null)
			message.setUid(user.getId()+"");
		}
		//保存message到消息列表
			if(message.getType()!=0) {
			//过滤掉测试消息
				if(type==6||type==7) {
					//视频请求====》直接发送给在线的用户浏览器，不再保存到mysql
					//将uid的信息包装到message中
					TbUser user = userService.getUserById(Integer.valueOf(message.getUid()));
					//放入message中,protect中包含了对方的图片http地址
					message.setProtect(user);
					messageService.setMessage(message,type);
				}else if(type==3) {
					//好友请求
					boolean oidIsOnline = OnlineUserListener.isOnline(message.getOid(),session);
					if(oidIsOnline) {
						//对方已在线
						messageService.setMessage(message,type);
					}
					//************************************数据库保存*************************
					TbInform inform=new TbInform();
					inform.setOriginal(Integer.valueOf(message.getUid()));
					inform.setTarget(Integer.valueOf(message.getOid()));
					//
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
					String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
					
					inform.setCreateDate(date);
					inform.setMessage(message.getMessage());
					inform.setType(3);
					//保存请求记录到mysql
					informService.insertInfrom(inform);
					
					
				}else if(type==4) {
					//好友请求结果
					//好友添加信息，且接受请求保存到tbuser表
					if(accept) {
						//************************************数据库保存*************************
						//保存到mysql数据表
						TbFriends tbFriends=new TbFriends();
						tbFriends.setUseid(Integer.parseInt(message.getUid()));
						tbFriends.setFriendid(Integer.parseInt(message.getOid()));
						TbFriends tbFriends2=new TbFriends();
						tbFriends2.setUseid(Integer.parseInt(message.getOid()));
						tbFriends2.setFriendid(Integer.parseInt(message.getUid()));
						try {
							//保存两条记录
							//uid---》oid
							friendsService.insertFreind(tbFriends);
							//oid--->uid
							friendsService.insertFreind(tbFriends2);
						} catch (TbFriendExsitException e) {
							e.printStackTrace();
						}
						
					}
					//好友请求
					boolean oidIsOnline = OnlineUserListener.isOnline(message.getOid(),session);
					if(oidIsOnline) {
						//对方已在线
						messageService.setMessage(message,type);
					}
					//************************************数据库保存*************************
					//保存请求记录
					TbInform inform=new TbInform();
					inform.setOriginal(Integer.valueOf(message.getUid()));
					inform.setTarget(Integer.valueOf(message.getOid()));
					
					//
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
					String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
					
					inform.setCreateDate(date);
					inform.setMessage(message.getMessage());
					inform.setType(4);
					//保存请求记录到mysql
					informService.insertInfrom(inform);
					
				}
				System.out.println("message :"+message);
			}
		
	
	
			return ResponseResult.success("消息发送成功！");
	
		
	}
	
	
	@RequestMapping("/clearAllMessage")
	@ResponseBody
	public ResponseResult clearAllMessage() {
		messageService.clearMessage();
		return ResponseResult.success("清空所有消息成功！");
	}
}
