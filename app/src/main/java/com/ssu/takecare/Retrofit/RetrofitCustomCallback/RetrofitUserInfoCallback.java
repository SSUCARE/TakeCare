package com.ssu.takecare.Retrofit.RetrofitCustomCallback;

import com.ssu.takecare.Retrofit.Match.DataResponseGetUser;

public interface RetrofitUserInfoCallback {
    void onError(Throwable t);

    void onSuccess(String message, DataResponseGetUser data);

    void onFailure(int error_code);
}
