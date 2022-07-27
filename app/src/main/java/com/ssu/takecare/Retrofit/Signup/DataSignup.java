package com.ssu.takecare.Retrofit.Signup;

import androidx.annotation.NonNull;
import com.google.gson.annotations.SerializedName;

public class DataSignup {
    @SerializedName("email")
    public String email;

    @NonNull
    @Override
    public String toString() {
        return email;
    }
}