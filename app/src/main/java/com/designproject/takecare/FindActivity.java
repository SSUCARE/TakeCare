package com.designproject.takecare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FindActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonFind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

        buttonFind = (Button) findViewById(R.id.btn_find);

        buttonFind.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == buttonFind) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}