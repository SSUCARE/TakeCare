package com.ssu.takecare;

import static com.ssu.takecare.util.FcmTokenUtil.loadFcmToken;

import android.app.Application;
import android.content.SharedPreferences;
import com.ssu.takecare.retrofit.RetrofitAPI;
import com.ssu.takecare.retrofit.RetrofitClient;
import com.ssu.takecare.retrofit.RetrofitManager;

public class ApplicationClass extends Application {

    public final String BASE_URL = "http://3.39.15.41:8080";

    public static SharedPreferences sharedPreferences;
    public static RetrofitAPI retrofit_api;
    public static RetrofitManager retrofit_manager;

    @Override
    public void onCreate() {
        super.onCreate();
        sharedPreferences = getApplicationContext().getSharedPreferences("TakeCare", MODE_PRIVATE);
        retrofit_api = RetrofitClient.getClient(BASE_URL).create(RetrofitAPI.class);
        retrofit_manager = new RetrofitManager();
        loadFcmToken();
    }
}
