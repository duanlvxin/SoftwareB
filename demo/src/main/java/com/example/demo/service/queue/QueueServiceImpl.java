package com.example.demo.service.queue;

import com.example.demo.mapper.RegMapper;
import com.example.demo.model.Reg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class QueueServiceImpl implements QueueService {
    @Autowired
    RegMapper regMapper;

    @Override
    public String patient_late(Map<String,Long> params) {
        Long reg_id = params.get("reg_id");
        Reg reg = regMapper.selectByPrimaryKey(reg_id);
        if (reg.getState() != 3) {
            try {
                return "{\n" +
                        "    \"data\": [],\n" +
                        "    \"meta\": {\n" +
                        "        \"msg\": \"修改挂号状态失败（原状态应为3，正在就诊）\",\n" +
                        "        \"status\": 400\n" +
                        "    }\n" +
                        "}";
            } catch (Exception e) {
                return "{\n" +
                        "    \"data\": [],\n" +
                        "    \"meta\": {\n" +
                        "        \"msg\": \"获取挂号失败\",\n" +
                        "        \"status\": 500\n" +
                        "    }\n" +
                        "}";
            }
        }
        reg.setState(0);
        regMapper.updateByPrimaryKeySelective((reg));
        regMapper.selectByPrimaryKey(reg_id);
        try {
            return "{\n" +
                    "    \"data\": [],\n" +
                    "    \"meta\": {\n" +
                    "        \"msg\": \"修改挂号状态成功\",\n" +
                    "        \"status\": 200\n" +
                    "    }\n" +
                    "}";
        } catch (Exception e) {
            return "{\n" +
                    "    \"data\": [],\n" +
                    "    \"meta\": {\n" +
                    "        \"msg\": \"获取挂号失败\",\n" +
                    "        \"status\": 500\n" +
                    "    }\n" +
                    "}";
        }
    }
}
