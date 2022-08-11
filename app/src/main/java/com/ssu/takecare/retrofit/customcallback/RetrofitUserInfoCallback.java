package com.ssu.takecare.retrofit.customcallback;

import com.ssu.takecare.retrofit.match.DataResponseGetUser;

public interface RetrofitUserInfoCallback {
    void onError(Throwable t);

    void onSuccess(String message, DataResponseGetUser data);

    void onFailure(int error_code);
}
