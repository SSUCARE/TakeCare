package com.ssu.takecare.Retrofit.GetReport;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class DataGetReport {

    @SerializedName("createdAt")
    public String createdAt;

    @SerializedName("reportId")
    public int reportId;

    @SerializedName("diastolic")
    public int diastolic;

    @SerializedName("systolic")
    public int sytstolic;

    @SerializedName("sugarLevels")
    public List<Integer> sugarLevels = null;

    @SerializedName("weight")
    public int weight;

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public void setDiastolic(int diastolic) {
        this.diastolic = diastolic;
    }

    public void setSytstolic(int sytstolic) {
        this.sytstolic = sytstolic;
    }

    public void setSugarLevels(List<Integer> sugarLevels) {
        this.sugarLevels = sugarLevels;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public int getReportId() {
        return reportId;
    }

    public int getDiastolic() {
        return diastolic;
    }

    public int getSytstolic() {
        return sytstolic;
    }

    public List<Integer> getSugarLevels() {
        return sugarLevels;
    }

    public int getWeight() {
        return weight;
    }
}
