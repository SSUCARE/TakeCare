package com.ssu.takecare.Retrofit.RetrofitCustomCallback;

import com.ssu.takecare.Retrofit.GetReport.DataGetReport;
import java.util.List;

public interface RetrofitReportCallback {
    void onError(Throwable t);

    void onSuccess(String message, List<DataGetReport> data);

    void onFailure(int error_code);
}
