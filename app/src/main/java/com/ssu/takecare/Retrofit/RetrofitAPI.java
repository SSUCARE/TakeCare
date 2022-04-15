package com.ssu.takecare.Retrofit;

import com.google.gson.JsonElement;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitAPI {
    @FormUrlEncoded
    @POST("users/login")
    Call<JsonElement> loginRequest(@Field("email") String email_login, @Field("password") String password_login);

    @GET("users/login")
    Call<JsonElement> loginResponse(@Query("email") String email_login, @Query("password") String password_login);

    @FormUrlEncoded
    @POST("users/signup")
    Call<JsonElement> registerRequest(@Field("email") String email_register, @Field("password") String password_register);

    @FormUrlEncoded
    @POST("users/info")
    Call<JsonElement> infoRequest(@Field("name") String name_info,
                                      @Field("gender") String gender_info,
                                      @Field("age") int age_info,
                                      @Field("height") int height_info,
                                      @Field("role") String role_info);
}
