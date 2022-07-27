package com.ssu.takecare.Retrofit;

import android.util.Log;
import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.ssu.takecare.ApplicationClass;
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
import com.ssu.takecare.Retrofit.RetrofitCustomCallback.RetrofitCareCallback;
import com.ssu.takecare.Retrofit.RetrofitCustomCallback.RetrofitCommentCallback;
import com.ssu.takecare.Retrofit.RetrofitCustomCallback.RetrofitCommentIdCallback;
import com.ssu.takecare.Retrofit.RetrofitCustomCallback.RetrofitReportCallback;
import com.ssu.takecare.Retrofit.RetrofitCustomCallback.RetrofitUserInfoCallback;
import com.ssu.takecare.Retrofit.Signup.RequestSignup;
import com.ssu.takecare.Retrofit.Signup.ResponseSignup;
import com.ssu.takecare.Retrofit.UpdateReport.ResponseUpdateReport;
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
                if (response.isSuccessful()) {
                    ResponseReport body = response.body();
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
                if (response.isSuccessful()) {
                    ResponseUpdateReport body = response.body();
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
        Call<Void> call = ApplicationClass.retrofit_api.careRequest(userId);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("RetrofitManager_careRequest", "onResponse : status code is " + response.code());

                    callback.onSuccess("careRequest 호출 : ", "상대방 추가완료");
                }
                else {
                    Log.d("RetrofitManager_careRequest", "onResponse : 실패, error code : " + response.code());

                    callback.onFailure(response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
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
                if (response.isSuccessful()) {
                    Object body = response.body();
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
                if (response.isSuccessful()) {
                    Object body = response.body();
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
                if (response.isSuccessful()) {
                    Object body = response.body();
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
