package com.example.demo.mapper;

import com.example.demo.model.Reg;

public interface RegMapper {
    int deleteByPrimaryKey(Long regId);

    int insert(Reg record);

    int insertSelective(Reg record);

    Reg selectByPrimaryKey(Long regId);

    int updateByPrimaryKeySelective(Reg record);

    int updateByPrimaryKey(Reg record);
}