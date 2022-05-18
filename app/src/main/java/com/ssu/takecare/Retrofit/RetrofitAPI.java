package com.ssu.takecare.Retrofit;

import com.google.gson.JsonElement;
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
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitAPI {
    // 회원가입
    @Headers("Content-Type: application/json")
    @POST("users")
    Call<ResponseSignup> registerRequest(@Body RequestSignup body);

    // 로그인
    @Headers("Content-Type: application/json")
    @POST("users/login")
    Call<ResponseLogin> loginRequest(@Body RequestLogin body);

    // 개인정보 저장
    @Headers("Content-Type: application/json")
    @PUT("users")
    Call<ResponseInfo> infoRequest(@Body RequestInfo body);

    // report 생성
    @Headers("Content-Type: application/json")
    @POST("/report")
    Call<ResponseReport> reportRequest(@Body RequestReport body);

    //
    @Headers("Content-Type: application/json")
    @GET("/users")
    Call<ResponseInfoCheck> infoCheckRequest();
    /*
        @Headers("Content-Type: application/json")
        @GET("/users/{userId}")
        Call<ResponseGetReport>GetReportRequest(@Path("userId")String id,@Query("???")String query);
    */

    //
    @Headers("Content-Type: application/json")
    @GET("/users/{userId}/")
    Call<ResponseGetReport> getReportRequest(@Path("userId")int path, @Query("year")int year, @Query("month")int month, @Query("date")int date);
}
