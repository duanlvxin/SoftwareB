package com.example.demo.model;

public class Drug {
    private Long drugId;

    private String drugName;

    private Integer drugStock;

    private Float drugPrice;

    private String drugEffect;

    private String drugSpecification;

    public Long getDrugId() {
        return drugId;
    }

    public void setDrugId(Long drugId) {
        this.drugId = drugId;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName == null ? null : drugName.trim();
    }

    public Integer getDrugStock() {
        return drugStock;
    }

    public void setDrugStock(Integer drugStock) {
        this.drugStock = drugStock;
    }

    public Float getDrugPrice() {
        return drugPrice;
    }

    public void setDrugPrice(Float drugPrice) {
        this.drugPrice = drugPrice;
    }

    public String getDrugEffect() {
        return drugEffect;
    }

    public void setDrugEffect(String drugEffect) {
        this.drugEffect = drugEffect == null ? null : drugEffect.trim();
    }

    public String getDrugSpecification() {
        return drugSpecification;
    }

    public void setDrugSpecification(String drugSpecification) {
        this.drugSpecification = drugSpecification == null ? null : drugSpecification.trim();
    }
}