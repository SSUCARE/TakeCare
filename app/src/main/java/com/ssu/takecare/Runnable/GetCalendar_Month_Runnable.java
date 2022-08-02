package com.ssu.takecare.Runnable;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.ssu.takecare.ApplicationClass;
import com.ssu.takecare.AssistClass.Calendar.Calendar_Day;
import com.ssu.takecare.Retrofit.GetReport.DataGetReport;
import com.ssu.takecare.Retrofit.RetrofitCustomCallback.RetrofitReportCallback;
import java.util.ArrayList;
import java.util.List;

public class GetCalendar_Month_Runnable implements Runnable {
    Handler handler;
    Context context;
    int userId;
    int year;
    int month;


    List<Calendar_Day> response_getcalendar_list=new ArrayList<>();

    public GetCalendar_Month_Runnable(Handler handler,Context context, int userId, int year, int month){
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
                Hmessage.arg1=-1;
                handler.sendMessage(Hmessage);
            }

            @Override
            public void onSuccess(String message, List<DataGetReport> data) {
                for(int i=0; i<data.size(); i++){
                    String s_day=data.get(i).getCreatedAt().substring(8,10);//day값으로 날짜 찍힘
                    Calendar_Day day_date=new Calendar_Day(Integer.parseInt(s_day),data.get(i).getSystolic(),data.get(i).getDiastolic(),data.get(i).getSugarLevels(),data.get(i).getWeight());
                    response_getcalendar_list.add(day_date);
                }

                Hmessage.arg1=1;
                handler.sendMessage(Hmessage);
            }

            @Override
            public void onFailure(int error_code) {
                Hmessage.arg1=0;
                handler.sendMessage(Hmessage);
            }
        });
    }

    public List<Calendar_Day> getResponse_getcalendar_list() {
        return response_getcalendar_list;
    }
}
