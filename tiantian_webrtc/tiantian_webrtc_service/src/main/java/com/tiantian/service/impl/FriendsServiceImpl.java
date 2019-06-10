package com.tiantian.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tiantian.dao.TbBlackMapper;
import com.tiantian.dao.TbFriendsMapper;
import com.tiantian.dao.TbUserMapper;
import com.tiantian.pojo.TbFriends;
import com.tiantian.pojo.TbFriendsExample;
import com.tiantian.pojo.TbFriendsExample.Criteria;
import com.tiantian.pojo.TbUser;
import com.tiantian.pojo.TbUserExample;
import com.tiantian.pojo.utils.FriendSearchResultBean;
import com.tiantian.pojo.utils.TbUserWithBlackDto;
import com.tiantian.service.FriendsService;

import common.constant.PaginationConstant;
import common.exception.FriendDeleteException;
import common.exception.TbFriendExsitException;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class FriendsServiceImpl implements FriendsService{
	@Autowired
	private TbFriendsMapper tbFriendsMapper;
	@Autowired
	private TbUserMapper tbUserMapper;
	@Autowired
	private TbBlackMapper tbBlackMapper;
	@Autowired
	private JedisPool jedisPool;
	@Override
	public List<String> findAllFriendsId(int id) {
		List<String> list = tbFriendsMapper.selectFriendsAll(id);
		return list;
	}
	@Override
	public void insertFreind(TbFriends friend) throws TbFriendExsitException {
		//查询记录是否已经存在
		TbFriendsExample example=new TbFriendsExample();
		Criteria criteria = example.createCriteria();
		criteria.andUseidEqualTo(friend.getUseid());
		criteria.andFriendidEqualTo(friend.getFriendid());
		List<TbFriends> list = tbFriendsMapper.selectByExample(example);
		if(list.size()>0) {
			//双方已是好友，不能重复添加
			throw new TbFriendExsitException();
		}
		tbFriendsMapper.insert(friend);
		//插入数据
		
	}
	@Override
	public List<TbUser> findAllFriends(int id) {
		// TODO Auto-generated method stub
		List<String> ids = tbFriendsMapper.selectFriendsAll(id);
		List<TbUser> users=new ArrayList<>();
		for(String  uid:ids) {
			TbUser user = tbUserMapper.selectByPrimaryKey(Integer.valueOf(uid));
			users.add(user);
		}
		
		return users;
	}
	@Override
	public void deleteFriend(int uid, int oid) throws FriendDeleteException {
		TbFriendsExample example=new TbFriendsExample();
		Criteria criteria1 = example.createCriteria();
		criteria1.andUseidEqualTo(uid);
		criteria1.andFriendidEqualTo(oid);
		
		Criteria criteria2 = example.createCriteria();
		criteria2.andUseidEqualTo(oid);
		criteria2.andFriendidEqualTo(uid);
		
		example.or(criteria2);
		int size=tbFriendsMapper.deleteByExample(example);
		if(size<1) {
			//双方已是好友，不能重复添加
			System.out.println("好友删除失败");
			throw new FriendDeleteException();
		}
	}
	@Override
	public PageInfo<TbUserWithBlackDto> findAllFriendsWithBlack(int id,int pageNum) {
		//分页
				PageHelper.startPage(pageNum,PaginationConstant.PAGE_SIZE);
				List<TbUserWithBlackDto> users =tbBlackMapper.findAllFriendsWithBlack(id);
				
				 //将查找出的结果封装到PageInfo对象中
		        PageInfo<TbUserWithBlackDto> pageInfo=new PageInfo<TbUserWithBlackDto>(users);
		
		//List<TbUserWithBlackDto> friendsWithBlack = tbBlackMapper.findAllFriendsWithBlack(id);
		return pageInfo;
	}
	@Override
	public List<TbUser> recommendFriends(Integer uid) {
		//生成一个6位前缀，用户区分不同用户的数据
		Random rand=new Random();
		
		String prefix=rand.nextInt(10000000)+"";
		Jedis jedis=jedisPool.getResource();
	    
		//集合缓存
		ArrayList<ArrayList<String>> lists=new ArrayList<>();
		//结果集
		ArrayList<String> results=new ArrayList<>();
		
		//查出用户的所有好友id
		List<String> allFid = tbFriendsMapper.selectFriendsAll(uid);
		if(null==allFid) {
			return null;
		}
		for(String fid:allFid) {
			lists.add(tbFriendsMapper.selectFriendsUid(Integer.valueOf(fid), uid));
		}
	
		//放到redis 集合中        
		for(int i=0;i<lists.size();i++) {
			for(String str:lists.get(i)) {
				//集合名，添加元素
				jedis.sadd(prefix+"set"+i, str);
				//20s后过期，自动删除
				jedis.expire(prefix+"set"+i,60);
			}
		}
		//进行集合运算
		for(int i=0;i<lists.size();i++) {
			for(int j=i+1;j<lists.size();j++) {
				//集合名，添加元素
				Set<String> sinter = jedis.sinter(prefix+"set"+i, prefix+"set"+j);
				results.addAll(sinter);
			}
		}
		//归还连接，否则连接数到达设置的20个时会报错
		jedis.close();
		//按好友重合次数排序
		HashMap<String,Integer> sortMap=new HashMap<>();
		for(String str:results) {
			if(!sortMap.containsKey(str)) {
				//第一次出现
				sortMap.put(str, 1);
			}else {
				//出现次数加1
				sortMap.put(str, sortMap.get(str)+1);
			}
		}
		  List<Map.Entry<String,Integer>> list = new ArrayList<Map.Entry<String,Integer>>(sortMap.entrySet());
	        Collections.sort(list,new Comparator<Map.Entry<String,Integer>>() {
	            //升序排序
	            public int compare(Entry<String, Integer> o1,
	                    Entry<String, Integer> o2) {
	            	//按出现次数降序
	                return o2.getValue().compareTo(o1.getValue());
	            }
	            
	        });
	        //清空已缓存的数据
	        results.clear();
	        //将排序后的结果放入results
	        for(Map.Entry<String,Integer> mapping:list){ 
	        		//按好友重合次数排序 返回
	        		results.add(mapping.getKey());
	        		//  key   出现次数
	             //  System.out.println(mapping.getKey()+":"+mapping.getValue()); 
	          } 
	     //分装返回结果
	     ArrayList<TbUser> users=new ArrayList<>();
	     for(String s:results) {
	    	 users.add(tbUserMapper.selectByPrimaryKey(Integer.valueOf(s)));
	     }
	     
		return users;
	}
	
}









































