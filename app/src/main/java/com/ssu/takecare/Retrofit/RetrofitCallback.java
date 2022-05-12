package com.ssu.takecare.Retrofit;

public interface RetrofitCallback<T> {
    void onError(Throwable t);

    void onSuccess(String message, String data);

    void onFailure(int error_code);
}
