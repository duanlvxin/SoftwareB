package com.example.demo.medrec;

import java.util.Map;

public interface MedrecService {

    String getAllMedrec(Long patient_id);
    String getSingleMedrec(Long medrec_id);
}
