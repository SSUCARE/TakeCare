package com.ssu.takecare.retrofit.comment;

import com.google.gson.annotations.SerializedName;

public class DataGetComment {
    @SerializedName("createdAt")
    public String createdAt;

    @SerializedName("modifiedAt")
    public String modifiedAt;

    @SerializedName("reportId")
    public int reportId;

    @SerializedName("authorId")
    public int authorId;

    @SerializedName("authorName")
    public String authorName;

    @SerializedName("id")
    public int commentId;

    @SerializedName("content")
    public String content;

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setModifiedAt(String modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getModifiedAt() {
        return modifiedAt;
    }

    public int getReportId() {
        return reportId;
    }

    public int getAuthorId() {
        return authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public int getCommentId() {
        return commentId;
    }

    public String getContent() {
        return content;
    }
}
