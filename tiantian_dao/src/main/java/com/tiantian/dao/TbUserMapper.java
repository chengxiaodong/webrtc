package com.tiantian.dao;


import com.tiantian.pojo.TbUser;
import com.tiantian.pojo.TbUserExample;
import com.tiantian.pojo.utils.FriendSearchResultBean;

import java.util.List;


import org.apache.ibatis.annotations.Param;

public interface TbUserMapper {
    int countByExample(TbUserExample example);

    int deleteByExample(TbUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbUser record);

    int insertSelective(TbUser record);

    List<TbUser> selectByExample(TbUserExample example);

    TbUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbUser record, @Param("example") TbUserExample example);

    int updateByExample(@Param("record") TbUser record, @Param("example") TbUserExample example);

    int updateByPrimaryKeySelective(TbUser record);

    int updateByPrimaryKey(TbUser record);
    
    List<FriendSearchResultBean> searchUserbyNameOrphoneOremail(@Param("key") String key,@Param("uid")Integer uid);
    
   void  updateProfilePhoto(@Param("id")int id, @Param("profilePhoto")String profilePhoto);
    
   void updateUserName(@Param("username")String username,@Param("id")int id);
   
   void updatePassword(@Param("password")String password,@Param("id")int id);
   
   void updateEmail(@Param("email")String email,@Param("id")int id);
   
   void updatePhone(@Param("phone")String phone,@Param("id")int id);
    //TbUser selectByLoginNameAndPassword(@Param("loginName")String loginName, @Param("password")String password, @Param("isValid")int isValid);
   TbUser login(@Param("key")String loginName,@Param("password")String password);
   
   List<TbUser> selectAllUsers();
}

















