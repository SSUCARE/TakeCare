package com.ssu.takecare.retrofit;

import android.util.Log;
import androidx.annotation.NonNull;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.google.gson.Gson;
import com.ssu.takecare.ApplicationClass;
import com.ssu.takecare.retrofit.comment.RequestComment;
import com.ssu.takecare.retrofit.comment.ResponseComment;
import com.ssu.takecare.retrofit.comment.ResponseGetComment;
import com.ssu.takecare.retrofit.match.Response_Resquest_Care;
import com.ssu.takecare.retrofit.report.ResponseGetReport;
import com.ssu.takecare.retrofit.info.RequestInfo;
import com.ssu.takecare.retrofit.info.ResponseInfo;
import com.ssu.takecare.retrofit.login.LoginError;
import com.ssu.takecare.retrofit.login.RequestLogin;
import com.ssu.takecare.retrofit.login.ResponseLogin;
import com.ssu.takecare.retrofit.match.ResponseCare;
import com.ssu.takecare.retrofit.match.ResponseGetUser;
import com.ssu.takecare.retrofit.password.RequestChangePassword;
import com.ssu.takecare.retrofit.password.ResponseChangePassword;
import com.ssu.takecare.retrofit.password.ResponseFindPassword;
import com.ssu.takecare.retrofit.report.RequestReport;
import com.ssu.takecare.retrofit.report.ResponseReport;
import com.ssu.takecare.retrofit.customcallback.RetrofitCareCallback;
import com.ssu.takecare.retrofit.customcallback.RetrofitCommentCallback;
import com.ssu.takecare.retrofit.customcallback.RetrofitCommentIdCallback;
import com.ssu.takecare.retrofit.customcallback.RetrofitErrorCallback;
import com.ssu.takecare.retrofit.customcallback.RetrofitReportCallback;
import com.ssu.takecare.retrofit.customcallback.RetrofitUserInfoCallback;
import com.ssu.takecare.retrofit.signup.RequestSignup;
import com.ssu.takecare.retrofit.signup.ResponseSignup;
import com.ssu.takecare.retrofit.signup.SignupError;
import com.ssu.takecare.retrofit.report.ResponseUpdateReport;
import java.io.IOException;
import java.util.List;

public class RetrofitManager {

