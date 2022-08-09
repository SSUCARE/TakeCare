package com.ssu.takecare.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ssu.takecare.ApplicationClass;
import com.ssu.takecare.R;
import com.ssu.takecare.Retrofit.Match.DataResponseGetUser;
import com.ssu.takecare.Retrofit.RetrofitCallback;
import com.ssu.takecare.Retrofit.RetrofitCustomCallback.RetrofitUserInfoCallback;

public class FindActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText email_find;
    private Button buttonFind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

        email_find = findViewById(R.id.et_email_find);

        buttonFind = (Button) findViewById(R.id.btn_find);
        buttonFind.setOnClickListener(this);
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
        if (view == buttonFind) {
            String email_str = email_find.getText().toString();

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            AlertDialog dialog = dialogBuilder.create();

            if (email_str.equals("")) {
                dialogBuilder.setTitle("알림");
                dialogBuilder.setMessage("비밀번호를 찾을 이메일을 입력해주세요.");
                dialogBuilder.setPositiveButton("확인", null);
                dialogBuilder.show();
                dialog.dismiss();
            }
            else {
                ApplicationClass.retrofit_manager.findPassword(email_str, new RetrofitCallback() {
                    @Override
                    public void onError(Throwable t) {
                    }

                    @Override
                    public void onSuccess(String log, String message) {
                        Log.d("FindPassword_onSuccess", log + message);
                        Toast.makeText(getApplicationContext(), "입력하신 이메일로 전송된 인증 메일을 확인해주세요", Toast.LENGTH_SHORT).show();

                        finish();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    }

                    @Override
                    public void onFailure(int error_code) {
                        Log.d("FindPassword_onFailure", "error code : " + error_code);
                        Toast.makeText(getApplicationContext(), "해당 요청 값이 요구하는 형식과 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
}