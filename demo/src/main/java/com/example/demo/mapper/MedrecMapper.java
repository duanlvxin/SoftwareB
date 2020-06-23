package com.example.demo.mapper;

import com.example.demo.model.Medrec;
import com.example.demo.model.Drug;
import com.example.demo.model.trueDrug;
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

    @Select("select prescribe.drug_id,prescribe.drug_num,drug.drug_name,drug.drug_specification from " +
            "medrec,prescribe,drug where medrec.medrec_id=#{medrecId} and medrec.medrec_id=prescribe.medrec_id" +
            " and prescribe.drug_id=drug.drug_id")
    List<trueDrug> getSingleMedrec(@Param("medrecId") Long medrecId);
}