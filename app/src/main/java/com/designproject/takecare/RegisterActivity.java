package com.designproject.takecare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonRegister;
    private TextView textViewLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        buttonRegister = (Button) findViewById(R.id.btn_register);
        textViewLogin = (TextView) findViewById(R.id.tv_login);

        buttonRegister.setOnClickListener(this);
        textViewLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view == buttonRegister) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        if (view == textViewLogin) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}