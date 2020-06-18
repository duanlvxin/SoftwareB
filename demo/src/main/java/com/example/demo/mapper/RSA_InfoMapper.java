package com.example.demo.mapper;

import com.example.demo.model.RSA_Info;

public interface RSA_InfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RSA_Info record);

    int insertSelective(RSA_Info record);

    RSA_Info selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RSA_Info record);

    int updateByPrimaryKeyWithBLOBs(RSA_Info record);
}