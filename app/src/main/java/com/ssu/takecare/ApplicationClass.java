package com.ssu.takecare;

import com.ssu.takecare.Retrofit.RetrofitClient;
import android.app.Application;
import android.content.SharedPreferences;
import com.kakao.sdk.common.KakaoSdk;


import retrofit2.Retrofit;

public class ApplicationClass extends Application {

    private static ApplicationClass instance;
    private final String BASE_URL = "http://10.0.2.2:8080/";

    SharedPreferences sharedPreferences;

    @Override
    public void onCreate() {
        sharedPreferences = getApplicationContext().getSharedPreferences("TakeCare", MODE_PRIVATE);

        super.onCreate();
        instance = this;

        // Kakao SDK 초기화
        KakaoSdk.init(this, getResources().getString(R.string.native_key));

        Retrofit rc = RetrofitClient.getClient(BASE_URL);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        instance = null;
    }
}
