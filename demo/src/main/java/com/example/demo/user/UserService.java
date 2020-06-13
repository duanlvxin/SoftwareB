package com.example.demo.user;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

public interface UserService {
    String register(Map<String, String> params);
    String patient_login(Map<String, String> params);
    String doctor_login(Map<String, String> params);
}
