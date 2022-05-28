package com.ssu.takecare.Retrofit.Comment;

public class Comment {
    public boolean left;
    public String message;

    public Comment(boolean left, String message) {
        super();
        this.left = left;
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
