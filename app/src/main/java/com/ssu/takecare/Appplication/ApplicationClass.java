package com.ssu.takecare.Appplication;

import com.ssu.takecare.Retrofit.RetrofitClient;
import android.app.Application;
import com.kakao.sdk.common.KakaoSdk;
import com.ssu.takecare.R;

import retrofit2.Retrofit;

public class ApplicationClass extends Application {
    private static ApplicationClass instance;
    private final String BASE_URL = "http://10.0.2.2:8080/";

    @Override
    public void onCreate() {
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
