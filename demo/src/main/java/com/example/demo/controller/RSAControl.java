package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import common.utils.RSA.RSAUtils;
import com.example.demo.service.Session.keySession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@EnableAutoConfiguration
public class RSAControl {

    @Autowired
    ApplicationContext context;

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
        keySession keysession = context.getBean(keySession.class);
        System.out.println("privateKey:"+keysession.getPrivateKey());
        if (keysession.getPrivateKey() == null) {
            try {
                // 获取公钥和私钥
                java.security.KeyPair keypair = RSAUtils.initKey();
                setPublicKey(RSAUtils.getPublicKey(keypair));
                setPrivateKey(RSAUtils.getPrivateKey(keypair));

//                request.getSession().setAttribute("publicKey", getPublicKey());
//                request.getSession().setAttribute("privateKey", getPrivateKey());
                this.addSession(keysession,getPublicKey(),getPrivateKey());

                // 将公钥传到前端
                return JSON.parseObject(
                        "{" +
                                "    \"data\":{" +
                                "        \"publicKey\":\"" + keysession.getPublicKey() + "\"," +
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
                            "        \"publicKey\":\"" + keysession.getPublicKey() + "\"," +
                            "    }," +
                            "    \"meta\":{" +
                            "        \"msg\":\"获取公匙成功\"," +
                            "        \"status\":200" +
                            "    }" +
                            "}"
            );
        }
    }

    public void addSession(keySession keysession, String publicKey, String privateKey){
        keysession.setPublicKey(publicKey);
        keysession.setPrivateKey(privateKey);
    }
}
