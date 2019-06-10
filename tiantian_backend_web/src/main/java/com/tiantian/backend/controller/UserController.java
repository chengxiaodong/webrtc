package com.tiantian.backend.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.tiantian.backend.service.UserService;
import com.tiantian.pojo.TbSysuser;
import com.tiantian.pojo.TbUser;
import com.tiantian.pojo.UpdateSysuserException;

import common.constant.UserConstant;
import common.util.ResponseResult;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	@RequestMapping("/login")
	public String login(String loginName,String password,Model model,HttpSession session) {
		//DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		//登录  password 已由jsMD5加密后
		
		TbSysuser sysuser = userService.login(loginName, password);
		if(sysuser==null) {
			//登录失败
			model.addAttribute("errorMsg", "用户名或密码错误");
			return "login";
		}
		session.setAttribute("sysuser", sysuser);
		model.addAttribute("sysuser", sysuser);
		return "main";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		//登录
		session.invalidate();
		return "login";
	}
	
	//用户修改
	@RequestMapping("/sysuser/update")
	@ResponseBody
	public ResponseResult update(HttpSession session,TbSysuser tbSysuser) {
		//跟新用户信息 
		if(tbSysuser.getPassword()!=null) {
			//MD5加密
			String passwordMD5=DigestUtils.md5DigestAsHex(tbSysuser.getPassword().getBytes());
			tbSysuser.setPassword(passwordMD5);
		}
		try {
			userService.updateByRecord(tbSysuser);
		} catch (UpdateSysuserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseResult.fail("信息修改失败");
		}
		return ResponseResult.success(tbSysuser);
	}
	
	//，删除
	@RequestMapping("/sysuser/delete")
	@ResponseBody
	public ResponseResult delete(HttpSession session,Integer uid) {
		//删除
		try {
			userService.deleteById(uid);
		}catch (Exception e) {
			ResponseResult.fail("删除失败，请重试");
		}
		//将uid返回，用于js移除uid所在行
		return ResponseResult.success(uid);
	}
	
	
	//，删除
		@RequestMapping("/sysuser/add")
		public String add(HttpSession session,TbSysuser tbSysuser,Model model) {
			//登录
			//跟新用户信息
			System.out.println(tbSysuser.toString());
			if(tbSysuser.getPassword()!=null) {
				//MD5加密
				String passwordMD5=DigestUtils.md5DigestAsHex(tbSysuser.getPassword().getBytes());
				tbSysuser.setPassword(passwordMD5);
			}
			try {
				userService.addSysuser(tbSysuser);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				model.addAttribute("errorMsg", "添加管理员失败");
				return "forward:/page/superuser/findAll";
			}
			//刷新页面
			
			return "forward:/page/superuser/findAll";
		}
		
		
		
		
		//用户修改
		@RequestMapping("/user/update")
		@ResponseBody
		public ResponseResult userUpdate(HttpSession session,TbSysuser tbSysuser) {
			//跟新用户信息 
			if(tbSysuser.getPassword()!=null) {
				//MD5加密
				String passwordMD5=DigestUtils.md5DigestAsHex(tbSysuser.getPassword().getBytes());
				tbSysuser.setPassword(passwordMD5);
			}
			try {
				userService.updateByRecord(tbSysuser);
			} catch (UpdateSysuserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return ResponseResult.fail("信息修改失败");
			}
			return ResponseResult.success(tbSysuser);
		}
		
		//，删除
		@RequestMapping("/user/delete")
		@ResponseBody
		public ResponseResult userDelete(HttpSession session,Integer uid) {
			//删除
			try {
				userService.deleteUserById(uid);
			}catch (Exception e) {
				ResponseResult.fail("删除失败，请重试");
			}
			//将uid返回，用于js移除uid所在行
			return ResponseResult.success(uid);
		}
		
		
		//，删除
			@RequestMapping("/user/add")
			public String userAdd(HttpSession session,TbUser tbUser,HttpServletRequest request ) {
				//登录
		    	//设置默认头像路径
		    	
		    	tbUser.setProfilephoto("http://1.jpg");
		    	tbUser.setCreatedate(new Date());
		    	tbUser.setUpdatedate(new Date());
		    	tbUser.setOnlinestatus(UserConstant.USER_VALID);
				//跟新用户信息
				//System.out.println(tbUser.toString());
				if(tbUser.getPassword()!=null) {
					//MD5加密
					String passwordMD5=DigestUtils.md5DigestAsHex(tbUser.getPassword().getBytes());
					tbUser.setPassword(passwordMD5);
				}
				try {
					userService.addUser(tbUser);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					request.setAttribute("errorMsg", "添加用户失败");
					return "forward:/page/sysuser/findAll";
				}
				//刷新页面
				//model.addAttribute("errorMsg", "添加用户成功");
				return "forward:/page/sysuser/findAll";
			}
		
		
}




































