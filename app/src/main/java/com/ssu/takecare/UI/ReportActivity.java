package com.ssu.takecare.UI;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.ssu.takecare.ApplicationClass;
import com.ssu.takecare.R;
import com.ssu.takecare.Retrofit.InfoCheck.ResponseInfoCheck;
import com.ssu.takecare.Retrofit.Match.ResponseGetUser;
import com.ssu.takecare.Retrofit.RetrofitCallback;
import com.ssu.takecare.Retrofit.RetrofitCustomCallback.RetrofitUserInfoCallback;

public class ReportActivity extends AppCompatActivity {
    Integer userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
/*
        ApplicationClass.retrofit_manager.infoCheck(new RetrofitUserInfoCallback() {
            @Override
            public void onError(Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onSuccess(String message, ResponseGetUser data) {
                userId=data.getData().getId();
            }
            @Override
            public void onFailure(int error_code) {
            //    Toast.makeText(getApplicationContext(), "error code : " + error_code, Toast.LENGTH_SHORT).show();
            }
        });
        ApplicationClass.retrofit_manager.getReport(1,2022,05,14,new RetrofitCallback() {
            @Override
            public void onError(Throwable t) {
               // Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
                Log.d("확인,getReport","에러:"+t.toString());
            }
            @Override
            public void onSuccess(String message, String data) {
            //    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                Log.d("확인,getReport",data);
            }
            @Override
            public void onFailure(int error_code) {
              //  Toast.makeText(getApplicationContext(), "error code : " + error_code, Toast.LENGTH_SHORT).show();
                Log.d("확인,getReport","실패:"+error_code);
            }
        });
    */
    }


    public void back_btn_event(View view) {
        finish();
    }
}