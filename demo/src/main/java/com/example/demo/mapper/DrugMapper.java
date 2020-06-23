package com.example.demo.mapper;

import com.example.demo.model.Drug;

public interface DrugMapper {
    int deleteByPrimaryKey(Long drugId);

    int insert(Drug record);

    int insertSelective(Drug record);

    Drug selectByPrimaryKey(Long drugId);

    int updateByPrimaryKeySelective(Drug record);

    int updateByPrimaryKey(Drug record);
}