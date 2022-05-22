package com.ssu.takecare.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ssu.takecare.ApplicationClass;
import com.ssu.takecare.R;

public class ProfileActivity extends AppCompatActivity {

    TextView tv_name, tv_gender, tv_age, tv_height, tv_role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        String my_name = ApplicationClass.sharedPreferences.getString("name", "");
        tv_name = findViewById(R.id.profile_name);
        tv_name.setText(my_name);

        String my_gender = ApplicationClass.sharedPreferences.getString("gender", "");
        tv_gender = findViewById(R.id.profile_gender);
        tv_gender.setText(my_gender);

        int my_age = ApplicationClass.sharedPreferences.getInt("age", 0);
        tv_age = findViewById(R.id.profile_age);
        tv_age.setText(String.valueOf(my_age));
        tv_age.append(" 세");

        int my_height = ApplicationClass.sharedPreferences.getInt("height", 0);
        tv_height = findViewById(R.id.profile_height);
        tv_height.setText(String.valueOf(my_height));
        tv_height.append(" cm");

        String my_role = ApplicationClass.sharedPreferences.getString("role", "");
        tv_role = findViewById(R.id.profile_role);
        tv_role.setText(my_role);
    }

    public void back_btn_event(View view) {
        finish();
    }
}