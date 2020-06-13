package com.example.demo.user;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

public interface UserService {
    String register(String username, String password, String mobile, Date birthday,String address,String patient_name,
                    Boolean patient_gender);
    String patient_login(Map<String, String> params);
    String doctor_login(HttpServletRequest request);
}
