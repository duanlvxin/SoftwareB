package com.example.demo.service.Session;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.io.Serializable;


@Scope(value=ConfigurableBeanFactory.SCOPE_SINGLETON, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Component
public class keySession implements Serializable {

    private static final long serialVersionUID = 9120765714832970813L;
    //私钥
    private String privateKey = null;
    //公钥
    private String publicKey = null;

    public String getPrivateKey() {
        return this.privateKey;
    }

    public String getPublicKey(){
        return this.publicKey;
    }

    public void setPrivateKey(String privateKey){
        this.privateKey = privateKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
}
