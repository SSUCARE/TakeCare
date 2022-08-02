package com.ssu.takecare.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.ssu.takecare.AssistClass.Calendar.CalendarDecorator;
import com.ssu.takecare.AssistClass.Calendar.Calendar_Day;
import com.ssu.takecare.AssistClass.Calendar.ReportDecorator;
import com.ssu.takecare.AssistClass.Calendar.SaturdayDecorator;
import com.ssu.takecare.AssistClass.Calendar.SundayDecorator;
import com.ssu.takecare.R;
import com.ssu.takecare.Runnable.GetCalendar_Month_Runnable;
import java.util.HashMap;
import java.util.List;

public class CalendarActivity extends AppCompatActivity {

    HashMap<Integer, String> ID_NAME;

    ConstraintLayout layout;
    TextView cal_name;
    MaterialCalendarView materialCalendarView;

    String userName;
    int userId;

    GetCalendar_Month_Runnable Runnable_gc;
    Handler Handler_Calendar;

    TextView high_pressure_calendar;
    TextView low_pressure_calendar;
    TextView before_sugar_calendar;
    TextView after_sugar_calendar;
    TextView weight_calendar;

    int now_year=-1; int now_month=-1; int now_day=-1; //달력 클릭시, 클릭한 달 저장

    List<Calendar_Day> report_list; // 한달치 건강리포트값 저장
    HashMap<Integer,Calendar_Day>hash_map; //한달치 데이터 저장하고 있다가, 빠른 서칭
    int cal_year;   //현재 달력의 year
    int cal_month;  //현재 달력의 month

