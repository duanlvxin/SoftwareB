package com.example.demo.mapper;

import com.example.demo.model.Drug;

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

    int countByName(String search);
}