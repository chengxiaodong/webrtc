package com.tiantian.backend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tiantian.backend.service.UserService;
import com.tiantian.dao.TbSysuserMapper;
import com.tiantian.dao.TbUserMapperBackend;
import com.tiantian.pojo.TbSysuser;
import com.tiantian.pojo.TbSysuserExample;
import com.tiantian.pojo.TbSysuserExample.Criteria;
import com.tiantian.pojo.TbUser;
import com.tiantian.pojo.UpdateSysuserException;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private TbSysuserMapper tbSysuserMapper;
	@Autowired
	private TbUserMapperBackend tbUserMapper;
	@Override
	public TbSysuser login(String loginName, String password) {
		TbSysuserExample example = new TbSysuserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(loginName.trim());
		criteria.andPasswordEqualTo(password.trim());
		List<TbSysuser> list = tbSysuserMapper.selectByExample(example);
		if(list.size()==0) {
			return null;
		}else {
			TbSysuser sysuser=list.get(0);
			//设置为空一面泄露密码
			sysuser.setPassword("");
			return sysuser;
		}
		
	}
	@Override
	public List<TbSysuser> findAllSysuser() {
		TbSysuserExample example = new TbSysuserExample();
		Criteria criteria = example.createCriteria();
		criteria.andLevelEqualTo(2);
		List<TbSysuser> list = tbSysuserMapper.selectByExample(example);
		return list;
	}
	@Override
	public void deleteById(Integer id) {
		tbSysuserMapper.deleteByPrimaryKey(id);
		
	}
	@Override
	public void updateByRecord(TbSysuser tbSysuser) throws UpdateSysuserException{
	
		tbSysuserMapper.updateByPrimaryKeySelective(tbSysuser);
		
	}
	@Override
	public void addSysuser(TbSysuser tbSysuser) {
		tbSysuserMapper.insert(tbSysuser);
	}
	
	@Override
	public List<TbUser> findAllUsers() {
		List<TbUser> users = tbUserMapper.selectAllUsers();
		return users;
	}
	@Override
	public void addUser(TbUser tbUser) {
		tbUserMapper.insertSelective(tbUser);
	}
	@Override
	public void deleteUserById(Integer id) {
		System.out.println("id:"+id);
		tbUserMapper.deleteByPrimaryKey(id);
	}
	

}
