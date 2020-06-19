package com.example.demo.mapper;

import com.example.demo.model.Medrec;

public interface MedrecMapper {
    int deleteByPrimaryKey(Long medrecId);

    int insert(Medrec record);

    int insertSelective(Medrec record);

    Medrec selectByPrimaryKey(Long medrecId);

    int updateByPrimaryKeySelective(Medrec record);

    int updateByPrimaryKey(Medrec record);
}