package com.designproject.takecare.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.designproject.takecare.Fragment.HomeFragment;
import com.designproject.takecare.Fragment.MyPageFragment;
import com.designproject.takecare.Fragment.ShareFragment;
import com.designproject.takecare.KakaoLogin.SessionCallback;
import com.designproject.takecare.R;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

public class MainActivity extends AppCompatActivity {

    private long backKeyPressedTime = 0;

    private SessionCallback sessionCallback = new SessionCallback();
    Session session;

    private int flag_login;
    private ImageButton tab_btn1, tab_btn2, tab_btn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment, new HomeFragment()).commit();

        session = Session.getCurrentSession();
        session.addCallback(sessionCallback);
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

    void init() {
        tab_btn1 = findViewById(R.id.tab_btn1);
        tab_btn2 = findViewById(R.id.tab_btn2);
        tab_btn3 = findViewById(R.id.tab_btn3);
    }

    public void logout(View view) {
        SharedPreferences sharedPreferences= getSharedPreferences("FLAG", MODE_PRIVATE);
        flag_login = sharedPreferences.getInt("flag",0);
        Log.d("logout", "flag : " + flag_login);
        switch (flag_login) {
            case 0 :
                finish();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                break;
            case 1 :
                break;
            case 2:
                Log.d("Kakao Logout", "onClick");
                UserManagement.getInstance()
                        .requestLogout(new LogoutResponseCallback() {
                            @Override
                            public void onSessionClosed(ErrorResult errorResult) {
                                super.onSessionClosed(errorResult);
                                Log.d("Kakao Logout", "onSessionClosed : " + errorResult.getErrorMessage());

                            }

                            @Override
                            public void onCompleteLogout() {
                                if (sessionCallback != null) {
                                    Session.getCurrentSession().removeCallback(sessionCallback);
                                }

                                Log.d("Kakao Logout", "onCompleteLogout : logout");
                            }
                        });
                break;
            default:
                break;
        }
    }

    public void click_event(View view) {
        switch (view.getId()) {
            case R.id.tab_btn1:
                getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment, new HomeFragment()).commit();
                tab_btn1.setImageResource(R.drawable.tab_btn1_select);
                tab_btn2.setImageResource(R.drawable.tab_btn2);
                tab_btn3.setImageResource(R.drawable.tab_btn3);
                break;
            case R.id.tab_btn2:
                getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment, new ShareFragment()).commit();
                tab_btn1.setImageResource(R.drawable.tab_btn1);
                tab_btn2.setImageResource(R.drawable.tab_btn2_select);
                tab_btn3.setImageResource(R.drawable.tab_btn3);
                break;
            case R.id.tab_btn3:
                getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment, new MyPageFragment()).commit();
                tab_btn1.setImageResource(R.drawable.tab_btn1);
                tab_btn2.setImageResource(R.drawable.tab_btn2);
                tab_btn3.setImageResource(R.drawable.tab_btn3_select);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 세션 콜백 삭제
        Session.getCurrentSession().removeCallback(sessionCallback);
    }
}