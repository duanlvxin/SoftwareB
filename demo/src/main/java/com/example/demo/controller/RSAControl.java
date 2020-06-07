package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.mapper.RSA_InfoMapper;
import com.example.demo.model.RSA_Info;
import common.utils.RSA.RSAUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

@RestController
@EnableAutoConfiguration
public class RSAControl {

    @Autowired
    RSA_InfoMapper rsa_infoMapper;

    /**
     * @Title:generateRSAKey
     * @Description:生成公匙和私匙
     * @param:username
     * @return
     */
    @RequestMapping(value = "api/getRSAKey",method = RequestMethod.GET)
    public JSONObject generateRSAKey(@RequestParam String username){
        try{
            // 获取公钥和私钥
            HashMap<String, Object> keys = RSAUtils.getKeys();
            RSAPublicKey publicKey = (RSAPublicKey) keys.get("public");
            RSAPrivateKey privateKey = (RSAPrivateKey) keys.get("private");
            // 保存私钥到 redis，也可以保存到数据库
            //如果不存在，插入，否则，更新私钥
            RSA_Info rsa_info = new RSA_Info();
            rsa_info.setUsername(username);
            rsa_info.setPrivatekey(privateKey.toString());
            if(rsa_infoMapper.selectByUsername(username)==null){
                rsa_infoMapper.insert(rsa_info);
            }
            else{
                rsa_infoMapper.updatePrivateKey(username,privateKey.toString());
            }
            // 将公钥传到前端
            Map<String,String> map = new HashMap<String,String>();
            // 注意返回modulus和exponent以16为基数的BigInteger的字符串表示形式
            map.put("modulus", publicKey.getModulus().toString(16));
            map.put("exponent", publicKey.getPublicExponent().toString(16));
            return JSON.parseObject(
                    "{" +
                            "    \"data\":{" +
                            "        \"modulus\":\""+publicKey.getModulus().toString(16)+"\"," +
                            "        \"exponent\":\""+publicKey.getPublicExponent().toString(16)+"\"" +
                            "    }," +
                            "    \"meta\":{" +
                            "        \"msg\":\"获取公匙成功\"," +
                            "        \"status\":200" +
                            "    }" +
                            "}"
            );
        }catch (Exception e) {
            e.printStackTrace();
            return JSON.parseObject("{\"msg\":\"内部错误\"}");
        }
    }

    /**
     *
     * @Title: checkRSAKey
     * @Description: 验证密码
     * @param username
     * @param password
     * @return
     * @date 2018年2月5日 下午4:25:43
     * @author p7
     */
    @RequestMapping(value = "api/checkRSAKey/{username}/{password}",method = RequestMethod.GET)
    public JSONObject checkRSAKey(@PathVariable String username, @PathVariable String password) {
        RSA_Info record = rsa_infoMapper.selectByUsername(username);
        Object privateKey = record.getPrivatekey();
        System.out.println(privateKey);
        try {
            // 解密
            String decryptByPrivateKey = RSAUtils.decryptByPrivateKey(password, (RSAPrivateKey) privateKey);
            System.out.println(decryptByPrivateKey);
            return JSON.parseObject("{\"msg\":\"解密成功\"}");
        } catch (Exception e) {
            e.printStackTrace();
            return JSON.parseObject("{\"msg\":\"解密失败\"}");
        }
    }
}
