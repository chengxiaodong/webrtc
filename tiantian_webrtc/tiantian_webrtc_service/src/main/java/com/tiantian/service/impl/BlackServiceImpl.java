package com.tiantian.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tiantian.dao.TbBlackMapper;
import com.tiantian.pojo.TbBlack;
import com.tiantian.pojo.TbBlackExample;
import com.tiantian.pojo.TbBlackExample.Criteria;
import com.tiantian.service.BlackService;

import common.exception.BlackAddException;
import common.exception.CancelBlackException;

@Service
public class BlackServiceImpl implements BlackService{
@Autowired
private TbBlackMapper tbBlackMapper;
	@Override
	public List<TbBlack> findAll(Integer uid) {
		TbBlackExample example=new TbBlackExample();
		Criteria criteria = example.createCriteria();
		criteria.andUidEqualTo(uid);
		List<TbBlack> blackers = tbBlackMapper.selectByExample(example);
		return blackers;
	}

	@Override
	public TbBlack isBlack(Integer uid, Integer oid) {
		TbBlackExample example=new TbBlackExample();
		Criteria criteria = example.createCriteria();
		criteria.andUidEqualTo(uid);
		criteria.andOidEqualTo(oid);
		List<TbBlack> blackers = tbBlackMapper.selectByExample(example);
		return blackers.size()==0?null:blackers.get(0);
	}

	@Override
	public void insertBlack(Integer uid, Integer oid) throws BlackAddException  {
		if(isBlack(uid,oid)!=null) {
			//已被设置为黑名单对象，不能重复设置
			throw new BlackAddException("已被设置为黑名单对象，不能重复设置");
		}
		
		TbBlack record=new TbBlack();
		record.setUid(uid);
		record.setOid(oid);
		tbBlackMapper.insert(record);
	}

	@Override
	public void cancelBlack(Integer id) throws CancelBlackException {
		if(tbBlackMapper.deleteByPrimaryKey(id)==0) {
			throw new CancelBlackException("所要取消拉黑的好友并未在黑名单中");
		};
	}

}
