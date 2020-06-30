package com.example.demo.mapper;

import com.example.demo.model.Reg;
import com.example.demo.model.patientInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;

public interface RegMapper {
    int deleteByPrimaryKey(Long regId);

    int insert(Reg record);

    int insertSelective(Reg record);

    Reg selectByPrimaryKey(Long regId);

    int updateByPrimaryKeySelective(Reg record);

    int updateByPrimaryKey(Reg record);

    @Update("update reg set state=#{state} where reg_id=#{reg_id}")
    int updateState(@Param("reg_id") Long reg_id,@Param("state") Integer state);

    int countReserved(Long doctorId, Date resDate, Boolean period);

    Reg selectByResDatePeriodSerialNum(@Param("resDate") Date resDate, @Param("period") Boolean period, @Param("serialNum") int serialNum);

    @Select("select reg.reg_id,reg.patient_id,patient_name,birthday,patient_gender,address from reg,patient" +
            " where reg.doctor_id=#{doctor_id} and reg.patient_id=patient.patient_id and res_date=#{res_date} " +
            "and period=#{period} and state=#{state} limit 1")
    patientInfo getRegPatientInfo(@Param("doctor_id") Long doctor_id,@Param("res_date") String res_date,
                                  @Param("period") int period,@Param("state") int state);
}