package com.ssu.takecare.Runnable;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import com.ssu.takecare.ApplicationClass;
import com.ssu.takecare.Retrofit.RetrofitCallback;

public class SignupRunnable implements Runnable {

    Handler handler;
    Context context;
    String email_str;
    String password_str;

    public SignupRunnable(Handler handler,Context context,String email_str, String password_str){
        this.handler = handler;
        this.context = context;
        this.email_str = email_str;
        this.password_str = password_str;
    }

    @Override
    public void run() {
        Message hMessage = handler.obtainMessage();
        ApplicationClass.retrofit_manager.signup(email_str, password_str, new RetrofitCallback() {
            @Override
            public void onError(Throwable t) {
                hMessage.arg1 = -1;
                handler.sendMessage(hMessage);
            }

            @Override
            public void onSuccess(String message, String email) {
                hMessage.arg1 = 1;
                handler.sendMessage(hMessage);
            }

            @Override
            public void onFailure(int error_code) {
                hMessage.arg1 = 0;
                handler.sendMessage(hMessage);
            }
        });
    }
}