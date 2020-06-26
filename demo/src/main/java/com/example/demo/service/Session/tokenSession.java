package com.example.demo.service.Session;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Scope(value= ConfigurableBeanFactory.SCOPE_SINGLETON, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Component
public class tokenSession implements Serializable {

    private String token = "";

    public String getToken(){
        return this.getToken();
    }

    public void setToken(String token){
        this.token = token;
    }
}
