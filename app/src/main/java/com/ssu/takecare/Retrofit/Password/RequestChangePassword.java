package com.ssu.takecare.Retrofit.Password;

import com.google.gson.annotations.SerializedName;

public class RequestChangePassword {
    @SerializedName("currentPassword")
    public String currentPassword;

    @SerializedName("newPassword")
    public String newPassword;

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
