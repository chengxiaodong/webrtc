package com.tiantian.backend.service;

import java.util.List;

import com.tiantian.pojo.TbSysuser;
import com.tiantian.pojo.TbUser;
import com.tiantian.pojo.UpdateSysuserException;

public interface UserService {
	 TbSysuser login(String loginName,String password);
	 
	 List<TbSysuser> findAllSysuser();
	 
	 void deleteById(Integer id);
	 
	 void deleteUserById(Integer id);
	 
	 void updateByRecord(TbSysuser tbSysuser) throws UpdateSysuserException;
	 
	 void addSysuser(TbSysuser tbSysuser);
	 
	 void addUser(TbUser tbUser);
	 
	 List<TbUser> findAllUsers();
}
