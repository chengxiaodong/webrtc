package com.tiantian.dao;

import com.tiantian.pojo.TbFriends;
import com.tiantian.pojo.TbFriendsExample;

import java.util.ArrayList;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbFriendsMapper {
    int countByExample(TbFriendsExample example);

    int deleteByExample(TbFriendsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbFriends record);

    int insertSelective(TbFriends record);

    List<TbFriends> selectByExample(TbFriendsExample example);

    TbFriends selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbFriends record, @Param("example") TbFriendsExample example);

    int updateByExample(@Param("record") TbFriends record, @Param("example") TbFriendsExample example);

    int updateByPrimaryKeySelective(TbFriends record);

    int updateByPrimaryKey(TbFriends record);
    
    List<String> selectFriendsAll(@Param("useId")int id);
    
    ArrayList<String> selectFriendsUid(@Param("useId")int id,@Param("ignoreId")int ignoreId);
    
    
}