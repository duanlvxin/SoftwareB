package com.example.demo.model;

public class trueDrug {
    private Long drug_id;
    private String drug_name;
    private int drug_num;
    private String drug_specification;

    public void setDrug_id(Long drug_id){
        this.drug_id = drug_id;
    }
    public void setDrug_name(String drug_name){
        this.drug_name = drug_name;
    }
    public void setDrug_num(int drug_num){
        this.drug_num = drug_num;
    }
    public void setDrug_specification(String drug_specification){
        this.drug_specification = drug_specification;
    }

    public Long getDrug_id(){
        return this.drug_id;
    }

    public String getDrug_name(){
        return this.drug_name;
    }

    public String getDrug_specification(){
        return this.drug_specification;
    }

    public int getDrug_num(){
        return this.drug_num;
    }

    @Override
    public String toString() {
        return "{" +
                "drug_id:" + drug_id +
                ", drug_name:\"" + drug_name + "\"" +
                ", drug_num:" + drug_num +
                ", drug_specification:\"" + drug_specification + "\"" +
                '}';
    }
}
