package com.ssu.takecare.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.kakao.sdk.user.UserApiClient;
import com.ssu.takecare.Dialog.PressureDialog;
import com.ssu.takecare.Dialog.SugarDialog;
import com.ssu.takecare.Dialog.WeightDialog;
import com.ssu.takecare.Fragment.HomeFragment;
import com.ssu.takecare.Fragment.MyPageFragment;
import com.ssu.takecare.Fragment.ShareFragment;
import com.ssu.takecare.R;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private long backKeyPressedTime = 0;

    private ImageButton tab_btn1, tab_btn2, tab_btn3;
    private TextView hp_input, lp_input;
    private TextView bs_input, as_input;
    private TextView w_input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment, new HomeFragment()).commit();
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

    public void inputPressure(View view) {
        PressureDialog pDialog = new PressureDialog(this);
        pDialog.setPressureDialogListener(new PressureDialog.PressureDialogListener() {
            @Override
            public void okClicked(String high_pressure, String low_pressure) {
                hp_input = findViewById(R.id.high_pressure);
                lp_input = findViewById(R.id.low_pressure);

                if (!high_pressure.equals(""))
                    hp_input.setText(high_pressure);

                if (!low_pressure.equals(""))
                    lp_input.setText(low_pressure);
            }
        });

        pDialog.show();
    }

    public void inputSugar(View view) {
        SugarDialog sDialog = new SugarDialog(this);
        sDialog.setSugarDialogListener(new SugarDialog.SugarDialogListener() {
            @Override
            public void okClicked(String before_sugar, String after_sugar, int input_hour, int input_minute) {
                bs_input = findViewById(R.id.before_sugar);
                as_input = findViewById(R.id.after_sugar);

                if (!before_sugar.equals(""))
                    bs_input.setText(before_sugar);

                if (!after_sugar.equals(""))
                    as_input.setText(after_sugar);

                Log.d(TAG, "input_hour : " + input_hour);
                Log.d(TAG, "input_minute : " + input_minute);
            }
        });

        sDialog.show();
    }

    public void inputWeight(View view) {
        WeightDialog wDialog = new WeightDialog(this);
        wDialog.setWeightDialogListener(new WeightDialog.WeightDialogListener() {
            @Override
            public void okClicked(String weight) {
                w_input = findViewById(R.id.weight);

                if (!weight.equals(""))
                    w_input.setText(weight);
            }
        });

        wDialog.show();
    }

    public void logout(View view) {
        SharedPreferences sharedPreferences= getSharedPreferences("TakeCare", MODE_PRIVATE);
        int flag_login = sharedPreferences.getInt("flag_login",0);
        switch (flag_login) {
            case 0 :
                finish();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                break;
            case 1 :
                GoogleSignInClient gsc = GoogleSignIn.getClient(getApplicationContext(), GoogleSignInOptions.DEFAULT_SIGN_IN);

                gsc.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG,"SignOut : Google Logout");
                    }
                });

                gsc.revokeAccess().addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG,"RevokeAccess : Google Logout");
                    }
                });

                finish();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                break;
            case 2:
                UserApiClient.getInstance().logout(error -> {
                    if (error != null) {
                        Log.e(TAG, "카카로 로그아웃 실패, SDK에서 토큰 삭제됨", error);
                    }
                    else {
                        Log.i(TAG, "카카오 로그아웃 성공, SDK에서 토큰 삭제됨");
                    }

                    return null;
                });

                // 연결 끊기
                UserApiClient.getInstance().unlink(error -> {
                    if (error != null) {
                        Log.e(TAG, "카카오 연결 끊기 실패", error);
                    }
                    else {
                        Log.i(TAG, "카카오 연결 끊기 성공. SDK에서 토큰 삭제 됨");
                    }

                    return null;
                });

                finish();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
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
    }
}