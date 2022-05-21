package com.ssu.takecare.Retrofit.RetrofitCustomCallback;

import com.ssu.takecare.Retrofit.Match.ResponseGetUser;

public interface RetrofitUserInfoCallback {
    void onError(Throwable t);

    void onSuccess(String message, ResponseGetUser data);

    void onFailure(int error_code);
}
