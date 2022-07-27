package com.ssu.takecare.AssistClass;

import java.util.List;

public class Calendar_Day {

    int day;
    public int systolic;
    public int diastolic;
    public List<Integer> sugarLevels = null;
    public int weight;

    public Calendar_Day(int day, int systolic, int diastolic, List<Integer>sugarLevels, int weight){
        this.day=day;
        this.systolic=systolic;
        this.diastolic=diastolic;
        this.sugarLevels=sugarLevels;
        this.weight=weight;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getSystolic() {
        return systolic;
    }

    public void setSystolic(int systolic) {
        this.systolic = systolic;
    }

    public int getDiastolic() {
        return diastolic;
    }

    public void setDiastolic(int diastolic) {
        this.diastolic = diastolic;
    }

    public List<Integer> getSugarLevels() {
        return sugarLevels;
    }

    public void setSugarLevels(List<Integer> sugarLevels) {
        this.sugarLevels = sugarLevels;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
