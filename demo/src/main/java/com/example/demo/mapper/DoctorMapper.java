package com.example.demo.mapper;

import com.example.demo.model.Doctor;

import javax.print.Doc;

public interface DoctorMapper {
    int deleteByPrimaryKey(Long doctorId);

    int insert(Doctor record);

    int insertSelective(Doctor record);

    Doctor selectByPrimaryKey(Long doctorId);

    int updateByPrimaryKeySelective(Doctor record);

    int updateByPrimaryKeyWithBLOBs(Doctor record);

    int updateByPrimaryKey(Doctor record);

    Doctor selectByUsername(String doctorUser);
}