package com.ssu.takecare.UI;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import com.ssu.takecare.ApplicationClass;
import com.ssu.takecare.R;
import com.ssu.takecare.Retrofit.RetrofitCallback;
import com.ssu.takecare.Retrofit.RetrofitCustomCallback.RetrofitErrorCallback;

public class FirstActivity extends AppCompatActivity {

    SharedPreferences.Editor editor = ApplicationClass.sharedPreferences.edit();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
    }

   @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();

        if (action == MotionEvent.ACTION_DOWN) {
            // 자동 로그인
            String loginEmail = ApplicationClass.sharedPreferences.getString("email_login", "");
            String loginPwd = ApplicationClass.sharedPreferences.getString("password_login", "");

            if (!loginEmail.equals("") && !loginPwd.equals("")) {
                ApplicationClass.retrofit_manager.login(loginEmail, loginPwd, new RetrofitErrorCallback() {
                    @Override
                    public void onError(Throwable t) {
                    }

                    @Override
                    public void onSuccess(String message, String token) {
                        editor.putString("accessToken", token);
                        editor.apply();

                        Log.d("FirstActivity_Login_onSuccess", "message : " + message);
                        Toast.makeText(getApplicationContext(), "자동로그인 성공", Toast.LENGTH_SHORT).show();

                        finish();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }

                    @Override
                    public void onFailure(String error_message, int error_code) {
                        Log.d("자동로그인_onFailure", "error message : " + error_message);
                        Log.d("자동로그인_onFailure", "error code : " + error_code);
                        Toast.makeText(getApplicationContext(), error_message, Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        }

        return super.onTouchEvent(event);
   }
}