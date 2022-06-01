package com.ssu.takecare.UI;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import com.ssu.takecare.ApplicationClass;
import com.ssu.takecare.R;
import com.ssu.takecare.Retrofit.RetrofitCallback;

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
                Log.d("FirstActivity", "email : " + loginEmail);
                Log.d("FirstActivity", "password : " + loginPwd);

                ApplicationClass.retrofit_manager.login(loginEmail, loginPwd, new RetrofitCallback() {
                    @Override
                    public void onError(Throwable t) {
                    }

                    @Override
                    public void onSuccess(String message, String token) {
                        editor.putString("accessToken", token);
                        editor.apply();

                        finish();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }

                    @Override
                    public void onFailure(int error_code) {
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