package com.ssu.takecare.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.ssu.takecare.R;
import com.ssu.takecare.runnable.SignupRunnable;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText email_register;
    private EditText password_register;
    private TextView password_register_tv;
    private EditText password_register_same;
    private TextView password_register_same_tv;

    private Button buttonRegister;
    private TextView textViewLogin;

    Handler Handler_Signup;
    ProgressDialog Circle_Dialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        email_register = (EditText) findViewById(R.id.et_email_register);
        password_register = (EditText) findViewById(R.id.et_password_register);
        password_register_tv=(TextView)findViewById(R.id.signup_tv4);
        password_register_same= (EditText) findViewById(R.id.et_password_register2);
        password_register_same_tv=(TextView)findViewById(R.id.signup_tv5);

        buttonRegister = (Button) findViewById(R.id.btn_register);
        textViewLogin = (TextView) findViewById(R.id.tv_login);

        SpannableString content=new SpannableString(textViewLogin.getText().toString());
        content.setSpan(new UnderlineSpan(), 0, content.length(),0);
        textViewLogin.setText(content);

        buttonRegister.setOnClickListener(this);
        textViewLogin.setOnClickListener(this);
        EditText_listener();
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
        if (view == buttonRegister) {
            String email_str = email_register.getText().toString();
            String password_str = password_register.getText().toString();

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
                // 스레드기능
                Circle_Dialog = new ProgressDialog(this);
                Circle_Dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                Circle_Dialog.setMessage("로딩중입니다...");
                Circle_Dialog.show();

                Handler_Signup = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        if (msg.arg1 == 1) {
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            finish();
                        }
                        Circle_Dialog.dismiss();
                    }
                };

                Thread st = new Thread(new SignupRunnable(Handler_Signup,getApplicationContext(),email_str,password_str));
                st.start();
            }
        }
        else if (view == textViewLogin) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
    protected void EditText_listener(){
        password_register.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length()>0&&s.toString().length()<6){
                    password_register_tv.setText("6자리 이상 입력해주세요!");
                    password_register_tv.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.red));
                }else{
                    password_register_tv.setText("");
                }

                if(s.toString().length()>0){
                    String same = password_register_same.getText().toString();
                    if (same.length() > 0) {
                        if (same.equals(s.toString())) {
                            password_register_same_tv.setText("두 비밀번호가 동일해요.");
                            password_register_same_tv.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
                        } else {
                            password_register_same_tv.setText("두 비밀번호가 달라요!");
                            password_register_same_tv.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                        }
                    }
                }else{
                    password_register_same_tv.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        password_register_same.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { //입력하기전에 조치
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { //입력난에 변화가 있을때의 조치
                String str=s.toString();
                if(str.equals(password_register.getText().toString())){
                    password_register_same_tv.setText("두 비밀번호가 동일해요.");
                    password_register_same_tv.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.green));
                }else{
                    password_register_same_tv.setText("두 비밀번호가 달라요!");
                    password_register_same_tv.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.red));
                }
            }

            @Override
            public void afterTextChanged(Editable s) { //입력이 끝났을때의 조치
            }
        });
    }
}