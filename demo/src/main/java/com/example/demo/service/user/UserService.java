package com.example.demo.service.user;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface UserService {
    String register(Map<String, String> params);
    String patient_login(Map<String, String> params,HttpServletRequest request);
    String doctor_login(Map<String, String> params);
}
