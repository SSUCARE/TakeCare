package com.ssu.takecare.Retrofit.GetReport;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataGetReport {
    @SerializedName("createdAt")
    public String createdAt;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getDiastolic() {
        return diastolic;
    }

    public void setDiastolic(int diastolic) {
        this.diastolic = diastolic;
    }

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public List<Integer> getSugarLevels() {
        return sugarLevels;
    }

    public void setSugarLevels(List<Integer> sugarLevels) {
        this.sugarLevels = sugarLevels;
    }

    public int getSytstolic() {
        return sytstolic;
    }

    public void setSytstolic(int sytstolic) {
        this.sytstolic = sytstolic;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @SerializedName("diastolic")
    public int diastolic;
    @SerializedName("reportId")
    public int reportId;
    @SerializedName("sugarLevels")
    public List<Integer> sugarLevels=null;
    @SerializedName("systolic")
    public int sytstolic;
    @SerializedName("weight")
    public int weight;
}
