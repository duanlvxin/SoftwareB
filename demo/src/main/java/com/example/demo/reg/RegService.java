package com.example.demo.reg;

import java.util.Date;
import java.util.Map;

public interface RegService {
    String department_list();
    String doctor_list(Long department_id);
    String doctor_info(Long doctor_id, Date res_date);
    String reg_submit(Map<String, String> params);
}
