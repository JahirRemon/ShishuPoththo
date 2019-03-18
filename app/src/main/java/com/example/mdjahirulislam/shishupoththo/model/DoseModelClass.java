package com.example.mdjahirulislam.shishupoththo.model;

public class DoseModelClass {
    private int id;
    private int drugId;
    private int startMonth;
    private int endMonth;
    private double dosesQuantity;
    private int interval;
    private int isWeightSensitive;

    public DoseModelClass() {
    }

    public DoseModelClass(int id, int drugId, int startMonth, int endMonth, double dosesQuantity, int interval, int isWeightSensitive) {
        this.id = id;
        this.drugId = drugId;
        this.startMonth = startMonth;
        this.endMonth = endMonth;
        this.dosesQuantity = dosesQuantity;
        this.interval = interval;
        this.isWeightSensitive = isWeightSensitive;
    }

    public int getId() {
        return id;
    }

    public int getDrugId() {
        return drugId;
    }

    public int getStartMonth() {
        return startMonth;
    }

    public int getEndMonth() {
        return endMonth;
    }

    public double getDosesQuantity() {
        return dosesQuantity;
    }

    public int getInterval() {
        return interval;
    }

    public int getIsWeightSensitive() {
        return isWeightSensitive;
    }


    @Override
    public String toString() {
        return "DoseModelClass{" +
                "id=" + id +
                ", drugId=" + drugId +
                ", startMonth=" + startMonth +
                ", endMonth=" + endMonth +
                ", dosesQuantity=" + dosesQuantity +
                ", interval=" + interval +
                ", isWeightSensitive=" + isWeightSensitive +
                '}';
    }
}

