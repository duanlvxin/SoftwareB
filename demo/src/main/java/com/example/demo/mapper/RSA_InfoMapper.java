package com.example.demo.mapper;

import com.example.demo.model.RSA_Info;

public interface RSA_InfoMapper {
    int insert(RSA_Info record);

    int insertSelective(RSA_Info record);

    RSA_Info selectByUsername(String username);

    int updatePrivateKey(String username,String privateKey);
}