package com.ssu.takecare.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ssu.takecare.ApplicationClass;
import com.ssu.takecare.R;
import com.ssu.takecare.Retrofit.RetrofitCallback;

public class PasswordActivity extends AppCompatActivity implements View.OnClickListener {

    SharedPreferences.Editor editor = ApplicationClass.sharedPreferences.edit();

    private String current_email;
    private EditText editTextCurrentPwd;
    private EditText editTextNewPwd;
    private EditText editTextCheckPwd;
    private Button buttonChangePwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        current_email = ApplicationClass.sharedPreferences.getString("email_login", "");
        TextView textViewCurrentEmail = findViewById(R.id.tv_current_email);
        textViewCurrentEmail.setText(current_email);

        editTextCurrentPwd = findViewById(R.id.et_current_pwd);
        editTextNewPwd = findViewById(R.id.et_new_pwd);
        editTextCheckPwd = findViewById(R.id.et_check_pwd);
        buttonChangePwd = findViewById(R.id.btn_change_pwd);

        buttonChangePwd.setOnClickListener(this);
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

    public void back_btn_event(View view) {
        finish();
    }

    @Override
    public void onClick(View view) {
        if (view == buttonChangePwd) {
            String str_current_pwd = editTextCurrentPwd.getText().toString();
            String str_new_pwd = editTextNewPwd.getText().toString();
            String str_check_pwd = editTextCheckPwd.getText().toString();

            if (str_current_pwd.equals(ApplicationClass.sharedPreferences.getString("password_login", ""))) {
                if (str_new_pwd.length() >= 6 && str_new_pwd.length() <= 20) {
                    if (str_new_pwd.equals(str_check_pwd)) {
                        ApplicationClass.retrofit_manager.changePassword(str_current_pwd, str_new_pwd, new RetrofitCallback() {
                            @Override
                            public void onError(Throwable t) {
                            }

                            @Override
                            public void onSuccess(String log, String message) {
                                Log.d("ChangePassword_onSuccess", log + message);
                                Toast.makeText(getApplicationContext(), "비밀번호가 변경되었습니다", Toast.LENGTH_SHORT).show();

                                editor.putString("password_login", editTextNewPwd.getText().toString());
                                editor.apply();

                                finish();
                            }

                            @Override
                            public void onFailure(int error_code) {
                                Log.d("ChangePassword_onFailure", "error code : " + error_code);
                                Toast.makeText(getApplicationContext(), "해당 요청 값이 요구하는 형식과 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "비밀번호는 최소 6자 이상 20자 이하여야 합니다", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(getApplicationContext(), "현재 비밀번호가 올바르지 않습니다", Toast.LENGTH_SHORT).show();
            }
        }
    }
}