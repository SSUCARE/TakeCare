package com.designproject.takecare.UI;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import com.designproject.takecare.KakaoLogin.SessionCallback;
import com.designproject.takecare.R;
import com.designproject.takecare.Retrofit.RetrofitManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.kakao.auth.AuthType;
import com.kakao.auth.Session;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    private long backKeyPressedTime = 0;

    private SessionCallback sessionCallback = new SessionCallback();
    Session session;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private FirebaseAuth firebaseAuth;              // 파이어베이스 인증 객체
    private GoogleApiClient googleApiClient;        // 구글 API 클라이언트 객체
    private static final int REQ_SIGN_GOOGLE = 100; // 구글 로그인 결과 코드

    private EditText email_login;
    private EditText password_login;
    private Button buttonLogin;
    private ImageView googleLogin;
    private ImageView kakaoLogin;
    private TextView textViewRegister;
    private TextView textViewFind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences= getSharedPreferences("FLAG", MODE_PRIVATE);
        editor= sharedPreferences.edit();

        email_login = (EditText) findViewById(R.id.et_email_login);
        password_login = (EditText) findViewById(R.id.et_password_login);

        buttonLogin = (Button) findViewById(R.id.btn_login);
        textViewRegister = (TextView) findViewById(R.id.tv_register);
        textViewFind = (TextView) findViewById(R.id.tv_find);

        buttonLogin.setOnClickListener(this);
        textViewRegister.setOnClickListener(this);
        textViewFind.setOnClickListener(this);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();

        firebaseAuth = FirebaseAuth.getInstance();  // 파이어베이스 인증 객체 초기화

        googleLogin = (ImageView) findViewById(R.id.btn_google);
        googleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putInt("flag", 1);
                editor.apply();

                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, REQ_SIGN_GOOGLE);
            }
        });

        session = Session.getCurrentSession();
        session.addCallback(sessionCallback);

        kakaoLogin = (ImageView) findViewById(R.id.btn_kakao);
        kakaoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putInt("flag", 2);
                editor.apply();

                if (Session.getCurrentSession().checkAndImplicitOpen()) {
                    Log.d("Kakao Login", "onClick: 로그인 세션 살아있음");
                    // 카카오 로그인 시도 (창이 안 뜸)
                    sessionCallback.requestMe();
                }
                else {
                    Log.d("Kakao Login", "onClick: 로그인 세션 끝남");
                    // 카카오 로그인 시도 (창이 뜸)
                    session.open(AuthType.KAKAO_LOGIN_ALL, LoginActivity.this);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {   // 구글 로그인 인증을 요청했을 때 결과값을 되돌려 받는 곳
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_SIGN_GOOGLE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                resultLogin(account);   // 로그인 결과값 출력 수행
            }
        }

        // 카카오톡 & 스토리 간편로그인 실행 결과를 받아서 SDK로 전달
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }
    }

    private void resultLogin(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {  // 로그인 성공
                            Toast.makeText(LoginActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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

    @Override
    public void onBackPressed() {
        // 2초 이내에 뒤로가기 버튼을 한번 더 클릭시 앱 종료
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
        else {
            moveTaskToBack(true); // 태스크를 백그라운드로 이동
            finishAndRemoveTask(); // 액티비티 종료 + 태스크 리스트에서 지우기
            System.exit(0);
        }
    }

    @Override
    public void onClick(View view) {
        if (view == buttonLogin) {
            editor.putInt("flag", 0);
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

                RetrofitManager instance = new RetrofitManager();
                instance.loginReq(email_str, password_str);
                instance.loginRes(email_str, password_str);

                finish();
                startActivity(new Intent(this, InfoActivity.class));
            }
        }

        if (view == textViewRegister) {
            startActivity(new Intent(this, RegisterActivity.class));
        }

        if (view == textViewFind) {
            startActivity(new Intent(this, FindActivity.class));
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 세션 콜백 삭제
        Session.getCurrentSession().removeCallback(sessionCallback);
    }
}