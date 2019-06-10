package com.tiantian.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tiantian.dao.TbInformMapper;
import com.tiantian.pojo.TbInform;
import com.tiantian.service.InformService;

import common.constant.PaginationConstant;

/**
 * 消息管理服务
 * @author 晓东
 *
 */
@Service
public class InformServiceImpl implements InformService{
	@Autowired
	private TbInformMapper tbInformMapper;
	@Override
	public void insertInfrom(TbInform tbInform) {
		//保存inform
		tbInformMapper.insert(tbInform);
	}
	@Override
	public PageInfo<TbInform> selectInformsByUid(Integer uid,Integer pageNum) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum,10);
		PageInfo<TbInform> pageInfo =new PageInfo<>( tbInformMapper.selectInformsByUid(uid));
		return pageInfo;
	}
	@Override
	public void mark(Integer messageId, Integer readed) {
		tbInformMapper.updateReaded(messageId ,readed );
	}
	@Override
	public Integer selectUnReadInformsByUid(Integer uid) {
		Integer count = tbInformMapper.selectUnReadInformsByUid(uid);
		return count;
	}

}
