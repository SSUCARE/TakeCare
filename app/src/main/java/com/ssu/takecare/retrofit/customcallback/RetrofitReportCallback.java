package com.ssu.takecare.retrofit.customcallback;

import com.ssu.takecare.retrofit.report.DataGetReport;
import java.util.List;

public interface RetrofitReportCallback {
    void onError(Throwable t);

    void onSuccess(String message, List<DataGetReport> data);

    void onFailure(int error_code);
}
