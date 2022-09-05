package com.ssu.takecare.retrofit;

import com.ssu.takecare.retrofit.comment.RequestComment;
import com.ssu.takecare.retrofit.comment.ResponseComment;
import com.ssu.takecare.retrofit.comment.ResponseGetComment;
import com.ssu.takecare.retrofit.match.Response_Resquest_Care;
import com.ssu.takecare.retrofit.report.ResponseGetReport;
import com.ssu.takecare.retrofit.info.RequestInfo;
import com.ssu.takecare.retrofit.info.ResponseInfo;
import com.ssu.takecare.retrofit.login.RequestLogin;
import com.ssu.takecare.retrofit.login.ResponseLogin;
import com.ssu.takecare.retrofit.match.ResponseCare;
import com.ssu.takecare.retrofit.match.ResponseGetUser;
import com.ssu.takecare.retrofit.password.RequestChangePassword;
import com.ssu.takecare.retrofit.password.ResponseChangePassword;
import com.ssu.takecare.retrofit.password.ResponseFindPassword;
import com.ssu.takecare.retrofit.report.RequestReport;
import com.ssu.takecare.retrofit.report.ResponseReport;
import com.ssu.takecare.retrofit.signup.RequestSignup;
import com.ssu.takecare.retrofit.signup.ResponseSignup;
import com.ssu.takecare.retrofit.report.ResponseUpdateReport;
import retrofit2.Call;
import retrofit2.http.*;

public interface RetrofitAPI {
    // 회원가입
    @Headers("Content-Type: application/json")
    @POST("/users")
    Call<ResponseSignup> registerRequest(@Body RequestSignup body);

    // 로그인
    @Headers("Content-Type: application/json")
    @POST("/users/login")
    Call<ResponseLogin> loginRequest(@Body RequestLogin body);

    // 토큰 등록
    @Headers("Content-Type: application/json")
    @POST("/users/token")
    Call<Object> tokenPostRequest(@Query("token") String token);

    // 토큰 삭제
    @Headers("Content-Type: application/json")
    @DELETE("/users/token")
    Call<Object> tokenDeleteRequest();

    // 회원정보 저장
    @Headers("Content-Type: application/json")
    @PUT("/users")
    Call<ResponseInfo> infoRequest(@Body RequestInfo body);

    // 회원정보 조회
    @Headers("Content-Type: application/json")
    @GET("/users")
    Call<ResponseGetUser> infoCheckRequest();

    // 이메일로 회원 검색
    @Headers("Content-Type: application/json")
    @GET("/users/{email}")
    Call<ResponseGetUser> searchByEmailRequest(@Path("email") String path);

    // 비밀번호 찾기
    @Headers("Content-Type: application/json")
    @GET("/users/password")
    Call<ResponseFindPassword> findPasswordRequest(@Query("email") String email);

    // 비밀번호 수정
    @Headers("Content-Type: application/json")
    @POST("/users/password")
    Call<ResponseChangePassword> changePasswordRequest(@Body RequestChangePassword body);

    // report 생성
    @Headers("Content-Type: application/json")
    @POST("/report")
    Call<ResponseReport> reportRequest(@Body RequestReport body);

    //report 수정
    @Headers("Content-Type: application/json")
    @PUT("/report/{reportId}/")
    Call<ResponseUpdateReport> update_reportRequest(@Path("reportId")int path, @Body RequestReport body);

    // report 조회
    @Headers("Content-Type: application/json")
    @GET("/report/{userId}/")
    Call<ResponseGetReport> getReportRequest(@Path("userId") int path, @Query("year") int year, @Query("month") int month, @Query("day") int day);

    // 월별 report 조회
    @Headers("Content-Type: application/json")
    @GET("/report/{userId}/")
    Call<ResponseGetReport> getReportRequest_Month(@Path("userId")int path, @Query("year")int year, @Query("month")int month);

    // 케어 목록 보기
    @Headers("Content-Type: application/json")
    @GET("/care")
    Call<ResponseCare> getCareDBRequest();

    // 케어 요청
    @Headers("Content-Type: application/json")
    @POST("/care/request/{userId}")
    Call<Response_Resquest_Care> careRequest(@Path("userId") int path);

    // 케어 수락
    @Headers("Content-Type: application/json")
    @POST("/care/request/{userId}/accept")
    Call<Object> careAcceptRequest(@Path("userId") int path);

    // 케어 삭제
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
