package com.ssu.takecare.Retrofit;

import com.ssu.takecare.Retrofit.Comment.RequestComment;
import com.ssu.takecare.Retrofit.Comment.ResponseComment;
import com.ssu.takecare.Retrofit.Comment.ResponseGetComment;
import com.ssu.takecare.Retrofit.GetReport.ResponseGetReport;
import com.ssu.takecare.Retrofit.Info.RequestInfo;
import com.ssu.takecare.Retrofit.Info.ResponseInfo;
import com.ssu.takecare.Retrofit.Login.RequestLogin;
import com.ssu.takecare.Retrofit.Login.ResponseLogin;
import com.ssu.takecare.Retrofit.Match.ResponseCare;
import com.ssu.takecare.Retrofit.Match.ResponseGetUser;
import com.ssu.takecare.Retrofit.Report.RequestReport;
import com.ssu.takecare.Retrofit.Report.ResponseReport;
import com.ssu.takecare.Retrofit.Signup.RequestSignup;
import com.ssu.takecare.Retrofit.Signup.ResponseSignup;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitAPI {
    // 회원가입
    @Headers("Content-Type: application/json")
    @POST("/users")
    Call<ResponseSignup> registerRequest(@Body RequestSignup body);

    // 로그인
    @Headers("Content-Type: application/json")
    @POST("/users/login")
    Call<ResponseLogin> loginRequest(@Body RequestLogin body);

    // 회원정보 저장
    @Headers("Content-Type: application/json")
    @PUT("/users")
    Call<ResponseInfo> infoRequest(@Body RequestInfo body);

    // 회원정보 조회
    @Headers("Content-Type: application/json")
    @GET("/users")
    Call<ResponseGetUser> infoCheckRequest();

    @Headers("Content-Type: application/json")
    @GET("/users/{email}")
    Call<ResponseGetUser> searchByEmailRequest(@Path("email") String path);

    // report 생성
    @Headers("Content-Type: application/json")
    @POST("/report")
    Call<ResponseReport> reportRequest(@Body RequestReport body);

    // report 조회
    @Headers("Content-Type: application/json")
    @GET("/report/{userId}/")
    Call<ResponseGetReport> getReportRequest(@Path("userId") int path, @Query("year") int year, @Query("month") int month, @Query("date") int date);

    // 월별 report 조회
    @Headers("Content-Type: application/json")
    @GET("/report/{userId}/")
    Call<ResponseGetReport> getReportRequest_Month(@Path("userId")int path, @Query("year")int year, @Query("month")int month);

    @Headers("Content-Type: application/json")
    @GET("/care")
    Call<ResponseCare> getCareDBRequest();

    @Headers("Content-Type: application/json")
    @POST("/care/request/{userId}")
    Call<Void> careRequest(@Path("userId") int path);

    @Headers("Content-Type: application/json")
    @POST("/care/request/{userId}/accept")
    Call<Object> careAcceptRequest(@Path("userId") int path);

    @Headers("Content-Type: application/json")
    @DELETE("/care/{userId}")
    Call<Object> careDeleteRequest(@Path("userId") int path);

    // comment 생성
    @Headers("Content-Type: application/json")
    @POST("/comment")
    Call<ResponseComment> commentRequest(@Body RequestComment body);

    // comment 불러오기
    @Headers("Content-Type: application/json")
    @GET("/comment/{reportId}")
    Call<ResponseGetComment> getCommentRequest(@Path("reportId") int path);

    // comment 삭제
    @Headers("Content-Type: application/json")
    @DELETE("/comment/{commentId}")
    Call<Object> commentDeleteRequest(@Path("commentId") int path);

    // comment 수정
    @Headers("Content-Type: application/json")
    @PUT("/comment/{commentId}")
    Call<ResponseComment> commentUpdateRequest(@Path("commentId") int path, @Body String comment);
}
