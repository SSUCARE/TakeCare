package com.ssu.takecare.UI;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ssu.takecare.ApplicationClass;
import com.ssu.takecare.R;
import com.ssu.takecare.Retrofit.GetReport.DataGetReport;
import com.ssu.takecare.Retrofit.Match.ResponseGetUser;
import com.ssu.takecare.Retrofit.RetrofitCallback;
import com.ssu.takecare.Retrofit.RetrofitCustomCallback.RetrofitReportCallback;
import com.ssu.takecare.Retrofit.RetrofitCustomCallback.RetrofitUserInfoCallback;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ReportActivity extends AppCompatActivity {

    Date currentTime = Calendar.getInstance().getTime();
    String date_year = new SimpleDateFormat("yyyy", Locale.getDefault()).format((currentTime));
    String date_month = new SimpleDateFormat("MM", Locale.getDefault()).format((currentTime));
    String date_day = new SimpleDateFormat("dd", Locale.getDefault()).format((currentTime));

    int find_year = Integer.parseInt(date_year);
    int find_month = Integer.parseInt(date_month);
    int find_day = Integer.parseInt(date_day);

    TextView tv_name;
    Integer userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_report);

        String my_name = ApplicationClass.sharedPreferences.getString("name", "");
        tv_name = findViewById(R.id.report_name);
        tv_name.setText(my_name);

        userId = ApplicationClass.sharedPreferences.getInt("userId", 0);

        ApplicationClass.retrofit_manager.getReport(userId, find_year, find_month, find_day, new RetrofitReportCallback() {

            @Override
            public void onError(Throwable t) {
                // Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
                Log.d("ReportActivity", "에러 : " + t.toString());
            }

            @Override
            public void onSuccess(String message, List<DataGetReport> data) {
                //    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                Log.d("ReportActivity", "data - CreatedAt : " + data.get(0).getCreatedAt());
                Log.d("ReportActivity", "data - ReportId : " + data.get(0).getReportId());
                Log.d("ReportActivity", "data - Systolic : " + data.get(0).getSystolic());
                Log.d("ReportActivity", "data - Diastolic : " + data.get(0).getDiastolic());
                Log.d("ReportActivity", "data - SugarLevels : " + data.get(0).getSugarLevels());
                Log.d("ReportActivity", "data - Weight : " + data.get(0).getWeight());
            }

            @Override
            public void onFailure(int error_code) {
                //  Toast.makeText(getApplicationContext(), "error code : " + error_code, Toast.LENGTH_SHORT).show();
                Log.d("ReportActivity", "실패 : " + error_code);
            }
        });
    }

    public void back_btn_event(View view) {
        finish();
    }
}