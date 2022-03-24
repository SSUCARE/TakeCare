package com.designproject.takecare;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private long backKeyPressedTime = 0;

    private Button buttonLogin;
    private TextView textViewRegister;
    private TextView textViewFind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        buttonLogin = (Button) findViewById(R.id.btn_login);
        textViewRegister = (TextView) findViewById(R.id.tv_register);
        textViewFind = (TextView) findViewById(R.id.tv_find);

        buttonLogin.setOnClickListener(this);
        textViewRegister.setOnClickListener(this);
        textViewFind.setOnClickListener(this);

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

        }

        if (view == textViewRegister) {
            startActivity(new Intent(this, RegisterActivity.class));
        }

        if (view == textViewFind) {
            startActivity(new Intent(this, FindActivity.class));
        }
    }
}