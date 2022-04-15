package com.ssu.takecare.KakaoLogin;

import android.app.Application;
import com.kakao.sdk.common.KakaoSdk;
import com.ssu.takecare.R;

public class KakaoApplication extends Application {
    private static KakaoApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        // Kakao SDK 초기화
        KakaoSdk.init(this, getResources().getString(R.string.native_key));
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        instance = null;
    }
}
