package com.ssu.takecare.Retrofit;

import android.util.Log;
import androidx.annotation.NonNull;
import com.google.gson.JsonElement;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.ssu.takecare.Retrofit.Login.RequestLogin;
import com.ssu.takecare.Retrofit.Login.ResponseLogin;
import com.ssu.takecare.Retrofit.Signup.RequestSignup;
import com.ssu.takecare.Retrofit.Signup.ResponseSignup;

public class RetrofitManager {
    private final String BASE_URL = "http://10.0.2.2:8080/";
    private RetrofitAPI retrofit_api = RetrofitClient.getClient(BASE_URL).create(RetrofitAPI.class);

    public void signup(String email, String password, RetrofitCallback callback) {
        RequestSignup requestSignup = new RequestSignup();
        requestSignup.setEmail(email);
        requestSignup.setPassword(password);

        Call<ResponseSignup> call = retrofit_api.registerRequest(requestSignup);

        call.enqueue(new Callback<ResponseSignup>() {
            @Override
            public void onResponse(@NonNull Call<ResponseSignup> call, @NonNull Response<ResponseSignup> response) {
                if (response.isSuccessful()) {
                    ResponseSignup body = response.body();
                    Log.d("RetrofitManager", "onResponse : 성공, message : " + body.toString());
                    Log.d("RetrofitManager", "onResponse : status code is " + response.code());

                    callback.onSuccess(body.message, body.data_email);
                }
                else {
                    Log.d("RetrofitManager", "onResponse : 실패, error code : " + response.code());

                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseSignup> call, @NonNull Throwable t) {
                Log.d("RetrofitManager", "onFailure : " + t.getLocalizedMessage());

                callback.onError(t);
            }
        });
    }

    public void login(String email, String password, RetrofitCallback callback) {
        RequestLogin requestLogin = new RequestLogin();
        requestLogin.setEmail(email);
        requestLogin.setPassword(password);

        Call<ResponseLogin> call = retrofit_api.loginRequest(requestLogin);

        call.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(@NonNull Call<ResponseLogin> call, @NonNull Response<ResponseLogin> response) {
                if (response.isSuccessful()) {
                    ResponseLogin body = response.body();
                    Log.d("RetrofitManager", "onResponse : 성공, message : " + body.toString());
                    Log.d("RetrofitManager", "onResponse : status code is " + response.code());

                    callback.onSuccess(body.message, body.data_accessToken);
                }
                else {
                    Log.d("RetrofitManager", "onResponse : 실패, error code : " + response.code());

                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseLogin> call, @NonNull Throwable t) {
                Log.e("RetrofitManager", "onFailure : " + t.getLocalizedMessage());

                callback.onError(t);
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
