package com.ssu.takecare.Runnable;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.ssu.takecare.ApplicationClass;
import com.ssu.takecare.Retrofit.RetrofitCallback;
import com.ssu.takecare.UI.LoginActivity;

public class SignupRunnable implements Runnable{
    Handler handler;
    Context context;
    String email_str;
    String password_str;
    public SignupRunnable(Handler handler,Context context,String email_str, String password_str){
        this.handler=handler;
        this.context=context;
        this.email_str=email_str;
        this.password_str=password_str;
    }
    @Override
    public void run() {
        Message Hmessage=handler.obtainMessage();
        ApplicationClass.retrofit_manager.signup(email_str, password_str, new RetrofitCallback() {
            @Override
            public void onError(Throwable t) {
                Hmessage.arg1=-1;
                handler.sendMessage(Hmessage);
                Toast.makeText(context.getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(String message, String email) {
                Hmessage.arg1=1;
                handler.sendMessage(Hmessage);
                Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int error_code) {
                Hmessage.arg1=0;
                handler.sendMessage(Hmessage);
                Toast.makeText(context.getApplicationContext(), "error code : " + error_code, Toast.LENGTH_SHORT).show();
            }
        });
    }
}