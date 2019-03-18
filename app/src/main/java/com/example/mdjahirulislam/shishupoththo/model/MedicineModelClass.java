package com.example.mdjahirulislam.shishupoththo.model;

import java.util.List;

public class MedicineModelClass {

    private int id;
    private String drugName;
    private String drugType;
    private List<DoseModelClass> doseList;


    public MedicineModelClass() {

    }

    public MedicineModelClass(int id, String drugName, String drugType,List<DoseModelClass> doseList) {
        this.id = id;
        this.drugName = drugName;
        this.drugType = drugType;
        this.doseList = doseList;
    }


    public int getId() {
        return id;
    }

    public String getDrugName() {
        return drugName;
    }

    public String getDrugType() {
        return drugType;
    }

    public List<DoseModelClass> getDoseList() {
        return doseList;
    }

    public void setDoseList(List<DoseModelClass> doseList) {
        this.doseList = doseList;
    }

    @Override
    public String toString() {
        return "MedicineModelClass{" +
                "id=" + id +
                ", drugName='" + drugName + '\'' +
                ", drugType='" + drugType + '\'' +
                ", doseList=" + doseList +
                '}';
    }
}
