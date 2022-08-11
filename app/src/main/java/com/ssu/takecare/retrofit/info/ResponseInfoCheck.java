package com.ssu.takecare.retrofit.info;

import androidx.annotation.NonNull;
import com.google.gson.annotations.SerializedName;

public class ResponseInfoCheck {
    @SerializedName("data")
    public DataInfoCheck data;

    @SerializedName("message")
    public String message;

    public void setData(DataInfoCheck data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataInfoCheck getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    @NonNull
    @Override
    public String toString() {
        return message;
    }
}
