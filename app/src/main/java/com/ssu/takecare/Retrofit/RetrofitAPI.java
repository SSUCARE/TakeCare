package com.ssu.takecare.Retrofit;

import com.google.gson.JsonElement;
import com.ssu.takecare.Retrofit.Info.RequestInfo;
import com.ssu.takecare.Retrofit.Info.ResponseInfo;
import com.ssu.takecare.Retrofit.Login.RequestLogin;
import com.ssu.takecare.Retrofit.Login.ResponseLogin;
import com.ssu.takecare.Retrofit.Signup.RequestSignup;
import com.ssu.takecare.Retrofit.Signup.ResponseSignup;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface RetrofitAPI {

    @Headers("Content-Type: application/json")
    @POST("users")
    Call<ResponseSignup> registerRequest(@Body RequestSignup body);

    @Headers("Content-Type: application/json")
    @POST("users/login")
    Call<ResponseLogin> loginRequest(@Body RequestLogin body);

    @Headers("Content-Type: application/json")
    @PUT("users")
    Call<ResponseInfo> infoRequest(@Body RequestInfo body);
}
