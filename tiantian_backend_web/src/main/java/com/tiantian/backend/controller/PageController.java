package com.tiantian.backend.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tiantian.backend.service.UserService;
import com.tiantian.pojo.TbSysuser;
import com.tiantian.pojo.TbUser;

@Controller
@RequestMapping("/page")
public class PageController {
	@Autowired
	private WebConfig webConfig;
	@Autowired
	private UserService userService;
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	
	@RequestMapping("/userManage")
	public String userManage() {
		return "userManager";
	}
	
	@RequestMapping("/configinfo")
	public String configinfo(Model model) {
		model.addAttribute("webconfig", webConfig);
		return "configinfo";
	}
	
	@RequestMapping("/sysuser/findAll")
	public String sysuserFindAll(Model model,HttpServletRequest request) {
		//查出所有普通用户
		List<TbUser> users = userService.findAllUsers();
		System.out.println("errorMsg"+request.getAttribute("errorMsg"));
		model.addAttribute("users", users);
		return "sysuserManage";
	}
	
	@RequestMapping("/superuser/findAll")
	public String superuserFindAll(Model model) {
		
		List<TbSysuser>  sysusers= userService.findAllSysuser();
		model.addAttribute("superuser", sysusers);
		return "superuserManage";
	}
}
