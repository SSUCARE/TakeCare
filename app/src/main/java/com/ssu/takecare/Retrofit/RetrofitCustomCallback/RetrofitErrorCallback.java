package com.ssu.takecare.Retrofit.RetrofitCustomCallback;

public interface RetrofitErrorCallback {
    void onError(Throwable t);

    void onSuccess(String message, String data);

    void onFailure(String errorMessage, int error_code);
}
