package com.example.demo.mapper;

import com.example.demo.model.Patient;

public interface PatientMapper {
    int deleteByPrimaryKey(Long patientId);

    int insert(Patient record);

    int insertSelective(Patient record);

    Patient selectByPrimaryKey(Long patientId);

    int updateByPrimaryKeySelective(Patient record);

    int updateByPrimaryKey(Patient record);

    Patient selectByUsername(String username);
}