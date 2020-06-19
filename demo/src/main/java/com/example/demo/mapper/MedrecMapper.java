package com.example.demo.mapper;

import com.example.demo.model.Medrec;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MedrecMapper {
    int deleteByPrimaryKey(Long medrecId);

    int insert(Medrec record);

    int insertSelective(Medrec record);

    Medrec selectByPrimaryKey(Long medrecId);

    int updateByPrimaryKeySelective(Medrec record);

    int updateByPrimaryKey(Medrec record);

    List<Medrec> selectByPatientId(Long patient_id);
}