package com.ssu.takecare.retrofit;

public interface RetrofitCallback {
    void onError(Throwable t);

    void onSuccess(String message, String data);

    void onFailure(int error_code);
}