    int cal_day;    //맨처음 액티비티 들어올
    int first_entrance=0; //맨처음 액티비티 들어왔을때 바로 값 호출하기 위한 확인 플래그
    CalendarDecorator calendarDecorator; //calendar 커스텀

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_calendar);

        ID_NAME = (HashMap<Integer, String>) getIntent().getSerializableExtra("ID_NAME");

        Init_Data_Setting();
        view_init();
        view_listener();
    }

    //view setting하기
    void view_init(){
        cal_name=(TextView)findViewById(R.id.calendar_name);
        cal_name.setText(userName);

        materialCalendarView = findViewById(R.id.calendar_view);
        materialCalendarView.setSelectedDate(CalendarDay.today());

        layout=(ConstraintLayout)findViewById(R.id.Calendar_transfer_Report);

        high_pressure_calendar=(TextView)findViewById(R.id.high_pressure_calendar) ;
        low_pressure_calendar=(TextView)findViewById(R.id.low_pressure_calendar);
        before_sugar_calendar=(TextView)findViewById(R.id.before_sugar_calendar);
        after_sugar_calendar=(TextView)findViewById(R.id.after_sugar_calendar);
        weight_calendar=(TextView)findViewById(R.id.weight_calendar);
    }

    //view event listener정의
    void view_listener(){
        //달력의 month 클릭시.
        materialCalendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                String cal=date.toString().substring(12,date.toString().length()-1);
                String[]str=cal.split("-");
                cal_year=Integer.parseInt(str[0]);
                cal_month=Integer.parseInt(str[1])+1;
                Log.d("디버그, CalendarActivity","년:"+cal_year+" 월:"+cal_month);
                Get_Data(userId,cal_year,cal_month);
                init_report();
            }
        });

        //달력의 day클릭시. month+1해주기. 예를들어 2022년2월31일 누르면 2022-1-31로 출력됨
        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                String cal=date.toString().substring(12,date.toString().length()-1); //CalendarDay{2022-4-24}일케줌
                String[]str=cal.split("-");
                Log.d("디버그, CalendarActivity",str[0]+"월:"+(Integer.parseInt(str[1])+1)+"일:"+str[2]);

                now_year=Integer.parseInt(str[0]);
                now_month=Integer.parseInt(str[1])+1;
                now_day=Integer.parseInt(str[2]);
                write_report(now_day);
            }
        });

        //건강 리포트 클릭시.
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), ReportActivity.class);
                intent.putExtra("USER_ID", userId);
                intent.putExtra("YEAR", now_year);
                intent.putExtra("MONTH", now_month);
                intent.putExtra("DAY", now_day);
                intent.putExtra("ID_NAME", ID_NAME);
                startActivity(intent);
                finish();
            }
        });
    }

    //UserId, UserName, 오늘날짜등을 얻어서 저장한다.
    void Init_Data_Setting(){
        userId=getIntent().getIntExtra("USER_ID",-1);
        userName=getIntent().getStringExtra("USER_NAME");
        if(userId==-1||userName==null || userName.equals("")){
            Toast.makeText(getApplicationContext(),"일시적 오류 발생",Toast.LENGTH_SHORT).show();
            finish();
        }
        Log.d("디버그,CalendarActivity-> onCreate"," userID:"+userId);

        String s_cal=CalendarDay.today().toString().substring(12,CalendarDay.today().toString().length()-1);
        String[]str=s_cal.split("-");
        cal_year=Integer.parseInt(str[0]);
        cal_month=Integer.parseInt(str[1])+1;
        cal_day=Integer.parseInt(str[2]);
        //맨 처음 캘린더 액티비티 들어갔을때, 해당 달의 데이터 불러오기
        Get_Data(userId,cal_year,cal_month);

        Log.d("디버그, CalendarActivity","오늘날짜:"+CalendarDay.today());
    }


    //한달치 데이터 조회하고, 데이터 변수에 저장하고, 처방까지 하기.
    void Get_Data(int user_id, int year,int month){
        Handler_Calendar=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.arg1 == 1) {
                    report_list=Runnable_gc.getResponse_getcalendar_list();
                    hash_map=new HashMap<>();
                    for(int i=0; i<report_list.size(); i++){
                        hash_map.put(report_list.get(i).getDay(),report_list.get(i));
                    }
                    if(first_entrance==0){ //이렇게 하지않으면, 처음 액티비티 호출시 해당 날짜 리포트 못불러옴
                        write_report(cal_day);
                        first_entrance++;
                    }
                    //성공
                }else if(msg.arg1==0){
                    //Toast.makeText(getApplicationContext(),"failure 발생",Toast.LENGTH_SHORT);
                    finish();
                    //failure 발생한 경우
                }else{
                    //Toast.makeText(getApplicationContext(),"error 발생",Toast.LENGTH_SHORT);
                    finish();
                    //error발생한 경우
                }

                materialCalendarView.addDecorators(new CalendarDecorator(),new SaturdayDecorator(),new SundayDecorator(),new ReportDecorator(hash_map,cal_year,cal_month));
            }
        };
        Runnable_gc=new GetCalendar_Month_Runnable(Handler_Calendar,getApplicationContext(),user_id,year,month);
        Thread st=new Thread(Runnable_gc);
        st.start();
    }

    //밑에 있는 report 작성하기
    public void write_report(int day){
        init_report();
        Calendar_Day data=hash_map.get(day);
        if (data!=null){
            if(data.getSystolic()>0)
                high_pressure_calendar.setText(String.valueOf(data.getSystolic())+" mmHg");

            if(data.getDiastolic()>0)
                low_pressure_calendar.setText(String.valueOf(data.getDiastolic())+" mmHg");

            if(data.getSugarLevels().size()>1){
                before_sugar_calendar.setText(Integer.toString(data.getSugarLevels().get(0))+" mg/dL");
                after_sugar_calendar.setText(Integer.toString(data.getSugarLevels().get(1))+" mg/dL");
            }
            else if(data.getSugarLevels().size()==1){
                before_sugar_calendar.setText(Integer.toString(data.getSugarLevels().get(0))+" mg/dL");
            }

            if(data.getWeight()>0)
                weight_calendar.setText(Integer.toString(data.getWeight())+" kg");

        }
        else{
            init_report();
        }
    }

    //레포트 초기화
    void init_report(){
        high_pressure_calendar.setText("____ mmHg");
        low_pressure_calendar.setText("____ mmHg");
        before_sugar_calendar.setText("____ mg/dL");
        after_sugar_calendar.setText("____ mg/dL");
        weight_calendar.setText("____ kg");
    }
    public void back_btn_event(View view) {
        finish();
    }
    public void memo(){} //무시
}