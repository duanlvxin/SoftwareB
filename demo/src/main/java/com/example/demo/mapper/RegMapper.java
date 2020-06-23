package com.example.demo.mapper;

import com.example.demo.model.Reg;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface RegMapper {
    int deleteByPrimaryKey(Long regId);

    int insert(Reg record);

    int insertSelective(Reg record);

    Reg selectByPrimaryKey(Long regId);

    int updateByPrimaryKeySelective(Reg record);

    int updateByPrimaryKey(Reg record);

    int countReserved(Long doctorId, Date resDate, Boolean period);

    Reg selectByResDatePeriodSerialNum(@Param("resDate") Date resDate, @Param("period") Boolean period, @Param("serialNum") int serialNum);
}