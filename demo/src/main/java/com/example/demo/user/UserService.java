package com.example.demo.user;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public interface UserService {
    String register(String username, String password, String mobile, Date birthday,String address,String patient_name,
                    Boolean patient_gender);
    String patient_login(HttpServletRequest request);
    String doctor_login(HttpServletRequest request);
}
