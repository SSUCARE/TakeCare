package com.designproject.takecare.KakaoLogin;

import android.util.Log;
import com.kakao.auth.ISessionCallback;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.usermgmt.response.model.Profile;
import com.kakao.usermgmt.response.model.UserAccount;
import com.kakao.util.exception.KakaoException;

public class SessionCallback implements ISessionCallback {

    // 로그인에 성공한 상태
    @Override
    public void onSessionOpened() {
        requestMe();
    }

    // 로그인에 실패한 상태
    @Override
    public void onSessionOpenFailed(KakaoException exception) {
        Log.e("SessionCallback :: ", "onSessionOpenFailed : " + exception.getMessage());
    }

    // 사용자 정보 요청
    public void requestMe() {
        UserManagement.getInstance()
                .me(new MeV2ResponseCallback() {
                    @Override
                    public void onSessionClosed(ErrorResult errorResult) {
                        Log.e("KAKAO_API", "세션이 닫혀 있음: " + errorResult);
                    }

                    @Override
                    public void onFailure(ErrorResult errorResult) {
                        Log.e("KAKAO_API", "onFailure : 사용자 정보 요청 실패: " + errorResult);
                    }

                    @Override
                    public void onSuccess(MeV2Response result) {
                        Log.i("KAKAO_API", "사용자 아이디: " + result.getId());
                        String id = String.valueOf(result.getId());
                        UserAccount kakaoAccount = result.getKakaoAccount();
                        if (kakaoAccount != null) {
                            String email = kakaoAccount.getEmail();
                            if (email != null) {
                                Log.d("KAKAO_API", "onSuccess : email "+email);
                            }

                            Profile profile = kakaoAccount.getProfile();
                            if (profile ==null) {
                                Log.d("KAKAO_API", "onSuccess : profile null ");
                            }
                            else {
                                Log.d("KAKAO_API", "onSuccess : getProfileImageUrl " + profile.getProfileImageUrl());
                                Log.d("KAKAO_API", "onSuccess : getNickname " + profile.getNickname());
                            }
                        }
                        else {
                            Log.d("KAKAO_API", "onSuccess : kakaoAccount null");
                        }
                    }
                });

    }
}