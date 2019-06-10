package com.tiantian.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tiantian.dao.TbUserMapper;
import com.tiantian.pojo.TbUser;
import com.tiantian.pojo.TbUserExample;
import com.tiantian.pojo.TbUserExample.Criteria;
import com.tiantian.pojo.utils.FriendSearchResultBean;
import com.tiantian.service.UserService;

import common.constant.PaginationConstant;
import common.constant.UserConstant;
import common.exception.UserNotExistException;
import common.util.ResponseResult;
/**
 *用户管理
 * @author 晓东
 *
 */
@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private TbUserMapper tbUserMapper;
/*	*//**
	 * 用户登录
	 * @throws UserNotExistException 
	 *//*
	@Override
	public TbUser login(String loginName, String password) throws UserNotExistException {
		// 实现登录功能
		TbUserExample example=new TbUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(loginName);
		criteria.andPasswordEqualTo(password);
		criteria.andOnlinestatusEqualTo(UserConstant.USER_VALID);
		 List<TbUser> users=tbUserMapper.selectByExample(example);
	        if(users.size()>0){
	            return users.get(0);
	        }
	     throw new UserNotExistException("用户名或密码不正确");
	}*/
	
	/**修改后可以使用phone、username、email登录
	 * 用户登录
	 * @throws UserNotExistException 
	 */
	@Override
	public TbUser login(String loginName, String password) throws UserNotExistException {
		// 实现登录功能
		TbUser user = tbUserMapper.login(loginName,password);
		if(user==null)
	     throw new UserNotExistException("用户名或密码不正确");
		return user;
	}
	@Override
	public ResponseResult canAddUser(TbUser user) {
		//测试username是否已存在
		TbUserExample example=new TbUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(user.getUsername());
		List<TbUser> list = tbUserMapper.selectByExample(example);
		if(list.size()>0) {
			return ResponseResult.fail("用户名已被占用！");
		}
		
		//测试phone是否已存在
		 example=new TbUserExample();
		 criteria = example.createCriteria();
		criteria.andPhoneEqualTo(user.getPhone());
		 list = tbUserMapper.selectByExample(example);
		if(list.size()>0) {
			return ResponseResult.fail("手机号码已被占用！");
		}
		//测试email是否已存在
		 example=new TbUserExample();
		 criteria = example.createCriteria();
		criteria.andEmailEqualTo(user.getEmail());
		 list = tbUserMapper.selectByExample(example);
		if(list.size()>0) {
			return ResponseResult.fail("邮箱已被占用！");
		}
		return ResponseResult.success();
	}
	@Override
	public int addUser(TbUser user) {
		//tbUserMapper.insert(user);执行完后插入的id保存在user.id
		tbUserMapper.insert(user);
		 return user.getId();
	}
	@Override
	public Boolean checkName(String name) {
		// TODO Auto-generated method stub
		TbUserExample example=new TbUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(name);
		List<TbUser> users = tbUserMapper.selectByExample(example);
		if(users.size()>0) {
			return false;
		}
		return true;
	}
	@Override
	public Boolean checkPhone(String phone) {
		// TODO Auto-generated method stub
		TbUserExample example=new TbUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andPhoneEqualTo(phone);
		List<TbUser> users = tbUserMapper.selectByExample(example);
		if(users.size()>0) {
			return false;
		}
		return true;
	}
	@Override
	public Boolean checkEmail(String email) {
		// TODO Auto-generated method stub
		TbUserExample example=new TbUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andEmailEqualTo(email);
		List<TbUser> users = tbUserMapper.selectByExample(example);
		if(users.size()>0) {
			return false;
		}
		return true;
	}
	@Override
	public Boolean checkPassword(String password1, String password2) {
		// TODO Auto-generated method stub
		if(password1.equals(password2)) {
			return true;
		}
		return false;
	}
	/**
	 * 
	 */
	@Override
	public PageInfo searchUserbyNameOrphoneOremail(int pageNum,String key,int uid) {
		//分页
		PageHelper.startPage(pageNum,PaginationConstant.PAGE_SIZE);
		List<FriendSearchResultBean> users = tbUserMapper.searchUserbyNameOrphoneOremail(key,uid);
		//System.out.println("users:"+users.size());
		 //将查找出的结果封装到PageInfo对象中
        PageInfo<FriendSearchResultBean> pageInfo=new PageInfo<FriendSearchResultBean>(users);
        
		return pageInfo;
	}
	@Override
	public TbUser getUserById(int id) {
		TbUser user = tbUserMapper.selectByPrimaryKey(id);
		return user;
	}
	@Override
	public List<TbUser> findUsersByUids(List<String> uids) {
		// TODO Auto-generated method stub
		TbUserExample example=new TbUserExample();
		for(String id:uids) {
			Criteria criteria = example.createCriteria();
			criteria.andIdEqualTo(Integer.valueOf(id));
			example.or(criteria);
		}
		List<TbUser> users = tbUserMapper.selectByExample(example);
		return users;
	}
	@Override
	public void updateProfilePhoto(int id, String profilePhoto) {
		// TODO Auto-generated method stub
		
		
		
		tbUserMapper.updateProfilePhoto( id,profilePhoto );
	}
	@Override
	public void updateUserName(String username, int id) {
		// TODO Auto-generated method stub
		tbUserMapper.updateUserName(username, id);
	}
	@Override
	public void updatePassword(String password, int id) {
		// TODO Auto-generated method stub
		tbUserMapper.updatePassword(password, id);
	}
	@Override
	public void updateEmail(String email, int id) {
		// TODO Auto-generated method stub
		tbUserMapper.updateEmail(email, id);
	}
	@Override
	public void updatePhone(String phone, int id) {
		// TODO Auto-generated method stub
		tbUserMapper.updatePhone(phone, id);
	}
	
	

}
























