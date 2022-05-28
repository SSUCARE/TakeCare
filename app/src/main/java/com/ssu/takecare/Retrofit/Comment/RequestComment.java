package com.ssu.takecare.Retrofit.Comment;

import com.google.gson.annotations.SerializedName;

public class RequestComment {
    @SerializedName("content")
    public String content;

    @SerializedName("reportId")
    public int reportId;

    public void setContent(String content) {
        this.content = content;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public String getContent() {
        return content;
    }

    public int getReportId() {
        return reportId;
    }
}
