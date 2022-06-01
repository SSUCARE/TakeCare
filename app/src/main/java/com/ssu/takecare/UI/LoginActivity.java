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
import android.widget.TextView;
import android.widget.Toast;
import com.ssu.takecare.ApplicationClass;
import com.ssu.takecare.Retrofit.Match.DataResponseGetUser;
import com.ssu.takecare.Retrofit.RetrofitCallback;
import com.ssu.takecare.R;
import com.ssu.takecare.Retrofit.RetrofitCustomCallback.RetrofitUserInfoCallback;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";
    private long backKeyPressedTime = 0;

    SharedPreferences.Editor editor = ApplicationClass.sharedPreferences.edit();

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
    }

    // 화면 터치 시 키보드 내려감
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

                        // 여기서 해당 이메일에 대한 정보가 있는지 확인하고 정보가 있으면 저장한 뒤 MainActivity로 감
                        ApplicationClass.retrofit_manager.infoCheck(new RetrofitUserInfoCallback() {
                            @Override
                            public void onError(Throwable t) {
                                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onSuccess(String message, DataResponseGetUser data) {
                                if (data.getName() != null || data.getGender() != null || data.getAge() != null || data.getHeight() != null || data.getRole() != null) {
                                    editor.putInt("userId", data.getId());
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

                                    finish();
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));

                                    // 보호자이면 매칭된 사용자 정보 불러오기
//                                    if (data.getRole().equals("ROLE_CARER")) {
//                                        ApplicationClass.retrofit_manager.getCareDBMatchInfo(new RetrofitCareCallback() {
//                                            @Override
//                                            public void onError(Throwable t) {
//                                                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
//                                                Log.d(TAG, "에러 : " + t);
//                                            }
//
//                                            @Override
//                                            public void onSuccess(String message, ResponseCare data) {
//                                                ArrayList<DataResponseCare> list = data.getData();
//
//                                                editor.putInt("match_num", list.size());
//                                                Log.d(TAG, "맵핑된 수 : " + list.size());
//
//                                                JSONArray array_name = new JSONArray();
//                                                JSONArray array_careId = new JSONArray();
//                                                JSONArray array_userId = new JSONArray();
//
//                                                for (int i = 0; i < list.size(); i++) {
//                                                    Log.d(TAG, "i : " + i + ", status : " + list.get(i).getStatus());
//
//                                                    if (list.get(i).getStatus().equals("ACCEPTED")) {
//                                                        Log.d(TAG, "이름 : " + list.get(i).getUserName() + ", careId : " + list.get(i).getId() + ", userId : " + list.get(i).getUserId());
//                                                        array_name.put(list.get(i).getUserName());
//                                                        array_careId.put(list.get(i).getId());
//                                                        array_userId.put(list.get(i).getUserId());
//                                                    }
//
//                                                    if (!list.isEmpty()) {
//                                                        editor.putString("match_name", array_name.toString());
//                                                        editor.putString("match_careId", array_careId.toString());
//                                                        editor.putString("match_userId", array_userId.toString());
//                                                    } else {
//                                                        editor.putString("match_name", null);
//                                                        editor.putString("match_careId", null);
//                                                        editor.putString("match_userId", null);
//                                                    }
//                                                }
//
//                                                editor.apply();
//
//                                                finish();
//                                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                                            }
//
//                                            @Override
//                                            public void onFailure(int error_code) {
//                                                Toast.makeText(getApplicationContext(), "error code : " + error_code, Toast.LENGTH_SHORT).show();
//                                                Log.d(TAG, "실패 : " + error_code);
//                                            }
//                                        });
//                                    }
//                                    else {
//                                        finish();
//                                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                                    }
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
}