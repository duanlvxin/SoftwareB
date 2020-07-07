package com.example.demo.mapper;

import com.example.demo.model.Drug;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface DrugMapper {
    int deleteByPrimaryKey(Long drugId);

    int insert(Drug record);

    int insertSelective(Drug record);

    Drug selectByPrimaryKey(Long drugId);

    int updateByPrimaryKeySelective(Drug record);

    int updateByPrimaryKey(Drug record);

    List<Drug> selectOnPage(int start, int page_size);

    int count();

    List<Drug> selectByNameOnPage(String search,int start, int page_size);

    @Update("update drug set drug_stock=drug_stock-#{drugNum} where drug_id=#{drugId}")
    void updateDrugStock(@Param("drugId") Long drugId, @Param("drugNum") int drugNum);

    int countByName(String search);
}