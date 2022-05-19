package com.ssu.takecare.Retrofit;

import com.ssu.takecare.Retrofit.InfoCheck.ResponseInfoCheck;

public interface UserInfoCallback {
    void onError(Throwable t);

    void onSuccess(String message, ResponseInfoCheck data);

    void onFailure(int error_code);
}
