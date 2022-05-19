package com.ssu.takecare.Retrofit;

import android.util.Log;
import androidx.annotation.NonNull;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ssu.takecare.ApplicationClass;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofitClient = null;

    public static Retrofit getClient(String baseURL) {

        OkHttpClient.Builder client = new OkHttpClient.Builder();

        client.addInterceptor(new Interceptor() {
            @NonNull
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                String token = ApplicationClass.sharedPreferences.getString("accessToken", "");
                Request newRequest = chain.request().newBuilder().addHeader("Authorization", token).build();
                return chain.proceed(newRequest);
            }
        });

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(@NonNull String message) {
                Log.d("RetrofitClient", ": " + message);
            }
        });

        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client.addInterceptor(loggingInterceptor);

        client.connectTimeout(100, TimeUnit.SECONDS);
        client.readTimeout(100, TimeUnit.SECONDS);
        client.writeTimeout(100, TimeUnit.SECONDS);
        client.retryOnConnectionFailure(true);

        if (retrofitClient == null) {
            Gson gson = new GsonBuilder().setLenient().create();

            retrofitClient = new Retrofit.Builder()
                    .baseUrl(baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client.build())
                    .build();
        }

        return retrofitClient;
    }
}
