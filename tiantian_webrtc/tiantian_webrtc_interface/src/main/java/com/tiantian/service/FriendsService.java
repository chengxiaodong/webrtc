package com.tiantian.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.tiantian.pojo.TbFriends;
import com.tiantian.pojo.TbUser;
import com.tiantian.pojo.utils.TbUserWithBlackDto;

import common.exception.FriendDeleteException;
import common.exception.TbFriendExsitException;

public interface FriendsService {
	List<String > findAllFriendsId(int id);
	void insertFreind(TbFriends friend) throws TbFriendExsitException;
	List<TbUser> findAllFriends(int id);
	PageInfo<TbUserWithBlackDto> findAllFriendsWithBlack(int id,int pageNum);
	void deleteFriend(int uid,int oid) throws FriendDeleteException;
	List<TbUser> recommendFriends(Integer uid);
}
