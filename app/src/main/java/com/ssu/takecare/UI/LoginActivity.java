package com.ssu.takecare.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpResponse;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.NameValuePair;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.HttpClient;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.entity.UrlEncodedFormEntity;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.methods.HttpPost;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.impl.client.DefaultHttpClient;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.message.BasicNameValuePair;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.util.EntityUtils;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.ssu.takecare.ApplicationClass;
import com.ssu.takecare.Retrofit.Match.DataResponseGetUser;
import com.ssu.takecare.Retrofit.RetrofitCallback;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.Task;
import com.ssu.takecare.R;
import com.ssu.takecare.Retrofit.RetrofitCustomCallback.RetrofitUserInfoCallback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";
    private long backKeyPressedTime = 0;

    SharedPreferences.Editor editor = ApplicationClass.sharedPreferences.edit();

    private GoogleSignInClient googleSignInClient;
    private static final int REQ_SIGN_GOOGLE = 1234;         // 구글 로그인 결과 코드

    private EditText email_login;
    private EditText password_login;
    private Button buttonLogin;
    private TextView textViewRegister;
    private TextView textViewFind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email_login = findViewById(R.id.et_email_login);
        password_login = findViewById(R.id.et_password_login);

        buttonLogin = findViewById(R.id.btn_login);
        textViewRegister = findViewById(R.id.tv_register);
        textViewFind = findViewById(R.id.tv_find);

        buttonLogin.setOnClickListener(this);
        textViewRegister.setOnClickListener(this);
        textViewFind.setOnClickListener(this);

        /*
        // 구글 로그인
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        googleSignInClient.silentSignIn().addOnCompleteListener(this, new OnCompleteListener<GoogleSignInAccount>() {
            @Override
            public void onComplete(@NonNull Task<GoogleSignInAccount> task) {
                handleSignInResult(task);
            }
        });

        ImageView googleLogin = findViewById(R.id.btn_google);
        googleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putInt("flag_login", 2);
                editor.apply();

                Intent intent = googleSignInClient.getSignInIntent();
                startActivityForResult(intent, REQ_SIGN_GOOGLE);
            }
        });

        Function2<OAuthToken, Throwable, Unit> callback = (oAuthToken, throwable) -> {
            if (oAuthToken != null) {
                Log.i(TAG, "카카오계정으로 로그인 성공 : " + oAuthToken.getAccessToken());
            }

            if (throwable != null) {
                Log.e(TAG, "카카오계정으로 로그인 실패 : " + throwable.getLocalizedMessage());
            }

            return null;
        };

        ImageView kakaoLogin = findViewById(R.id.btn_kakao);
        kakaoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putInt("flag_login", 3);
                editor.apply();

                // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
                if (UserApiClient.getInstance().isKakaoTalkLoginAvailable(LoginActivity.this)) {
                    UserApiClient.getInstance().loginWithKakaoTalk(LoginActivity.this, (oAuthToken, error) -> {
                        if (error != null) {
                            Log.e(TAG, "카카오톡으로 로그인 실패 : ", error);

                            // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                            UserApiClient.getInstance().loginWithKakaoAccount(LoginActivity.this, callback);
                        }
                        else if (oAuthToken != null) {
                            Log.i(TAG, "카카오톡으로 로그인 성공 : " + oAuthToken.getAccessToken());
                        }

                        return null;
                    });
                }
                else {
                    UserApiClient.getInstance().loginWithKakaoAccount(LoginActivity.this, callback);
                }
            }
        });
        */
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();

