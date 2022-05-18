package com.ssu.takecare.Retrofit.Report;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RequestReport {
    // 이완기: 최저혈압
    @SerializedName("diastolic")
    public int diastolic;

    // 수축기: 최고혈압
    @SerializedName("systolic")
    public int systolic;

    // 혈당
    @SerializedName("sugarLevels")
    public List<Integer> sugarLevels = null;

    // 체중
    @SerializedName("weight")
    public int weight;

    public void setDiastolic(int diastolic) {
        this.diastolic = diastolic;
    }

    public void setSystolic(int systolic) {
        this.systolic = systolic;
    }

    public void setSugarLevels(List<Integer> sugarLevels) {
        this.sugarLevels = sugarLevels;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getDiastolic() {
        return diastolic;
    }

    public int getSystolic() {
        return systolic;
    }

    public List<Integer> getSugarLevels() {
        return sugarLevels;
    }

    public int getWeight() {
        return weight;
    }
}
