package com.ssu.takecare.Retrofit.InfoCheck;

import com.google.gson.annotations.SerializedName;
/*회원 정보 조회*/
public class DataInfoCheck {
    @SerializedName("age")
    public int age;
    @SerializedName("email")
    public String email;
    @SerializedName("gender")
    public String gender;
    @SerializedName("height")
    public int height;
    @SerializedName("id")
    public int id;
    @SerializedName("name")
    public String name;
    @SerializedName("weight")
    public int weight;

    //추가 매칭 액티비티에서 필요
    @SerializedName("role")
    public String role;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getRole() { return role; }

    public void setRole(String role) { this.role = role; }
}
