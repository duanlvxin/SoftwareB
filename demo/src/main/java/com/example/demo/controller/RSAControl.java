package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.mapper.RSA_InfoMapper;
import common.utils.RSA.RSAUtils2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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

    Map<String, String> keyPair = new HashMap<String, String>();
    String publicKey = "";
    String privateKey = "";

    public Map<String, String> getKeyPair() {
        return this.keyPair;
    }

    public String getPublicKey() {
        return this.publicKey;
    }

    public String getPrivateKey() {
        return this.privateKey;
    }

    public void setKeyPair(Map<String, String> keyPair) {
        this.keyPair = keyPair;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }


    @RequestMapping(value = "api/getRSAKey", method = RequestMethod.GET)
    public JSONObject generateRSAKey(HttpServletRequest request) {
        if (request.getSession().getAttribute("publicKey") == null) {
            try {
                // 获取公钥和私钥
                java.security.KeyPair keypair = RSAUtils2.initKey();
                setPublicKey(RSAUtils2.getPublicKey(keypair));
                setPrivateKey(RSAUtils2.getPrivateKey(keypair));

                request.getSession().setAttribute("publicKey", getPublicKey());
                request.getSession().setAttribute("privateKey", getPrivateKey());
                System.out.println("pub:"+request.getSession().getAttribute("publicKey"));
                System.out.println("pri:"+request.getSession().getAttribute("privateKey"));

                // 将公钥传到前端
                return JSON.parseObject(
                        "{" +
                                "    \"data\":{" +
                                "        \"publicKey\":\"" + request.getSession().getAttribute("publicKey") + "\"," +
                                "    }," +
                                "    \"meta\":{" +
                                "        \"msg\":\"获取公匙成功\"," +
                                "        \"status\":200" +
                                "    }" +
                                "}"
                );
            } catch (Exception e) {
                e.printStackTrace();
                return JSON.parseObject("{" +
                        "  \"meta\": {" +
                        "    \"msg\": \"获取公匙失败\"," +
                        "    \"status\": 500" +
                        "  }" +
                        "}");
            }
        } else {
            return JSON.parseObject(
                    "{" +
                            "    \"data\":{" +
                            "        \"publicKey\":\"" + request.getSession().getAttribute("publicKey") + "\"," +
                            "    }," +
                            "    \"meta\":{" +
                            "        \"msg\":\"获取公匙成功\"," +
                            "        \"status\":200" +
                            "    }" +
                            "}"
            );
        }
    }
}
//    @RequestMapping(value = "api/getRSAKey", method = RequestMethod.GET)
//    public JSONObject generateRSAKey(HttpServletRequest request) {
//            try {
//                // 获取公钥和私钥
//                java.security.KeyPair keypair = RSAUtils2.initKey();
//                setPublicKey(RSAUtils2.getPublicKey(keypair));
//                setPrivateKey(RSAUtils2.getPrivateKey(keypair));
//
//                // 将公钥传到前端
//                return JSON.parseObject(
//                        "{" +
//                                "    \"data\":{" +
//                                "        \"publicKey\":\"" + getPublicKey() + "\"," +
//                                "    }," +
//                                "    \"meta\":{" +
//                                "        \"msg\":\"获取公匙成功\"," +
//                                "        \"status\":200" +
//                                "    }" +
//                                "}"
//                );
//            } catch (Exception e) {
//                e.printStackTrace();
//                return JSON.parseObject("{" +
//                        "  \"meta\": {" +
//                        "    \"msg\": \"获取公匙失败\"," +
//                        "    \"status\": 500" +
//                        "  }" +
//                        "}");
//            }
//    }
//}
