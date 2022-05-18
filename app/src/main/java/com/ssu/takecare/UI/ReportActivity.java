package com.ssu.takecare.UI;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.ssu.takecare.ApplicationClass;
import com.ssu.takecare.R;
import com.ssu.takecare.Retrofit.RetrofitCallback;

public class ReportActivity extends AppCompatActivity {
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        ApplicationClass.retrofit_manager.infoCheck(new RetrofitCallback() {
            @Override
            public void onError(Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(String message, String Id) {
             //   Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                Log.d("확인,InfoCheck",Id);
                // 여기서 해당 이메일에 대한 정보가 있는지 확인하고 정보가 있으면 MainActivity로 감
                userId=Id;
            }
            @Override
            public void onFailure(int error_code) {
            //    Toast.makeText(getApplicationContext(), "error code : " + error_code, Toast.LENGTH_SHORT).show();
            }
        });
        Log.d("확인,중간","아이디값:"+userId);



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
    }


    public void back_btn_event(View view) {
        finish();
    }
}