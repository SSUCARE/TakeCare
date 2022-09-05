package com.ssu.takecare.retrofit.comment;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ResponseGetComment {
    @SerializedName("data")
    public List<DataGetComment> data;

    @SerializedName("message")
    public String message;

    public void setData(List<DataGetComment> data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataGetComment> getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
