package com.ssu.takecare.Runnable;



import android.app.Notification;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.ssu.takecare.ApplicationClass;
import com.ssu.takecare.Retrofit.GetReport.DataGetReport;
import com.ssu.takecare.Retrofit.RetrofitCustomCallback.RetrofitReportCallback;

import java.util.List;



public class ShareRunnable implements Runnable{
    StringBuilder stringBuilder;
    int userId;
    int year;
    int month;
    Handler handler;
    private final String TAG="ShareRunnable,Deburg";
    public ShareRunnable(StringBuilder stringBuilder, int userId, int year, int month,Handler handler){
        this.stringBuilder=stringBuilder;
        this.userId=userId;
        this.year=year;
        this.month=month;
        this.handler=handler;
    }
    @Override
    public void run() {
        Message msg=handler.obtainMessage();
        ApplicationClass.retrofit_manager.getReport_Month(userId,year,month, new RetrofitReportCallback() {
            @Override
            public void onError(Throwable t) {
                msg.arg1=-1;
                handler.sendMessage(msg);
            }
            @Override
            public void onSuccess(String message, List<DataGetReport> data) {
                for (int i = 0; i < data.size(); i++) {
                    stringBuilder.append(data.get(i).getCreatedAt().split("T")[0] + ",");
                    stringBuilder.append(data.get(i).systolic + ",");
                    stringBuilder.append(data.get(i).getDiastolic() + ",");
                    stringBuilder.append(new String(data.get(i).getSugarLevels() + "").replace(",", "/") + ",");
                    stringBuilder.append(data.get(i).getWeight() + "\n");
                    Log.d(TAG, data.get(i).getCreatedAt());
                }
                msg.arg1=1;
                handler.sendMessage(msg);
            }
                @Override
                public void onFailure (int error_code){
                     msg.arg1=0;
                     handler.sendMessage(msg);
            }
        });
    }
}
