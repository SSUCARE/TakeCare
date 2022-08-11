package com.ssu.takecare.retrofit.login;

import com.google.gson.annotations.SerializedName;

public class RequestLogin {
    @SerializedName("email")
    public String email;

    @SerializedName("password")
    public String password;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
