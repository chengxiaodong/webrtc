package com.tiantian.service;


import java.util.List;


import com.github.pagehelper.PageInfo;
import com.tiantian.pojo.TbUser;

import common.exception.UserNotExistException;
import common.util.ResponseResult;
/**
 * 
 * @author 晓东
 *
 */
public interface UserService {
	TbUser login(String loginName, String password) throws UserNotExistException;
	
	ResponseResult canAddUser(TbUser user);
	//返回插入的id
	 int addUser(TbUser user);
	 
	 Boolean checkName(String name);
	 
	 Boolean checkPhone(String phone);
	 
	 Boolean checkEmail(String email);
	 
	 Boolean checkPassword(String password1,String password2);
	 
	 PageInfo searchUserbyNameOrphoneOremail(int pageNum,String key,int uid);
	 
	 TbUser getUserById(int id);
	 
	 List<TbUser> findUsersByUids(List<String> uids);
	 
	 void updateProfilePhoto(int id,String profilePhoto);
	    
	   void updateUserName(String username,int id);
	   
	   void updatePassword(String password,int id);
	   
	   void updateEmail(String email,int id);
	   
	   void updatePhone(String phone,int id);
}
