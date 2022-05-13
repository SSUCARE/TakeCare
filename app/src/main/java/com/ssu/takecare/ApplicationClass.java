package com.ssu.takecare;

import android.app.Application;
import android.content.SharedPreferences;
import com.kakao.sdk.common.KakaoSdk;
import com.ssu.takecare.Retrofit.RetrofitAPI;
import com.ssu.takecare.Retrofit.RetrofitClient;
import com.ssu.takecare.Retrofit.RetrofitManager;

public class ApplicationClass extends Application {
    //private final String BASE_URL = "http://3.39.15.41:8080/";
    private final String BASE_URL ="http://10.0.2.2:8080/";

    private static ApplicationClass instance;

    public static SharedPreferences sharedPreferences;

    public static RetrofitAPI retrofit_api;
    public static RetrofitManager retrofit_manager;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        sharedPreferences = getApplicationContext().getSharedPreferences("TakeCare", MODE_PRIVATE);

        retrofit_api = RetrofitClient.getClient(BASE_URL).create(RetrofitAPI.class);
        retrofit_manager = new RetrofitManager();

        // Kakao SDK 초기화
        KakaoSdk.init(this, getResources().getString(R.string.native_key));
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        instance = null;
    }
}
