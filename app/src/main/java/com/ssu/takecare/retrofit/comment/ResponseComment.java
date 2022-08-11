package com.ssu.takecare.retrofit.comment;

import androidx.annotation.NonNull;
import com.google.gson.annotations.SerializedName;

public class ResponseComment {
    @SerializedName("data")
    public int commentId;

    @SerializedName("message")
    public String message;

    @NonNull
    @Override
    public String toString() {
        return message;
    }
}
