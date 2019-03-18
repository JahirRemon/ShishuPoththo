package com.example.mdjahirulislam.shishupoththo.model;

public class WeightModelClass {
    private int startMonth;
    private int endMonth;
    private double weightKG;

    public WeightModelClass(int startMonth, int endMonth, double weightKG) {
        this.startMonth = startMonth;
        this.endMonth = endMonth;
        this.weightKG = weightKG;
    }

    public int getStartMonth() {
        return startMonth;
    }

    public int getEndMonth() {
        return endMonth;
    }

    public double getWeightKG() {
        return weightKG;
    }
}
