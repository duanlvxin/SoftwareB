package com.example.demo.mapper;

import com.example.demo.model.Doctor;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

import javax.print.Doc;

public interface DoctorMapper {
    int deleteByPrimaryKey(Long doctorId);

    int insert(Doctor record);

    int insertSelective(Doctor record);

    Doctor selectByPrimaryKey(Long doctorId);

    int updateByPrimaryKeySelective(Doctor record);

    int updateByPrimaryKeyWithBLOBs(Doctor record);

    int updateByPrimaryKey(Doctor record);

    @Update("update doctor set doctor_password=#{password} where doctor_id=#{doctor_id}")
    int updatePassword(@Param("doctor_id") Long doctor_id, @Param("password") String password);

    @Update("update doctor set doctor_email=#{doctor_email},doctor_mobile=#{doctor_mobile}" +
            ",doctor_intro=#{doctor_intro},doctor_pho=#{doctor_pho} where doctor_id=#{doctor_id}")
    int updateInfo(@Param("doctor_id") Long doctor_id,@Param("doctor_email") String doctor_email,@Param("doctor_mobile")
            String doctor_mobile,@Param("doctor_intro") String doctor_intro,@Param("doctor_pho") byte[] doctor_pho);

    Doctor selectByUsername(String doctorUser);

    List<Doctor> selectByDepartmentId(Long departmentId);
}