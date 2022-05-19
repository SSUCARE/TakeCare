package com.ssu.takecare.Retrofit;

import com.ssu.takecare.Retrofit.Match.ResponseCare;
import java.util.List;

public interface CareListCallback {
    void onError(Throwable t);

    void onSuccess(String message, List<ResponseCare> data);

    void onFailure(int error_code);
}
