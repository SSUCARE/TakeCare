package com.ssu.takecare.UI;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.ssu.takecare.ApplicationClass;
import com.ssu.takecare.R;
import com.ssu.takecare.Retrofit.Match.DataResponseGetUser;
import com.ssu.takecare.Retrofit.RetrofitCallback;
import com.ssu.takecare.Retrofit.RetrofitCustomCallback.RetrofitUserInfoCallback;

public class InfoActivity extends AppCompatActivity implements View.OnClickListener {

    private long backKeyPressedTime = 0;

    private EditText name_register;
    private EditText age_register;
    private EditText height_register;

    private RadioGroup gender_rg;
    private RadioGroup role_rg;

    private String gender_register = "MALE";
    private String role_register = "ROLE_CARER";

    private Button buttonInfo;

    SharedPreferences.Editor editor = ApplicationClass.sharedPreferences.edit();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        buttonInfo = (Button) findViewById(R.id.btn_info);
        buttonInfo.setOnClickListener(this);

        name_register = (EditText) findViewById(R.id.et_name);
        age_register = (EditText) findViewById(R.id.et_age);
        height_register = (EditText) findViewById(R.id.et_height);

        gender_rg = (RadioGroup) findViewById(R.id.rg_gender);
        gender_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checked) {
                switch (checked) {
                    case R.id.rb_man:
                        gender_register = "MALE";
                        break;
                    case R.id.rb_woman:
                        gender_register = "FEMALE";
                        break;
                }
            }
        });

        role_rg = (RadioGroup) findViewById(R.id.rg_role);
        role_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checked) {
                switch (checked) {
                    case R.id.rb_caring:
                        role_register = "ROLE_CARER";
                        break;
                    case R.id.rb_cared:
                        role_register = "ROLE_CARED";
                        break;
                }
            }
        });
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
    public void onClick(View view) {
        if (view == buttonInfo) {
            String name_str = name_register.getText().toString();
            String age_str = age_register.getText().toString();
            String height_str = height_register.getText().toString();

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            AlertDialog dialog = dialogBuilder.create();
            if (name_str.equals("") || age_str.equals("") || height_str.equals("")) {
                dialogBuilder.setTitle("알림");
                dialogBuilder.setMessage("빈 칸을 전부 채워주세요.");
                dialogBuilder.setPositiveButton("확인", null);
                dialogBuilder.show();
                dialog.dismiss();
            }
            else {
                int age_int = Integer.parseInt(age_str);
                int height_int = Integer.parseInt(height_str);

                ApplicationClass.retrofit_manager.info(name_str, gender_register, age_int, height_int, role_register, new RetrofitCallback() {
                    @Override
                    public void onError(Throwable t) {
                        Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(String message, String token) {
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                        ApplicationClass.retrofit_manager.infoCheck(new RetrofitUserInfoCallback() {
                            @Override
                            public void onError(Throwable t) {
                                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onSuccess(String message, DataResponseGetUser data) {
                                Toast.makeText(getApplicationContext(), "개인정보 저장완료", Toast.LENGTH_SHORT).show();

                                editor.putInt("userId", data.getId());
                                editor.putString("name", data.getName());
                                editor.putInt("age", data.getAge());
                                editor.putInt("height", data.getHeight());

                                if (data.getGender().equals("MALE"))
                                    editor.putString("gender", "남성");
                                else
                                    editor.putString("gender", "여성");

                                // 실제 서버에서 사용
                                if (data.getRole().equals("ROLE_CARER"))
                                    editor.putString("role", "보호자");
                                else
                                    editor.putString("role", "피보호자");

                                // 로컬에서 테스트할때만
//                                if (role_register.equals("ROLE_CARER"))
//                                    editor.putString("role", "보호자");
//                                else
//                                    editor.putString("role", "피보호자");

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

                    @Override
                    public void onFailure(int error_code) {
                        Toast.makeText(getApplicationContext(), "error code : " + error_code, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
}