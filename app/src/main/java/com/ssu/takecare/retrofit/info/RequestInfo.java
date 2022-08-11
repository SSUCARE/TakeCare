package com.ssu.takecare.retrofit.info;

import com.google.gson.annotations.SerializedName;

public class RequestInfo {
    @SerializedName("name")
    public String name;

    @SerializedName("gender")
    public String gender;

    @SerializedName("age")
    public int age;

    @SerializedName("height")
    public int height;

    @SerializedName("role")
    public String role;

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public int getHeight() {
        return height;
    }

    public String getRole() {
        return role;
    }

    @SerializedName("weight")
    public int weight;

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
