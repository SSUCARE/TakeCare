package com.ssu.takecare.UI;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.ssu.takecare.R;

public class PrescriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_presciption);

    }

    public void back_btn_event(View view) {
        finish();
    }
}