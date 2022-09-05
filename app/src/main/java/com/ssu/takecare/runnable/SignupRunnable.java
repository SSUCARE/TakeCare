package com.ssu.takecare.runnable;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
import com.ssu.takecare.ApplicationClass;
import com.ssu.takecare.retrofit.customcallback.RetrofitErrorCallback;

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
        ApplicationClass.retrofit_manager.signup(email_str, password_str, new RetrofitErrorCallback() {
            @Override
            public void onError(Throwable t) {
                hMessage.arg1 = -1;
                handler.sendMessage(hMessage);
            }

            @Override
            public void onSuccess(String message, String email) {
                Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                hMessage.arg1 = 1;
                handler.sendMessage(hMessage);
            }

            @Override
            public void onFailure(String error_message, int error_code) {
                Toast.makeText(context.getApplicationContext(), error_message, Toast.LENGTH_SHORT).show();
                hMessage.arg1 = 0;
                handler.sendMessage(hMessage);
            }
        });
    }
}