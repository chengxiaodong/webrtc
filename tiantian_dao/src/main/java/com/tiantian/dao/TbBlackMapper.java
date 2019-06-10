package com.tiantian.dao;

import com.tiantian.pojo.TbBlack;
import com.tiantian.pojo.TbBlackExample;
import com.tiantian.pojo.utils.TbUserWithBlackDto;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbBlackMapper {
    int countByExample(TbBlackExample example);

    int deleteByExample(TbBlackExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbBlack record);

    int insertSelective(TbBlack record);

    List<TbBlack> selectByExample(TbBlackExample example);

    TbBlack selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbBlack record, @Param("example") TbBlackExample example);

    int updateByExample(@Param("record") TbBlack record, @Param("example") TbBlackExample example);

    int updateByPrimaryKeySelective(TbBlack record);

    int updateByPrimaryKey(TbBlack record);
    
   List<TbUserWithBlackDto> findAllFriendsWithBlack(Integer id);
    
}
















