package com.ssu.takecare.Retrofit;

import android.util.Log;
import androidx.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.ssu.takecare.ApplicationClass;
import com.ssu.takecare.Retrofit.GetReport.ResponseGetReport;
import com.ssu.takecare.Retrofit.Info.RequestInfo;
import com.ssu.takecare.Retrofit.Info.ResponseInfo;
import com.ssu.takecare.Retrofit.InfoCheck.ResponseInfoCheck;
import com.ssu.takecare.Retrofit.Login.RequestLogin;
import com.ssu.takecare.Retrofit.Login.ResponseLogin;
import com.ssu.takecare.Retrofit.Report.RequestReport;
import com.ssu.takecare.Retrofit.Report.ResponseReport;
import com.ssu.takecare.Retrofit.Signup.RequestSignup;
import com.ssu.takecare.Retrofit.Signup.ResponseSignup;

import java.util.List;

public class RetrofitManager {

    public void signup(String email, String password, RetrofitCallback callback) {
        RequestSignup requestSignup = new RequestSignup();
        requestSignup.setEmail(email);
        requestSignup.setPassword(password);

        Call<ResponseSignup> call = ApplicationClass.retrofit_api.registerRequest(requestSignup);

        call.enqueue(new Callback<ResponseSignup>() {
            @Override
            public void onResponse(@NonNull Call<ResponseSignup> call, @NonNull Response<ResponseSignup> response) {
                if (response.isSuccessful()) {
                    ResponseSignup body = response.body();
                    Log.d("RetrofitManager_signup", "onResponse : 성공, message : " + body.toString());
                    Log.d("RetrofitManager_signup", "onResponse : status code is " + response.code());

                    callback.onSuccess(body.message, body.data_email);
                }
                else {
                    Log.d("RetrofitManager_signup", "onResponse : 실패, error code : " + response.code());

                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseSignup> call, @NonNull Throwable t) {
                Log.d("RetrofitManager_signup", "onFailure : " + t.getLocalizedMessage());

                callback.onError(t);
            }
        });
    }

    public void login(String email, String password, RetrofitCallback callback) {
        RequestLogin requestLogin = new RequestLogin();
        requestLogin.setEmail(email);
        requestLogin.setPassword(password);

        Call<ResponseLogin> call = ApplicationClass.retrofit_api.loginRequest(requestLogin);

        call.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(@NonNull Call<ResponseLogin> call, @NonNull Response<ResponseLogin> response) {
                if (response.isSuccessful()) {
                    ResponseLogin body = response.body();
                    Log.d("RetrofitManager_login", "onResponse : 성공, message : " + body.toString());
                    Log.d("RetrofitManager_login", "onResponse : status code is " + response.code());

                    callback.onSuccess(body.message, body.data_accessToken);
                }
                else {
                    Log.d("RetrofitManager_login", "onResponse : 실패, error code : " + response.code());

                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseLogin> call, @NonNull Throwable t) {
                Log.e("RetrofitManager_login", "onFailure : " + t.getLocalizedMessage());

                callback.onError(t);
            }
        });
    }

