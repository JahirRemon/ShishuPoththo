package com.example.mdjahirulislam.shishupoththo.model;

public class SuspensionModelClass {
    private int id;
    private int drugId;
    private double suspensionTSFUnite;

    public SuspensionModelClass(int id, int drugId, double suspensionTSFUnite) {
        this.id = id;
        this.drugId = drugId;
        this.suspensionTSFUnite = suspensionTSFUnite;
    }

    public int getId() {
        return id;
    }

    public int getDrugId() {
        return drugId;
    }

    public double getSuspensionTSFUnite() {
        return suspensionTSFUnite;
    }
}
