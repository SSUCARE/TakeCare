package com.ssu.takecare.UI;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.ssu.takecare.ApplicationClass;
import com.ssu.takecare.R;
import com.ssu.takecare.Retrofit.InfoCheck.ResponseInfoCheck;
import com.ssu.takecare.Retrofit.RetrofitCallback;
import com.ssu.takecare.Retrofit.UserInfoCallback;

public class ReportActivity extends AppCompatActivity {
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        ApplicationClass.retrofit_manager.infoCheck(new UserInfoCallback() {
            @Override
            public void onError(Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(String message, ResponseInfoCheck data) {
                userId = data.getDataInfocheck().getId();

                Log.d("ReportActivity", "onSuccess : userId : " + userId);

                ApplicationClass.retrofit_manager.getReport(userId, 2022, 5, 18, new RetrofitCallback() {
                    @Override
                    public void onError(Throwable t) {
                        Log.d("확인,ReportActivity,getReport:onerror", "에러:" + t.toString());
                    }

                    @Override
                    public void onSuccess(String message, String data) {
                        Log.d("확인,ReportActivity,getReport:onsucess", data);
                    }

                    @Override
                    public void onFailure(int error_code) {
                        Log.d("확인,ReportActivity,getReport:onfailure", "실패:" + error_code);
                    }
                });
            }

            @Override
            public void onFailure(int error_code) {
                Toast.makeText(getApplicationContext(), "error code : " + error_code, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void back_btn_event(View view) {
        finish();
    }
}