    public void info(String name, String gender, int age, int height, String role, RetrofitCallback callback) {
        RequestInfo requestInfo = new RequestInfo();
        requestInfo.setName(name);
        requestInfo.setGender(gender);
        requestInfo.setAge(age);
        requestInfo.setHeight(height);
        requestInfo.setRole(role);
        requestInfo.setWeight(60);

        Call<ResponseInfo> call = ApplicationClass.retrofit_api.infoRequest(requestInfo);

        call.enqueue(new Callback<ResponseInfo>() {
            @Override
            public void onResponse(@NonNull Call<ResponseInfo> call, @NonNull Response<ResponseInfo> response) {
                if (response.isSuccessful()) {
                    ResponseInfo body = response.body();
                    Log.d("RetrofitManager_info", "onResponse : 성공, message : " + body.toString());
                    Log.d("RetrofitManager_info", "onResponse : status code is " + response.code());

                    callback.onSuccess(body.message, body.data);
                }
                else {
                    Log.d("RetrofitManager_info", "onResponse : 실패, error code : " + response.code());

                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseInfo> call, @NonNull Throwable t) {
                Log.e("RetrofitManager_info", "onFailure : " + t.getLocalizedMessage());

                callback.onError(t);
            }
        });
    }

    public void makeReport(int systolic, int diastolic, List<Integer> sugarLevels, int weight, RetrofitCallback callback) {
        RequestReport requestReport = new RequestReport();
        requestReport.setDiastolic(diastolic);
        requestReport.setSugarLevels(sugarLevels);
        requestReport.setSystolic(systolic);
        requestReport.setWeight(weight);

        Call<ResponseReport> call = ApplicationClass.retrofit_api.reportRequest(requestReport);

        call.enqueue(new Callback<ResponseReport>() {
            @Override
            public void onResponse(@NonNull Call<ResponseReport> call, @NonNull Response<ResponseReport> response) {
                if (response.isSuccessful()) {
                    ResponseReport body = response.body();
                    Log.d("RetrofitManager_report", "onResponse : 성공, message : " + body.toString());
                    Log.d("RetrofitManager_report", "onResponse : status code is " + response.code());

                    callback.onSuccess(body.message, body.message);
                }
                else {
                    Log.d("RetrofitManager_report", "onResponse : 실패, error code : " + response.code());

                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseReport> call, @NonNull Throwable t) {
                Log.e("RetrofitManager_report", "onFailure : " + t.getLocalizedMessage());

                callback.onError(t);
            }
        });
    }

    public void infoCheck(RetrofitCallback callback) {
        Call<ResponseInfoCheck> call = ApplicationClass.retrofit_api.infoCheckRequest();

        call.enqueue(new Callback<ResponseInfoCheck>() {
            @Override
            public void onResponse(@NonNull Call<ResponseInfoCheck> call, @NonNull Response<ResponseInfoCheck> response) {
                if (response.isSuccessful()) {
                    ResponseInfoCheck body = response.body();
                    Log.d("RetrofitManager_check", "onResponse : 성공, message : " + body.toString());
                    Log.d("RetrofitManager_check", "onResponse : status code is " + response.code());

                    callback.onSuccess(body.message, "userId : " + body.getDatainfocheck().getId() + "name : " + body.getDatainfocheck().getName());
                }
                else {
                    Log.d("RetrofitManager_check", "onResponse : 실패, error code : " + response.code());

                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseInfoCheck> call, @NonNull Throwable t) {
                Log.e("RetrofitManager_check", "onFailure : " + t.getLocalizedMessage());

                callback.onError(t);
            }
        });
    }

    public void getReport(int path, int year, int month, int date, RetrofitCallback callback) {
        Call<ResponseGetReport>call=ApplicationClass.retrofit_api.getReportRequest(1,year,month,date);

        call.enqueue(new Callback<ResponseGetReport>() {
            @Override
            public void onResponse(@NonNull Call<ResponseGetReport> call, @NonNull Response<ResponseGetReport> response) {
                if (response.isSuccessful()) {
                    ResponseGetReport body = response.body();
                    Log.d("RetrofitManager", "ReportonResponse : 성공, message : " + body.toString());
                    Log.d("RetrofitManager", "ReportonResponse : status code is " + response.code());
                    String data="고혈압:"+body.getDatagetreprot().diastolic;
                    data+="저혈압:"+body.getDatagetreprot().sytstolic;
                    callback.onSuccess(body.message, data);
                } else {
                    Log.d("RetrofitManager", "ReportonResponse : 실패, error code : " + response.code());

                    callback.onFailure(response.code());

                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseGetReport> call, @NonNull Throwable t) {
                Log.e("RetrofitManager", "ReportonFailure : " + t.getLocalizedMessage());

                callback.onError(t);
            }
        });
    }
}
