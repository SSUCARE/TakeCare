package com.ssu.takecare.Runnable;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.ssu.takecare.ApplicationClass;
import com.ssu.takecare.Retrofit.RetrofitCallback;
import com.ssu.takecare.Retrofit.RetrofitCustomCallback.RetrofitErrorCallback;

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
                Log.d("Signup_onSuccess", "message : " + message);
                Log.d("Signup_onSuccess", "email : " + email);
                Toast.makeText(context.getApplicationContext(), "회원가입 성공", Toast.LENGTH_SHORT).show();
                hMessage.arg1 = 1;
                handler.sendMessage(hMessage);
            }

            @Override
            public void onFailure(String error_message, int error_code) {
                Log.d("Signup_onFailure", "error message : " + error_message);
                Log.d("Signup_onFailure", "error code : " + error_code);
                Toast.makeText(context.getApplicationContext(), "해당 요청 값이 요구하는 형식과 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                hMessage.arg1 = 0;
                handler.sendMessage(hMessage);
            }
        });
    }
}