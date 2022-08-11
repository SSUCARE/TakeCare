package com.ssu.takecare.retrofit.customcallback;

import com.ssu.takecare.retrofit.match.ResponseCare;

public interface RetrofitCareCallback {
    void onError(Throwable t);

    void onSuccess(String message, ResponseCare data);

    void onFailure(int error_code);
}
