package com.tiantian.service;

import java.util.List;

import com.tiantian.pojo.TbBlack;

import common.exception.BlackAddException;
import common.exception.CancelBlackException;

/**
 * 黑名单管理功能
 * @author 晓东
 *
 */
public interface BlackService {
	//返回uid设置的黑名单
	public List<TbBlack> findAll(Integer uid);
	//判断oid是否被uid放入了黑名单
	public TbBlack isBlack(Integer uid,Integer oid);
	//uid添加oid为黑名单一员
	public void insertBlack(Integer uid,Integer oid) throws BlackAddException;
	//取消拉黑
	public void cancelBlack(Integer id) throws CancelBlackException;
	
}
