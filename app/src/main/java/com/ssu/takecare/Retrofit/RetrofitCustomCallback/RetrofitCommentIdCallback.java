package com.ssu.takecare.Retrofit.RetrofitCustomCallback;

public interface RetrofitCommentIdCallback {
    void onError(Throwable t);

    void onSuccess(String message, int commentId);

    void onFailure(int error_code);
}
