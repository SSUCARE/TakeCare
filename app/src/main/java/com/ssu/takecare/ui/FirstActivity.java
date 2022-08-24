package com.ssu.takecare.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Toast;
import com.ssu.takecare.ApplicationClass;
import com.ssu.takecare.R;
import com.ssu.takecare.retrofit.customcallback.RetrofitErrorCallback;

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
            int keep_sign_in_flag=ApplicationClass.sharedPreferences.getInt("keep_sign_in_flag",0);

            if (!loginEmail.equals("") && !loginPwd.equals("") && keep_sign_in_flag == 1) {
                ApplicationClass.retrofit_manager.login(loginEmail, loginPwd, new RetrofitErrorCallback() {
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
                    public void onFailure(String error_message, int error_code) {
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