package com.example.demo.mapper;

import com.example.demo.model.Prescribe;
import com.example.demo.model.PrescribeKey;

public interface PrescribeMapper {
    int deleteByPrimaryKey(PrescribeKey key);

    int insert(Prescribe record);

    int insertSelective(Prescribe record);

    Prescribe selectByPrimaryKey(PrescribeKey key);

    int updateByPrimaryKeySelective(Prescribe record);
}