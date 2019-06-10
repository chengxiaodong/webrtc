package com.tiantian.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.tiantian.pojo.TbInform;

public interface InformService {
	void insertInfrom(TbInform tbInform);
	PageInfo<TbInform> selectInformsByUid(Integer uid,Integer pageNum);
	//返回未打开的记录数
	Integer selectUnReadInformsByUid(Integer uid);
	void mark(Integer messageId,Integer readed);
}
