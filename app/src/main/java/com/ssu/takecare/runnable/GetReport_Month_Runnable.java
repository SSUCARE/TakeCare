package com.ssu.takecare.runnable;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.ssu.takecare.ApplicationClass;
import com.ssu.takecare.retrofit.report.DataGetReport;
import com.ssu.takecare.retrofit.customcallback.RetrofitReportCallback;
import java.util.List;

public class GetReport_Month_Runnable implements Runnable {
    Handler handler;
    Context context;
    int userId;
    int year;
    int month;
    List<DataGetReport> response_getreport_list;

    public GetReport_Month_Runnable(Handler handler,Context context, int userId, int year, int month){
        Log.d("디버그, ShareGraph->getMonthData->생성자", " 년:"+year+" 월:"+month);
        this.handler=handler;
        this.context=context;
        this.userId=userId;
        this.year=year;
        this.month=month;
    }
    @Override
    public void run() {
        Message Hmessage=handler.obtainMessage();
        ApplicationClass.retrofit_manager.getReport_Month(userId, year, month, new RetrofitReportCallback() {

            @Override
            public void onError(Throwable t) {
                Log.d("디버그, ShareGraph->getMonthData", "에러 : " + t.toString());
                Hmessage.arg1=-1;
                handler.sendMessage(Hmessage);
            }

            @Override
            public void onSuccess(String message, List<DataGetReport> data) {
                if(data.size()>0){
                    Log.d("디버그, GetReport_month_runnable",  data.get(0).getCreatedAt());
                    Log.d("디버그, GetReport_month_runnable",  ""+data.get(0).getReportId());
                    Log.d("디버그, GetReport_month_runnable", ""+data.get(0).getSystolic());
                    Log.d("디버그, GetReport_month_runnable", ""+ data.get(0).getDiastolic());
                    Log.d("디버그, GetReport_month_runnable","" + data.get(0).getSugarLevels());
                    Log.d("디버그, GetReport_month_runnable","" + data.get(0).getWeight());
                }
                Log.d("디버그, ShareGraph->getMonthData->onsuccess", " 데이터 개수:"+data.size());
                response_getreport_list=data;
                Hmessage.arg1=1;
                handler.sendMessage(Hmessage);
            }

            @Override
            public void onFailure(int error_code) {
                Log.d("ReportActivity", "실패 : " + error_code);
                Hmessage.arg1=0;
                handler.sendMessage(Hmessage);
            }
        });
    }
    public List<DataGetReport> getResponse_getreport_list() {
        return response_getreport_list;
    }

    public void setResponse_getreport_list(List<DataGetReport> response_getreport_list) {
        this.response_getreport_list = response_getreport_list;
    }
}
