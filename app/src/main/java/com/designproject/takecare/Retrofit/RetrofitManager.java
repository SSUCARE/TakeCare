package com.designproject.takecare.Retrofit;

import android.util.Log;
import androidx.annotation.NonNull;
import com.google.gson.JsonElement;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitManager {
    private final String BASE_URL = "http://10.0.2.2:8080/";
    private RetrofitAPI retrofit_api = RetrofitClient.getClient(BASE_URL).create(RetrofitAPI.class);

    public void loginReq(String email, String password) {
        Call<JsonElement> call = retrofit_api.loginRequest(email, password);

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(@NonNull Call<JsonElement> call, @NonNull Response<JsonElement> response) {
                if (response.isSuccessful()) {
                    Log.d("RetrofitManager", "onResponse : 성공, 결과 : " + response.body());
                    Log.d("RetrofitManager", "onResponse : status code is " + response.code());
                }
                else {
                    Log.d("RetrofitManager", "onResponse : 실패, 결과 : " + response.body());
                    Log.d("RetrofitManager", "onResponse : status code is " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonElement> call, @NonNull Throwable t) {
                Log.e("RetrofitManager", "onFailure : " + t.getLocalizedMessage());
            }
        });
    }

    public void loginRes(String email, String password) {
        Call<JsonElement> call = retrofit_api.loginResponse(email, password);

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(@NonNull Call<JsonElement> call, @NonNull Response<JsonElement> response) {
                if (response.isSuccessful()) {
                    Log.d("RetrofitManager", "onResponse : 성공, 결과 : " + response.body());
                    Log.d("RetrofitManager", "onResponse : status code is " + response.code());
                }
                else {
                    Log.d("RetrofitManager", "onResponse : 실패, 결과 : " + response.body());
                    Log.d("RetrofitManager", "onResponse : status code is " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonElement> call, @NonNull Throwable t) {
                Log.e("RetrofitManager", "onFailure : " + t.getLocalizedMessage());
            }
        });
    }

    public void register(String email, String password) {
        Call<JsonElement> call = retrofit_api.registerRequest(email, password);

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(@NonNull Call<JsonElement> call, @NonNull Response<JsonElement> response) {
                if (response.isSuccessful()) {
                    Log.d("RetrofitManager", "onResponse : 성공, 결과 : " + response.body());
                    Log.d("RetrofitManager", "onResponse : status code is " + response.code());
                }
                else {
                    Log.d("RetrofitManager", "onResponse : 실패, 결과 : " + response.body());
                    Log.d("RetrofitManager", "onResponse : status code is " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonElement> call, @NonNull Throwable t) {
                Log.d("RetrofitManager", "onFailure : " + t.getLocalizedMessage());
            }
        });
    }

    public void info(String name, String gender, int age, int height, String role) {
        Call<JsonElement> call = retrofit_api.infoRequest(name, gender, age, height, role);

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(@NonNull Call<JsonElement> call, @NonNull Response<JsonElement> response) {
                if (response.isSuccessful()) {
                    Log.d("RetrofitManager", "onResponse : 성공, 결과 : " + response.body());
                    Log.d("RetrofitManager", "onResponse : status code is " + response.code());
                }
                else {
                    Log.d("RetrofitManager", "onResponse : 실패, 결과 : " + response.body());
                    Log.d("RetrofitManager", "onResponse : status code is " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonElement> call, @NonNull Throwable t) {
                Log.d("RetrofitManager", "onFailure : " + t.getLocalizedMessage());
            }
        });
    }
}
