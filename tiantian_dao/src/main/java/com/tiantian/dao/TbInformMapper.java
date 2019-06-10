package com.tiantian.dao;

import com.tiantian.pojo.TbInform;
import com.tiantian.pojo.TbInformExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbInformMapper {
    int countByExample(TbInformExample example);

    int deleteByExample(TbInformExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbInform record);

    int insertSelective(TbInform record);

    List<TbInform> selectByExample(TbInformExample example);

    TbInform selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbInform record, @Param("example") TbInformExample example);

    int updateByExample(@Param("record") TbInform record, @Param("example") TbInformExample example);

    int updateByPrimaryKeySelective(TbInform record);

    int updateByPrimaryKey(TbInform record);
   
    List<TbInform> selectInformsByUid(Integer uid);
    
    Integer selectUnReadInformsByUid(Integer uid);
    
    void updateReaded(@Param("id")Integer messageId,@Param("readed")Integer readed);
    
}










