    public void signup(String email, String password, RetrofitErrorCallback callback) {
        RequestSignup requestSignup = new RequestSignup();
        requestSignup.setEmail(email);
        requestSignup.setPassword(password);

        Call<ResponseSignup> call = ApplicationClass.retrofit_api.registerRequest(requestSignup);

        call.enqueue(new Callback<ResponseSignup>() {
            @Override
            public void onResponse(@NonNull Call<ResponseSignup> call, @NonNull Response<ResponseSignup> response) {
                if (response.isSuccessful()) {
                    ResponseSignup body = response.body();
                    Log.d("RetrofitManager_signup", "onResponse : 성공, message : " + body.getMessage());
                    Log.d("RetrofitManager_signup", "onResponse : status code is " + response.code());

                    callback.onSuccess(body.message, body.data_email);
                }
                else {
                    ResponseBody responseBody = response.errorBody();
                    Gson gson = new Gson();
                    try {
                        SignupError signupError = gson.fromJson(responseBody.string(), SignupError.class);
                        Log.d("RetrofitManager_signup", "onResponse : 실패, error message : " + signupError.getErrorMessage());
                        Log.d("RetrofitManager_signup", "onResponse : 실패, error code : " + response.code());

                        callback.onFailure(signupError.errorMessage, response.code());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseSignup> call, @NonNull Throwable t) {
                Log.d("RetrofitManager_signup", "onFailure : " + t.getLocalizedMessage());

                callback.onError(t);
            }
        });
    }

    public void login(String email, String password, RetrofitErrorCallback callback) {
        RequestLogin requestLogin = new RequestLogin();
        requestLogin.setEmail(email);
        requestLogin.setPassword(password);

        Call<ResponseLogin> call = ApplicationClass.retrofit_api.loginRequest(requestLogin);

        call.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(@NonNull Call<ResponseLogin> call, @NonNull Response<ResponseLogin> response) {
                if (response.isSuccessful()) {
                    ResponseLogin body = response.body();
                    Log.d("RetrofitManager_login", "onResponse : 성공, message : " + body.getMessage());
                    Log.d("RetrofitManager_login", "onResponse : status code is " + response.code());

                    callback.onSuccess(body.message, body.data_accessToken);
                }
                else {
                    ResponseBody responseBody = response.errorBody();
                    Gson gson = new Gson();
                    try {
                        LoginError loginError = gson.fromJson(responseBody.string(), LoginError.class);
                        Log.d("RetrofitManager_login", "onResponse : 실패, error message : " + loginError.getErrorMessage());
                        Log.d("RetrofitManager_login", "onResponse : 실패, error code : " + response.code());

                        callback.onFailure(loginError.errorMessage, response.code());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
                ResponseInfo body = response.body();
                if (response.isSuccessful()) {
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

    public void infoCheck(RetrofitUserInfoCallback callback) {
        Call<ResponseGetUser> call = ApplicationClass.retrofit_api.infoCheckRequest();

        call.enqueue(new Callback<ResponseGetUser>() {
            @Override
            public void onResponse(@NonNull Call<ResponseGetUser> call, @NonNull Response<ResponseGetUser> response) {
                if (response.isSuccessful()) {
                    ResponseGetUser body = response.body();
                    Log.d("RetrofitManager_infoCheck", "onResponse : 성공, message : " + body.getMessage());
                    Log.d("RetrofitManager_infoCheck", "onResponse : status code is " + response.code());

                    callback.onSuccess(body.message, body.getData());
                }
                else {
                    Log.d("RetrofitManager_infoCheck", "onResponse : 실패, error code : " + response.code());

                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseGetUser> call, @NonNull Throwable t) {
                Log.e("RetrofitManager_infoCheck", "onFailure : " + t.getLocalizedMessage());

                callback.onError(t);
            }
        });
    }

    public void getByEmailUserInfo(String email, RetrofitUserInfoCallback callback) {
        Call<ResponseGetUser> call = ApplicationClass.retrofit_api.searchByEmailRequest(email);

        call.enqueue(new Callback<ResponseGetUser>() {
            @Override
            public void onResponse(@NonNull Call<ResponseGetUser> call, @NonNull Response<ResponseGetUser> response) {
                if (response.isSuccessful()) {
                    ResponseGetUser body = response.body();
                    Log.d("RetrofitManager_getByEmailUserInfo", "onResponse : 성공, message : " + body.getMessage());
                    Log.d("RetrofitManager_getByEmailUserInfo", "onResponse : status code is " + response.code());

                    callback.onSuccess("searchByEmailRequest 호출 : ", body.getData());
                }
                else {
                    Log.d("RetrofitManager_getByEmailUserInfo", "onResponse : 실패, error code : " + response.code());

                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseGetUser> call, @NonNull Throwable t) {
                Log.e("RetrofitManager_getByEmailUserInfo", "onFailure : " + t.getLocalizedMessage());

                callback.onError(t);
            }
        });
    }

    public void findPassword(String email, RetrofitCallback callback) {
        Call<ResponseFindPassword> call = ApplicationClass.retrofit_api.findPasswordRequest(email);

        call.enqueue(new Callback<ResponseFindPassword>() {
            @Override
            public void onResponse(Call<ResponseFindPassword> call, Response<ResponseFindPassword> response) {
                if (response.isSuccessful()) {
                    ResponseFindPassword body = response.body();
                    Log.d("RetrofitManager_findPassword", "onResponse : 성공, message : " + body.getMessage());
                    Log.d("RetrofitManager_findPassword", "onResponse : status code is " + response.code());

                    callback.onSuccess("findPassword 호출 : ", body.getMessage());
                }
                else {
                    Log.d("RetrofitManager_findPassword", "onResponse : 실패, error code : " + response.code());

                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseFindPassword> call, Throwable t) {
                Log.e("RetrofitManager_findPassword", "onFailure : " + t.getLocalizedMessage());

                callback.onError(t);
            }
        });
    }

    public void changePassword(String current_email, String new_password, RetrofitCallback callback) {
        RequestChangePassword requestChangePassword = new RequestChangePassword();
        requestChangePassword.setCurrentPassword(current_email);
        requestChangePassword.setNewPassword(new_password);

        Call<ResponseChangePassword> call = ApplicationClass.retrofit_api.changePasswordRequest(requestChangePassword);

        call.enqueue(new Callback<ResponseChangePassword>() {
            @Override
            public void onResponse(Call<ResponseChangePassword> call, Response<ResponseChangePassword> response) {
                if (response.isSuccessful()) {
                    ResponseChangePassword body = response.body();
                    Log.d("RetrofitManager_ChangePassword", "onResponse : 성공, message : " + body.getMessage());
                    Log.d("RetrofitManager_ChangePassword", "onResponse : status code is " + response.code());

                    callback.onSuccess("ChangePassword 호출 : ", body.getMessage());
                }
                else {
                    Log.d("RetrofitManager_ChangePassword", "onResponse : 실패, error code : " + response.code());

                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseChangePassword> call, Throwable t) {
                Log.e("RetrofitManager_ChangePassword", "onFailure : " + t.getLocalizedMessage());

                callback.onError(t);
            }
        });
    }

    public void makeReport(int systolic, int diastolic, List<Integer> sugarLevels, int weight, RetrofitCallback callback) {
        RequestReport requestReport = new RequestReport();
        requestReport.setSystolic(systolic);
        requestReport.setDiastolic(diastolic);
        requestReport.setSugarLevels(sugarLevels);
        requestReport.setWeight(weight);

        Call<ResponseReport> call = ApplicationClass.retrofit_api.reportRequest(requestReport);

        call.enqueue(new Callback<ResponseReport>() {
            @Override
            public void onResponse(@NonNull Call<ResponseReport> call, @NonNull Response<ResponseReport> response) {
                ResponseReport body = response.body();
                if (response.isSuccessful()) {
                    Log.d("RetrofitManager_makeReport", "onResponse : 성공, message : " + body.getMessage());
                    Log.d("RetrofitManager_makeReport", "onResponse : status code is " + response.code());

                    callback.onSuccess(body.message, body.message);
                }
                else {
                    Log.d("RetrofitManager_makeReport", "onResponse : 실패, error code : " + response.code());

                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseReport> call, @NonNull Throwable t) {
                Log.e("RetrofitManager_makeReport", "onFailure : " + t.getLocalizedMessage());

                callback.onError(t);
            }
        });
    }

    public void updateReport(int reportId,int systolic, int diastolic, List<Integer> sugarLevels, int weight, RetrofitCallback callback) {
        RequestReport requestReport = new RequestReport();
        requestReport.setSystolic(systolic);
        requestReport.setDiastolic(diastolic);
        requestReport.setSugarLevels(sugarLevels);
        requestReport.setWeight(weight);

        Call<ResponseUpdateReport> call = ApplicationClass.retrofit_api.update_reportRequest(reportId,requestReport);

        call.enqueue(new Callback<ResponseUpdateReport>() {
            @Override
            public void onResponse(@NonNull Call<ResponseUpdateReport> call, @NonNull Response<ResponseUpdateReport> response) {
                ResponseUpdateReport body = response.body();
                if (response.isSuccessful()) {
                    Log.d("RetrofitManager_updateReport", "onResponse : 성공, message : " + body.getMessage());
                    Log.d("RetrofitManager_updateReport", "onResponse : status code is " + response.code());

                    callback.onSuccess(body.message, body.message);
                }
                else {
                    Log.d("RetrofitManager_updateReport", "onResponse : 실패, error code : " + response.code());

                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseUpdateReport> call, @NonNull Throwable t) {
                Log.e("RetrofitManager_updateReport", "onFailure : " + t.getLocalizedMessage());
                callback.onError(t);
            }
        });
    }

    public void getReport(int path, int year, int month, int day, RetrofitReportCallback callback) {
        Call<ResponseGetReport> call = ApplicationClass.retrofit_api.getReportRequest(path, year, month, day);

        call.enqueue(new Callback<ResponseGetReport>() {
            @Override
            public void onResponse(@NonNull Call<ResponseGetReport> call, @NonNull Response<ResponseGetReport> response) {
                if (response.isSuccessful()) {
                    ResponseGetReport body = response.body();
                    Log.d("RetrofitManager_getReport", "onResponse : 성공, message : " + body.getMessage());
                    Log.d("RetrofitManager_getReport", "onResponse : status code is " + response.code());

                    callback.onSuccess(body.message, body.getData());
                }
                else {
                    Log.d("RetrofitManager_getReport", "onResponse : 실패, error code : " + response.code());

                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseGetReport> call, @NonNull Throwable t) {
                Log.e("RetrofitManager_getReport", "onFailure : " + t.getLocalizedMessage());

                callback.onError(t);
            }
        });
    }

    // 월별 리포트 조회
    public void getReport_Month(int path, int year, int month, RetrofitReportCallback callback) {
        Call<ResponseGetReport> call = ApplicationClass.retrofit_api.getReportRequest_Month(path, year, month);

        call.enqueue(new Callback<ResponseGetReport>() {
            @Override
            public void onResponse(@NonNull Call<ResponseGetReport> call, @NonNull Response<ResponseGetReport> response) {
                if (response.isSuccessful()) {
                    ResponseGetReport body = response.body();
                    Log.d("RetrofitManager_getReport_Month", "onResponse : 성공, message : " + body.getMessage());
                    Log.d("RetrofitManager_getReport_Month", "onResponse : status code is " + response.code());
                    callback.onSuccess(body.message, body.getData());

                }
                else {
                    Log.d("디버그,RetrofitManager_getReport_Month", "onResponse : 실패, error code : " + response.code());
                    Log.d("디버그,RetrofitManager_getReport_Month","메세지:"+response.message());
                    callback.onFailure(response.code());
                }
            }
            @Override
            public void onFailure(@NonNull Call<ResponseGetReport> call, @NonNull Throwable t) {
                Log.e("RetrofitManager_getReport_Month", "onFailure : " + t.getLocalizedMessage());
                callback.onError(t);
            }
        });
    }

    public void getCareDBMatchInfo(RetrofitCareCallback callback) {
        Call<ResponseCare> call = ApplicationClass.retrofit_api.getCareDBRequest();

        call.enqueue(new Callback<ResponseCare>() {
            @Override
            public void onResponse(@NonNull Call<ResponseCare> call, @NonNull Response<ResponseCare> response) {
                if (response.isSuccessful()) {
                    ResponseCare body = response.body();
                    Log.d("RetrofitManager_matchInfo", "onResponse : 성공, message : " + body.getMessage());
                    Log.d("RetrofitManager_matchInfo", "onResponse : status code is " + response.code());

                    callback.onSuccess("GetCareDBRequest 호출 : ", body);
                }
                else {
                    Log.d("RetrofitManager_matchInfo", "onResponse : 실패, error code : " + response.code());

                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseCare> call, @NonNull Throwable t) {
                Log.e("RetrofitManager_matchInfo", "onFailure : " + t.getLocalizedMessage());

                callback.onError(t);
            }
        });
    }

    public void careRequest(int userId, RetrofitCallback callback) {
        Call<Response_Resquest_Care> call = ApplicationClass.retrofit_api.careRequest(userId);

        call.enqueue(new Callback<Response_Resquest_Care>() {
            @Override
            public void onResponse(@NonNull Call<Response_Resquest_Care> call, @NonNull Response<Response_Resquest_Care> response) {
                if (response.isSuccessful()) {
                    Log.d("RetrofitManager_careRequest", "onResponse : status code is " + response.code());

                    callback.onSuccess("careRequest 호출 : ", Integer.toString(response.body().getData()));
                }
                else {
                    Log.d("RetrofitManager_careRequest", "onResponse : 실패, error code : " + response.code());

                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Response_Resquest_Care> call, @NonNull Throwable t) {
                Log.e("RetrofitManager_careRequest", "onFailure : " + t.getLocalizedMessage());

                callback.onError(t);
            }
        });
    }

    public void careAcceptRequest(int userId, RetrofitCallback callback) {
        Call<Object> call = ApplicationClass.retrofit_api.careAcceptRequest(userId);

        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(@NonNull Call<Object> call, @NonNull Response<Object> response) {
                Object body = response.body();
                if (response.isSuccessful()) {
                    Log.d("RetrofitManager_careAcceptRequest", "onResponse : 성공, message : " + body.toString());
                    Log.d("RetrofitManager_careAcceptRequest", "onResponse : status code is " + response.code());
                    
                    callback.onSuccess("careAcceptRequest 호출 : ", body.toString());
                }
                else {
                    Log.d("RetrofitManager_careAcceptRequest", "onResponse : 실패, error code : " + response.code());

                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Object> call, @NonNull Throwable t) {
                Log.e("RetrofitManager_careAcceptRequest", "onFailure : " + t.getLocalizedMessage());

                callback.onError(t);
            }
        });
    }

    public void careDeleteRequest(int userId, RetrofitCallback callback) {
        Call<Object> call = ApplicationClass.retrofit_api.careDeleteRequest(userId);

        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(@NonNull Call<Object> call, @NonNull Response<Object> response) {
                Object body = response.body();
                if (response.isSuccessful()) {
                    Log.d("RetrofitManager_careDeleteRequest", "onResponse : 성공, message : " + body.toString());
                    Log.d("RetrofitManager_careDeleteRequest", "onResponse : status code is " + response.code());

                    callback.onSuccess("careDeleteRequest 호출 : ", body.toString());
                }
                else {
                    Log.d("RetrofitManager_careDeleteRequest", "onResponse : 실패, error code : " + response.code());

                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Object> call, @NonNull Throwable t) {
                Log.e("RetrofitManager_careDeleteRequest", "onFailure : " + t.getLocalizedMessage());

                callback.onError(t);
            }
        });
    }

    public void makeComment(String content, int reportId, RetrofitCommentIdCallback callback) {
        RequestComment requestComment = new RequestComment();
        requestComment.setContent(content);
        requestComment.setReportId(reportId);

        Call<ResponseComment> call = ApplicationClass.retrofit_api.commentRequest(requestComment);

        call.enqueue(new Callback<ResponseComment>() {
            @Override
            public void onResponse(@NonNull Call<ResponseComment> call, @NonNull Response<ResponseComment> response) {
                if (response.isSuccessful()) {
                    ResponseComment body = response.body();
                    Log.d("RetrofitManager_makeComment", "onResponse : 성공, message : " + body.toString());
                    Log.d("RetrofitManager_makeComment", "onResponse : status code is " + response.code());

                    callback.onSuccess(body.message, body.commentId);
                } else {
                    Log.d("RetrofitManager_makeComment", "onResponse : 실패, error code : " + response.code());

                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseComment> call, @NonNull Throwable t) {
                Log.e("RetrofitManager_makeComment", "onFailure : " + t.getLocalizedMessage());

                callback.onError(t);
            }
        });
    }

    public void getComment(int reportId, RetrofitCommentCallback callback) {
        Call<ResponseGetComment> call = ApplicationClass.retrofit_api.getCommentRequest(reportId);

        call.enqueue(new Callback<ResponseGetComment>() {
            @Override
            public void onResponse(@NonNull Call<ResponseGetComment> call, @NonNull Response<ResponseGetComment> response) {
                if (response.isSuccessful()) {
                    ResponseGetComment body = response.body();
                    Log.d("RetrofitManager_getComment", "onResponse : 성공, message : " + body.getMessage());
                    Log.d("RetrofitManager_getComment", "onResponse : status code is " + response.code());

                    callback.onSuccess(body.message, body.data);
                } else {
                    Log.d("RetrofitManager_getComment", "onResponse : 실패, error code : " + response.code());

                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseGetComment> call, @NonNull Throwable t) {
                Log.e("RetrofitManager_getComment", "onFailure : " + t.getLocalizedMessage());

                callback.onError(t);
            }
        });
    }

    public void deleteComment(int commentId, RetrofitCallback callback) {
        Call<Object> call = ApplicationClass.retrofit_api.commentDeleteRequest(commentId);

        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(@NonNull Call<Object> call, @NonNull Response<Object> response) {
                Object body = response.body();
                if (response.isSuccessful()) {
                    Log.d("RetrofitManager_deleteComment", "onResponse : 성공, message : " + body.toString());
                    Log.d("RetrofitManager_deleteComment", "onResponse : status code is " + response.code());

                    callback.onSuccess("deleteComment 호출 : ", body.toString());
                }
                else {
                    Log.d("RetrofitManager_deleteComment", "onResponse : 실패, error code : " + response.code());

                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Object> call, @NonNull Throwable t) {
                Log.e("RetrofitManager_deleteComment", "onFailure : " + t.getLocalizedMessage());

                callback.onError(t);
            }
        });
    }

    public void updateComment(int commentId, String content, RetrofitCommentIdCallback callback) {
        Call<ResponseComment> call = ApplicationClass.retrofit_api.commentUpdateRequest(commentId, content);

        call.enqueue(new Callback<ResponseComment>() {
            @Override
            public void onResponse(@NonNull Call<ResponseComment> call, @NonNull Response<ResponseComment> response) {
                if (response.isSuccessful()) {
                   ResponseComment body = response.body();
                    Log.d("RetrofitManager_updateComment", "onResponse : 성공, message : " + body.toString());
                    Log.d("RetrofitManager_updateComment", "onResponse : status code is " + response.code());

                    callback.onSuccess(body.message, body.commentId);
                }
                else {
                    Log.d("RetrofitManager_updateComment", "onResponse : 실패, error code : " + response.code());

                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseComment> call, @NonNull Throwable t) {
                Log.e("RetrofitManager_updateComment", "onFailure : " + t.getLocalizedMessage());

                callback.onError(t);
            }
        });
    }
}