//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
//        updateUI(account);
    }

    private void updateUI(GoogleSignInAccount account) {
        if (account != null) {
            Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {   // 구글 로그인 인증을 요청했을 때 결과값을 되돌려 받는 곳
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_SIGN_GOOGLE) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(@NonNull Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            String idToken = account.getIdToken();

            // TODO : send ID Token to server and validate
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("https://yourbackend.example.com/tokensignin");

            try {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair("idToken", idToken));
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse response = httpClient.execute(httpPost);
                int statusCode = response.getStatusLine().getStatusCode();
                final String responseBody = EntityUtils.toString(response.getEntity());
                Log.i("handleSignInResult", "Signed in as: " + responseBody);
            }
            catch (IOException e) {
                Log.e("handleSignInResult", "Error sending ID token to backend.", e);
            }

            updateUI(account);
        } catch (ApiException e) {
            Log.w("handleSignInResult", "handleSignInResult failed : code = " + e);
            updateUI(null);
        }
    }

    //화면 터치 시 키보드 내려감
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View focusView = getCurrentFocus();
        if (focusView != null) {
            Rect rect = new Rect();
            focusView.getGlobalVisibleRect(rect);
            int x = (int) ev.getX(), y = (int) ev.getY();
            if (!rect.contains(x, y)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (imm != null)
                    imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
                focusView.clearFocus();
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    // 2초 이내에 뒤로가기 버튼을 한번 더 클릭시 앱 종료
    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
        } else {
            moveTaskToBack(true); // 태스크를 백그라운드로 이동
            finishAndRemoveTask(); // 액티비티 종료 + 태스크 리스트에서 지우기
            System.exit(0);
        }
    }

    @Override
    public void onClick(View view) {
        if (view == buttonLogin) {
            editor.putInt("flag_login", 1);
            editor.apply();

            String email_str = email_login.getText().toString();
            String password_str = password_login.getText().toString();

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            AlertDialog dialog = dialogBuilder.create();

            if (email_str.equals("") || password_str.equals("")) {
                dialogBuilder.setTitle("알림");
                dialogBuilder.setMessage("빈 칸을 전부 채워주세요.");
                dialogBuilder.setPositiveButton("확인", null);
                dialogBuilder.show();
                dialog.dismiss();
            }
            else {
                Log.d("LoginActivity", "email : " + email_str);
                Log.d("LoginActivity", "password : " + password_str);

                ApplicationClass.retrofit_manager.login(email_str, password_str, new RetrofitCallback() {
                    @Override
                    public void onError(Throwable t) {
                        Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(String message, String token) {
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        setPreference("email_login", email_str);
                        setPreference("password_login", password_str);
                        setPreference("accessToken", token);

                        // 여기서 해당 이메일에 대한 정보가 있는지 확인하고 정보가 있으면 info 정보 조회한 뒤 MainActivity로 감
                        if (ApplicationClass.sharedPreferences.getInt(email_str, 0) != 0) {
                            ApplicationClass.retrofit_manager.infoCheck(new RetrofitUserInfoCallback() {
                                @Override
                                public void onError(Throwable t) {
                                    Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onSuccess(String message, DataResponseGetUser data) {
                                    Toast.makeText(getApplicationContext(), "개인정보 저장완료", Toast.LENGTH_SHORT).show();

                                    editor.putString("name", data.getName());
                                    editor.putInt("age", data.getAge());
                                    editor.putInt("height", data.getHeight());

                                    if (data.getGender().equals("MALE"))
                                        editor.putString("gender", "남성");
                                    else
                                        editor.putString("gender", "여성");

                                    if (data.getRole().equals("ROLE_CARER"))
                                        editor.putString("role", "보호자");
                                    else
                                        editor.putString("role", "피보호자");

                                    editor.apply();
                                }

                                @Override
                                public void onFailure(int error_code) {
                                    Toast.makeText(getApplicationContext(), "error code : " + error_code, Toast.LENGTH_SHORT).show();
                                }
                            });

                            finish();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                        else {
                            finish();
                            startActivity(new Intent(getApplicationContext(), InfoActivity.class));
                        }
                    }

                    @Override
                    public void onFailure(int error_code) {
                        Toast.makeText(getApplicationContext(), "error code : " + error_code, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

        if (view == textViewRegister) {
            startActivity(new Intent(this, SignupActivity.class));
        }

        if (view == textViewFind) {
            startActivity(new Intent(this, FindActivity.class));
        }
    }

    public void setPreference(String flag, String value) {
        editor.putString(flag, value);
        editor.apply();
    }

    public String getPreference(String flag) {
        return ApplicationClass.sharedPreferences.getString(flag, "");
    }
}