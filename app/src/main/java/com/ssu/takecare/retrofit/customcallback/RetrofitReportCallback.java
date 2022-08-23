package com.ssu.takecare.retrofit.customcallback;

import com.ssu.takecare.retrofit.report.DataReport;

public interface RetrofitReportCallback {
    void onError(Throwable t);

    void onSuccess(String message, DataReport data);

    void onFailure(int error_code);
}
