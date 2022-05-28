package com.ssu.takecare.Retrofit.Match;

import com.google.gson.annotations.SerializedName;

public class DataResponseCare {
    @SerializedName("id")
    private Integer id;

    @SerializedName("userId")
    private Integer userId;

    @SerializedName("userName")
    private String userName;

    @SerializedName("status")
    private String status; // PENDING, ACCEPTED

    public Integer getId() { return this.id; }

    public Integer getUserId() {
        return userId;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getStatus() {
        return this.status;
    }

    public void setId(Integer id) {
        this.id=id;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName=userName;
    }

    public void setStatus(String status) {
        this.status=status;
    }
}
