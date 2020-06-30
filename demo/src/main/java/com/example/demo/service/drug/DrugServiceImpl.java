package com.example.demo.service.drug;

import com.example.demo.mapper.DrugMapper;
import com.example.demo.model.Drug;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DrugServiceImpl implements com.example.demo.service.drug.DrugService {
    @Autowired
    DrugMapper drugMapper;

    @Override
    public String drug_list(String drug_search, int page_num, int page_size) {
        List<Drug> drugs;
        int total;
        String search = '%'+drug_search+'%';
        int start=page_size*(page_num-1);
        if(drug_search.length()==0) {
            total=drugMapper.count();
        }
        else {
            total=drugMapper.countByName(search);
        }
        if(total==0) {
            try {
                return "{\n" +
                        "    \"data\": [],\n" +
                        "    \"meta\": {\n" +
                        "        \"msg\": \"没有符合要求的药品\",\n" +
                        "        \"status\": 404\n" +
                        "    }\n" +
                        "}";
            } catch (Exception e) {
                return "{\n" +
                        "    \"data\": [],\n" +
                        "    \"meta\": {\n" +
                        "        \"msg\": \"获取药品列表失败\",\n" +
                        "        \"status\": 500\n" +
                        "    }\n" +
                        "}";
            }
        }
        if(start>total-1) {
            try {
                return "{\n" +
                        "    \"data\": [],\n" +
                        "    \"meta\": {\n" +
                        "        \"msg\": \"页码超出范围\",\n" +
                        "        \"status\": 400\n" +
                        "    }\n" +
                        "}";
            } catch (Exception e) {
                return "{\n" +
                        "    \"data\": [],\n" +
                        "    \"meta\": {\n" +
                        "        \"msg\": \"获取药品列表失败\",\n" +
                        "        \"status\": 500\n" +
                        "    }\n" +
                        "}";
            }
        }
        if(drug_search.length()==0) {
            drugs = drugMapper.selectOnPage(start, page_size);
        }
        else {
            drugs=drugMapper.selectByNameOnPage(search,start,page_size);
        }
        StringBuilder str = new StringBuilder();
        for (Drug drug : drugs) {
            str.append("{\"drug_id\":");
            str.append(drug.getDrugId());
            str.append(",\n\"drug_name\":\"");
            str.append(drug.getDrugName());
            str.append("\",\n\"drug_stock\":");
            str.append(drug.getDrugStock());
            str.append(",\n\"drug_specification\":\"");
            str.append(drug.getDrugSpecification());
            str.append("\"},");
        }
        str.deleteCharAt(str.length() - 1);
        try {
            return "{\n" +
                    "    \"data\": {\n" +
                    "        \"total\":" + total + ",\n" +
                    "        \"drug\":[" + str + "],\n" +
                    "    },\n" +
                    "    \"meta\": {\n" +
                    "        \"msg\": \"获取药品列表成功\",\n" +
                    "        \"status\": 200\n" +
                    "    }\n" +
                    "}";
        } catch (Exception e) {
            return "{\n" +
                    "    \"data\": [],\n" +
                    "    \"meta\": {\n" +
                    "        \"msg\": \"获取药品列表失败\",\n" +
                    "        \"status\": 500\n" +
                    "    }\n" +
                    "}";
        }
    }
}
