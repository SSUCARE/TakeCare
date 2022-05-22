package com.ssu.takecare.Retrofit.GetReport;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class DataGetReport {

    @SerializedName("createdAt")
    public String createdAt;

    @SerializedName("reportId")
    public int reportId;

    @SerializedName("systolic")
    public int systolic;

    @SerializedName("diastolic")
    public int diastolic;

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

    public void setSystolic(int systolic) {
        this.systolic = systolic;
    }

    public void setDiastolic(int diastolic) {
        this.diastolic = diastolic;
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

    public int getSystolic() {
        return systolic;
    }

    public int getDiastolic() {
        return diastolic;
    }

    public List<Integer> getSugarLevels() {
        return sugarLevels;
    }

    public int getWeight() {
        return weight;
    }
}
