package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.mapper.RSA_InfoMapper;
import com.example.demo.model.RSA_Info;
import common.utils.RSA.RSAUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

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
//    HttpServletRequest request = null;

    public Map<String,String> getKeyPair(){
        return this.keyPair;
    }

    public String getPublicKey() {
        return this.publicKey;
    }

    public String getPrivateKey() {
        return this.privateKey;
    }

//    public void setRequest(HttpServletRequest request){
//        this.request = request;
//    }

    public void setKeyPair(Map<String, String> keyPair){
        this.keyPair = keyPair;
    }

    public void setPublicKey(String publicKey){
        this.publicKey = publicKey;
    }

    public void setPrivateKey(String privateKey){
        this.privateKey = privateKey;
    }

//    public void clearRSAKey(){
//        //清除session中的privateKey和publicKey
////        System.out.println("hello");
////        System.out.println("request:"+this.request);
//        if(this.request!=null){
//            this.request.getSession().setAttribute("privateKey", "");
//            System.out.println("herePub:"+request.getSession().getAttribute("publicKey"));
//            this.request.getSession().setAttribute("publicKey", "");
//        }
//    }
//
//    public void setTimer() {
//        final Timer timer = new Timer();
//        //设定定时任务
//        timer.schedule(new TimerTask() {
//            //定时任务执行方法
//            @Override
//            public void run() {
//                clearRSAKey();
//            }
//        }, 0, 2 * 1000);
//    }

    @RequestMapping(value = "api/getRSAKey", method = RequestMethod.GET)
    public JSONObject generateRSAKey(HttpServletRequest request) {
//        setRequest(request);
//        System.out.println("request2:"+this.request);
        System.out.println("pubbbbbbbbbbbbbb:"+request.getSession().getAttribute("publicKey"));
        if (request.getSession().getAttribute("publicKey")==null) {
            try {
                // 获取公钥和私钥
                setKeyPair(RSAUtils.createKeys(1024));
                setPublicKey(keyPair.get("publicKey"));
                setPrivateKey(keyPair.get("privateKey"));

                request.getSession().setAttribute("publicKey",getPublicKey());
                request.getSession().setAttribute("privateKey",getPrivateKey());
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
