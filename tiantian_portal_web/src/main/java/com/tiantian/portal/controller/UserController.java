package com.tiantian.portal.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.dubbo.config.support.Parameter;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tiantian.application.listener.OnlineUserListener;
import com.tiantian.pojo.TbInform;
import com.tiantian.pojo.TbUser;
import com.tiantian.pojo.utils.FriendSearchResultBean;
import com.tiantian.portal.pojo.RegisterBeanVo;
import com.tiantian.service.FriendsService;
import com.tiantian.service.InformService;
import com.tiantian.service.SmsService;
import com.tiantian.service.UserService;

import common.constant.PaginationConstant;
import common.constant.ResponseStatusConstant;
import common.constant.ServerConstant;
import common.constant.UserConstant;
import common.exception.FriendDeleteException;
import common.exception.UserNotExistException;
import common.util.ResponseResult;

/**
 * Created by 晓东 on 2019/2/28.
 * QQ:2774398338
 */
@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private InformService informService;
	@Autowired
	private SmsService smsService;
	/**
	 * 
	 * @param loginName
	 * @param password
	 * @param session
	 * @param model
	 * @return
	 */
    @RequestMapping("/login")
    public String login(String loginName,String password,String code,HttpSession session,Model model){
        try {
        //DigestUtils.md5DigestAsHex(tbSysuser.getPassword().getBytes());
        	//MD5加密====》后期浏览器在js中加密后传过来
        /*	System.out.println("loginName:"+loginName+";password:"+password);*/
        	if(code==null||!code.equals(session.getAttribute("verificationCode"))) {
        		 model.addAttribute("errorMsg","验证码错误");
                 return "login";
        	}
        	TbUser user=userService.login(loginName,password);
        	
            session.setAttribute("user",user);
            model.addAttribute("user", user);
            //把uid放到servletContext
            OnlineUserListener.addOnline(user.getId(), session);
          /*  //将用户的在线情况保存到applicationContext
            List onlineList = (List) session.getServletContext().getAttribute("onlineUserList");
            if(onlineList==null)
            	onlineList=new ArrayList<String>();
            if(!onlineList.contains(user.getId()))
            	onlineList.add(user.getId()+"");
            session.getServletContext().setAttribute("onlineUserList",onlineList);*/
            //将用户在离线时发来的未读的消息推送给他
            System.out.println("user.getId():"+user.getId());
            Integer unreadCount=informService.selectUnReadInformsByUid(user.getId());
            model.addAttribute("informsize", unreadCount==null?0:unreadCount);
            return "main";
        } catch (UserNotExistException e) {
            model.addAttribute("errorMsg",e.getMessage());
            return "login";
            //e.printStackTrace();
        }

    }
    /**
     *  //退出账号
     * @param session
     * @return
     */
   
    @RequestMapping("/logout")
    public String logout(HttpSession session){
       // session.removeAttribute("user");
        //触发HttpSessionListener的destory方法
        session.invalidate();
        return "login";

    }
    /**
     * 
     * @param vo
     * @param request
     * @return
     */
    @RequestMapping("/register")
    @ResponseBody
    public ResponseResult register(RegisterBeanVo vo,HttpServletRequest request,HttpSession session) {
    	System.out.println(vo);
    	if(vo==null||vo.getEmail()==null||vo.getName()==null||vo.getPassword1()==null||vo.getPassword2()==null||vo.getPhone()==null) {
    		return ResponseResult.fail("请将用户注册表填写完整！");
    	}
    	if(!vo.getPassword1().equals(vo.getPassword2())) {
    		return ResponseResult.fail("输入的两次密码不一致！");
    	}
    	if(!vo.getVerifyCode().trim().equals(smsService.getSmsVerifyCode(session.getId()))) {
    		return ResponseResult.fail("手机短信验证码错误！");
    	}
    	TbUser user=new TbUser();
    	
    	user.setUsername(vo.getName());
    	user.setEmail(vo.getEmail());
    	user.setOnlinestatus(UserConstant.USER_VALID);
    	//MD5加密
    	String passwordMD5=DigestUtils.md5DigestAsHex(vo.getPassword1().getBytes());
    	user.setPassword(passwordMD5);
    	user.setPhone(vo.getPhone());
    	//设置默认头像路径
    	
    	user.setProfilephoto("https://"+ServerConstant.imageServerIp+":"+ServerConstant.imageServerPort+"/headIcons/1.jpg");
    	
    	user.setCreatedate(new Date());
    	user.setUpdatedate(new Date());
    	
    	ResponseResult responseResult=userService.canAddUser(user);
    	//判断user中的username、email、phone
    	if(responseResult.getStatus()==ResponseStatusConstant.RESPONSE_STATUS_FAIL) {
    		return responseResult;
    	}
    	//File file=new File();
    	//向数据库保存用户数据
    	/**
    	 * 添加<selectKey>将新插入的id值设置到user中
    	 */
    	int id=userService.addUser(user);
    	//*************fileupload 保存图片***********
    	if(!StringUtils.isEmpty(vo.getHeadIcon().getOriginalFilename() )) {
    	try {
    		//绝对路径地址，用于存储到磁盘
    	String realPath=request.getSession().getServletContext().getRealPath("/headIcons");
    	//图片的http路径用于，http访问图片
    	String httpPathPre="https://"+ServerConstant.imageServerIp+":"+ServerConstant.imageServerPort+"/headIcons";
    	MultipartFile iconFile=vo.getHeadIcon();
    	String originalName=iconFile.getOriginalFilename();
    	//System.out.println(originalName+"|"+iconFile.getName());
    	String subfix= originalName.substring(originalName.lastIndexOf("."));
    	String headIcoPath=realPath+"\\"+id+subfix;
    	//System.out.println("headIcoPath"+headIcoPath);
    	File headfile=new File(headIcoPath);
    	if(headfile.exists()) {
    		//头像修改时调用
    	}else {
    		headfile.getParentFile().mkdirs();
    		headfile.createNewFile();
    		
    	}
    	FileOutputStream out=new FileOutputStream(headfile);
    	out.write(iconFile.getBytes());
    	out.close();
    	
    	//保存到mysql  http://....../id.jpg(png)
    	String httpHeadIcoPath=httpPathPre+"/"+id+subfix;
    	userService.updateProfilePhoto(id, httpHeadIcoPath);
    	}catch (Exception e) {
    		System.out.println("图片保存到服务器失败！");
    		e.printStackTrace();
    	}
    	}
    	//*************fileupload***********
    	//注册成功
        return ResponseResult.success();
    }
    
    /**bootstrap-validator远程check
     * 返回结果形式为Map<String ,Object>
     * 返回结果中应包含：
     * 		valid ：true|false
     * 		message:valid为false时前端的提醒信息
     * @param name
     * @return
     */
    @RequestMapping("/check/name")
    @ResponseBody
    public Map<String,Object> checkName(String name){
        Map<String, Object> map = new HashMap<>();
        if(userService.checkName(name)) {
        	map.put("valid", true);
        }else {
        	map.put("valid", false);
        	map.put("message","用户名已被占用！")	;
        }
        
        return map;

    }
    /**
     * 
     * @param phone
     * @return
     */
    @RequestMapping("/check/phone")
    @ResponseBody
    public Map<String,Object> checkPhone(String phone){
    	Map<String, Object> map = new HashMap<>();
    	
    	 if(userService.checkPhone(phone)) {
         	map.put("valid", true);
         }else {
         	map.put("valid", false);
         	map.put("message","手机号已被占用！")	;
         }
    	 return map;
    }
    /**
     * 
     * @param email
     * @return
     */
    @RequestMapping("/check/email")
    @ResponseBody
    public Map<String,Object> checkEmail(String email){
    	Map<String, Object> map = new HashMap<>();
    	
    	 if(userService.checkEmail(email)) {
         	map.put("valid", true);
         }else {
         	map.put("valid", false);
         	map.put("message","邮箱已被占用！")	;
         }
    	 return map;
    }
    /**
     * 
     * @param password1
     * @param password2
     * @return
     */
    //检查两次password是否一致
    @RequestMapping("/check/password")
    @ResponseBody
    public Map<String,Object> checkPassword(String password1,String password2){
    	System.out.println(password1+"|"+password2);
    	Map<String, Object> map = new HashMap<>();
    	
    	 if(userService.checkPassword(password1, password2)) {
         	map.put("valid", true);
         }else {
         	map.put("valid", false);
         	map.put("message","输入的两次密码不相同！")	;
         }
    	 return map;
    }
    /**
     * 添加好友，搜索好友
     * @param userName
     * @return  friendSearchResult.jsp   
     * 	jstl:   
     * pageInfo===>
     *            username
				  phone
				  email
     */
    @RequestMapping("/search/searchUserbyNameOrphoneOremail")
    public String getUserByName(String key,Integer pageNum,Model model,HttpSession session,@RequestParam(required=false)Integer uid) {
    	  //设置分页
    	 if(ObjectUtils.isEmpty(pageNum)){
    		pageNum= PaginationConstant.PAGE_NUM;
    	}
    	 
    	 if(StringUtils.isEmpty(uid)) {
    		 //查找数据
        	 TbUser user = (TbUser) session.getAttribute("user");
        	 uid=user.getId();
    	 }
       
    	 //查询费uid好友的id
         PageInfo pageInfo = userService.searchUserbyNameOrphoneOremail(pageNum,key,uid);
       
        //pageInfo.getPageNum();
        //pageInfo.getPages();
        //pageInfo.getNextPage();
        //pageInfo.getPrePage();
        //pageInfo.getList();
        model.addAttribute("uid", uid);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("nextPageKeyword", key);
    	return "friendsSearchResult";
    	
    }
    /**
     * 
     * @param uid
     * @param username
     * @return
     */
    @RequestMapping("/update/username")
    @ResponseBody
    public ResponseResult updateUserName(@RequestParam("uid")int uid,@RequestParam("username")String username) {
    	Boolean checkName = userService.checkName(username);
    	if(!checkName) {
    		//该用昵称已被占用
    		return ResponseResult.fail("该昵称已被占用");
    	}
    	userService.updateUserName(username, uid);
    	return ResponseResult.success("用户名修改成功！");
    }
    /**
     * 
     * @param uid
     * @param phone
     * @return
     */
    @RequestMapping("/update/phone")
    @ResponseBody
    public ResponseResult updatePhone(@RequestParam("uid")int uid,@RequestParam("phone")String phone) {
    	Boolean checkPhone = userService.checkPhone(phone);
    	if(!checkPhone) {
    		//该手机号已被占用
    		return ResponseResult.fail("该手机号已被占用");
    	}
    	userService.updatePhone(phone, uid);
    	return ResponseResult.success("手机号修改成功！");
    }
    
    /**
     * 
     * @param uid
     * @param email
     * @return
     */
    @RequestMapping("/update/email")
    @ResponseBody
    public ResponseResult updateEmail(@RequestParam("uid")int uid,@RequestParam("email")String email) {
    	Boolean checkEmail = userService.checkEmail(email);
    	if(!checkEmail) {
    		//该邮箱已被占用
    		return ResponseResult.fail("该邮箱已被占用");
    	}
    	userService.updateEmail(email, uid);
    	return ResponseResult.success("邮箱修改成功！");
    }
    
  /**
   * 
   * @param uid
   * @param password1
   * @param password2
   * @return
   */
    @RequestMapping("/update/password")
    @ResponseBody
    public ResponseResult updatePassword(@RequestParam("uid")int uid,@RequestParam("password1")String password1,@RequestParam("password2")String password2) {
    	if(StringUtils.isEmpty(password1)||StringUtils.isEmpty(password2)||"null".equals(password1)||"null".equals(password2)) {
    		//密码为空
    		return ResponseResult.fail("新密码不能为空！");
    	}
    	if(!password1.equals(password2)) {
    		return ResponseResult.fail("两次密码输入不一致！");
    	}
    	//MD5加密
    	String passwordMD5 = DigestUtils.md5DigestAsHex(password1.getBytes());
    	userService.updatePassword(passwordMD5, uid);
    	return ResponseResult.success("密码修改成功！");
    }
  
    @RequestMapping("/update/profilePhoto")
    @ResponseBody
    public ResponseResult updateProfilePhoto(@RequestParam("uid")int uid,HttpServletRequest request,@RequestParam("headIcon")MultipartFile iconFile) {
    	//*************fileupload 保存图片***********
    	try {
    		//绝对路径地址，用于存储到磁盘
    	String realPath=request.getSession().getServletContext().getRealPath("/headIcons");
    	//图片的http路径用于，http访问图片
    	String httpPathPre="https://"+ServerConstant.imageServerIp+":"+ServerConstant.imageServerPort+"/headIcons";
    	String originalName=iconFile.getOriginalFilename();
    	//System.out.println(originalName+"|"+iconFile.getName());
    	String subfix= originalName.substring(originalName.lastIndexOf("."));
    	String headIcoPath=realPath+"\\"+uid+subfix;
    	//System.out.println("headIcoPath"+headIcoPath);
    	File headfile=new File(headIcoPath);
    	if(headfile.exists()) {
    		//头像修改时调用
    	}else {
    		headfile.getParentFile().mkdirs();
    		headfile.createNewFile();
    		
    	}
    	FileOutputStream out=new FileOutputStream(headfile);
    	out.write(iconFile.getBytes());
    	out.close();
    	
    	//保存到mysql  http://....../id.jpg(png)
    	String httpHeadIcoPath=httpPathPre+"/"+uid+subfix;
    	userService.updateProfilePhoto(uid, httpHeadIcoPath);
    	ResponseResult result=ResponseResult.success("头像修改成功！");
    	//返回文件名
    	result.setData(httpHeadIcoPath);
    	return result;
    	}catch (Exception e) {
    		System.out.println("图片保存到服务器失败！");
    		e.printStackTrace();
    		return ResponseResult.fail("图片保存到服务器失败！");
    	}
    	
    }
    
}
    

