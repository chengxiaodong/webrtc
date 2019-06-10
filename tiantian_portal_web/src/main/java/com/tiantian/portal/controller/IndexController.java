package com.tiantian.portal.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
/**
 * 首页展示Controller
 * 
 * @author 晓东
 *
 */
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class IndexController {
	
	@RequestMapping("/login")
	public String showIndex(Model model) {
	    
		return "login";
	}
	
}
