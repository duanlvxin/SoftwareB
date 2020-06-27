package com.example.demo.model;

public class drugInfo {
    private Long drug_id;
    private int drug_num;

    public int getDrug_num() {
        return drug_num;
    }

    public Long getDrug_id() {
        return drug_id;
    }

    public void setDrug_id(Long drug_id) {
        this.drug_id = drug_id;
    }

    public void setDrug_num(int drug_num) {
        this.drug_num = drug_num;
    }
}
