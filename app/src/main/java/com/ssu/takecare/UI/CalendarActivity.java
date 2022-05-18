package com.ssu.takecare.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ssu.takecare.R;

public class CalendarActivity extends AppCompatActivity {
    ConstraintLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        layout=(ConstraintLayout)findViewById(R.id.Calendar_transfer_Report);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),ReportActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void back_btn_event(View view) {
        finish();
    }
}