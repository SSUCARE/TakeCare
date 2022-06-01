package com.ssu.takecare.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.ssu.takecare.R;

// ShareFragment, RoleCaredFragment 로부터 USER_ID를 받아와야만 한다.
public class CalendarActivity extends AppCompatActivity {

    ConstraintLayout layout;
    String userName;
    int userId;
    int year=-1; int month=-1; int day=-1;
    TextView cal_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_calendar);

        MaterialCalendarView materialCalendarView = findViewById(R.id.calendar_view);
        materialCalendarView.setSelectedDate(CalendarDay.today());

        userId=getIntent().getIntExtra("USER_ID",-1);
        userName=getIntent().getStringExtra("USER_NAME");
        Log.d("디버그,CalendarActivity-> onCreate"," userID:"+userId);

        cal_name=(TextView)findViewById(R.id.calendar_name);
        cal_name.setText(userName);

        // month+1해주기. 예를들어 2022년2월31일 누르면 2022-1-31로 출력됨
        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                String cal=date.toString().substring(12,date.toString().length()-1); //CalendarDay{2022-4-24}일케줌
                String[]str=cal.split("-");
                Log.d("디버그, CalendarActivity",str[0]+"월:"+Integer.parseInt(str[1])+1+"일:"+str[2]);

                year=Integer.parseInt(str[0]);
                month=Integer.parseInt(str[1])+1;
                day=Integer.parseInt(str[2]);
            }
        });

        layout=(ConstraintLayout)findViewById(R.id.Calendar_transfer_Report);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), ReportActivity.class);
                intent.putExtra("USER_ID", userId);
                intent.putExtra("YEAR", year);
                intent.putExtra("MONTH", month);
                intent.putExtra("DAY", day);
                startActivity(intent);
                finish();
            }
        });
    }

    public void back_btn_event(View view) {
        finish();
    }
}