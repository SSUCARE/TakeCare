package com.ssu.takecare.retrofit.customcallback;

public interface RetrofitCommentIdCallback {
    void onError(Throwable t);

    void onSuccess(String message, int commentId);

    void onFailure(int error_code);
}
