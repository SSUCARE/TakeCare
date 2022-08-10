package com.ssu.takecare.Retrofit.Signup;

import com.google.gson.annotations.SerializedName;

public class RequestSignup {
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
