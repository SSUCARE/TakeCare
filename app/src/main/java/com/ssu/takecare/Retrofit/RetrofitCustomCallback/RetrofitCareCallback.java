package com.ssu.takecare.Retrofit.RetrofitCustomCallback;

import com.ssu.takecare.Retrofit.Match.ResponseCare;

public interface RetrofitCareCallback {
    void onError(Throwable t);

    void onSuccess(String message, ResponseCare data);

    void onFailure(int error_code);
}
