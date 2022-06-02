package com.ssu.takecare.UI;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.ssu.takecare.AssistClass.SugarLevels_Graph;
import com.ssu.takecare.Fragment.PressureFragment;
import com.ssu.takecare.Fragment.SugarFragment;
import com.ssu.takecare.Fragment.WeightFragment;
import com.ssu.takecare.R;
import com.ssu.takecare.Retrofit.GetReport.DataGetReport;
import com.ssu.takecare.Runnable.GetReport_Month_Runnable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ShareGraph extends AppCompatActivity {

    TextView year_graph, month_graph;
    Button pressure_btn, sugar_btn, weight_btn;

    Date now_date = Calendar.getInstance().getTime();
    String now_year = new SimpleDateFormat("yyyy", Locale.getDefault()).format((now_date));
    String now_month = new SimpleDateFormat("M", Locale.getDefault()).format((now_date));

    int userId; //Intent값으로 넘겨받기!
    int Current_Graph; //1은 혈압, 2는 혈당, 3은 몸무게

    List<Integer> systolic_list;
    List<Integer> systolic_list_date;
    List<Integer> diastolic_list;
    List<Integer> diastolic_list_date;
    List<SugarLevels_Graph> sugarlevels_list;
    List<Integer> sugarlevels_list_date;
    List<Integer> weight_list;
    List<Integer> weight_list_date;

    Handler Handler_Report;
    GetReport_Month_Runnable Runnable_gm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_graph);

        pressure_btn = findViewById(R.id.pressure_btn);
        sugar_btn = findViewById(R.id.sugar_btn);
        weight_btn = findViewById(R.id.weight_btn);

        pressure_btn.setSelected(true);

        year_graph = findViewById(R.id.year_text);
        year_graph.setText(now_year);

        month_graph = findViewById(R.id.month_text);
        month_graph.setText(now_month);

        Current_Graph=1;
        userId=getIntent().getIntExtra("USER_ID",-1);
        Log.d("디버그, ShareGraph","userid:"+userId);

        Handler_Report=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.arg1 == 1) {
                    Log.d("디버그, ShareGraph->handleMessage","실행");
                    Log.d("디버그, ShareGraph->handleMessage","데이터 개수:"+Runnable_gm.getResponse_getreport_list().size());
                    Classify_Data(Runnable_gm.getResponse_getreport_list());
                    if(Current_Graph==1)
                        getSupportFragmentManager().beginTransaction().replace(R.id.graph_fragment, new PressureFragment(systolic_list,systolic_list_date,diastolic_list,diastolic_list_date)).commit();
                    else if(Current_Graph==2)
                        getSupportFragmentManager().beginTransaction().replace(R.id.graph_fragment, new SugarFragment(sugarlevels_list,sugarlevels_list_date)).commit();
                    else
                        getSupportFragmentManager().beginTransaction().replace(R.id.graph_fragment, new WeightFragment(weight_list,weight_list_date)).commit();
                }else if(msg.arg1==0){
                    //failure 발생한 경우
                }else{
                    //error발생한 경우
                }
            }
        };

        Runnable_gm=new GetReport_Month_Runnable(Handler_Report,getApplicationContext(),userId,Integer.parseInt(now_year),Integer.parseInt(now_month));
        Thread st=new Thread(Runnable_gm);
        st.start();
    }

    public void Classify_Data(List<DataGetReport> data){
        systolic_list=new ArrayList<>();systolic_list_date=new ArrayList<>(); //매번 초기화를 해줘야한다.
        diastolic_list=new ArrayList<>();diastolic_list_date=new ArrayList<>();
        sugarlevels_list=new ArrayList<>();sugarlevels_list_date=new ArrayList<>();
        weight_list=new ArrayList<>();weight_list_date=new ArrayList<>();
        int day=0;
        for(int i=0; i<data.size(); i++){
            day=Integer.parseInt(data.get(i).getCreatedAt().substring(8,10)); //날짜 뽑아오기

            if(data.get(i).getSystolic()!=0){
                systolic_list.add(data.get(i).getSystolic());
                systolic_list_date.add(day);
            }

            if(data.get(i).getDiastolic()!=0){
                diastolic_list.add(data.get(i).getDiastolic());
                diastolic_list_date.add(day);
            }

            if(data.get(i).getSugarLevels().size()!=0){
                sugarlevels_list.add(new SugarLevels_Graph(data.get(i).getSugarLevels()));
                sugarlevels_list_date.add(day);
            }

            if(data.get(i).getWeight()!=0){
                weight_list.add(data.get(i).getWeight());
                weight_list_date.add(day);
            }

        }
        Log.d("디버그,sharegraph->claasify","혈압 데이터수:"+systolic_list.size()+","+systolic_list_date.size()+" 혈당 데이터 수:"+sugarlevels_list.size()+","+sugarlevels_list_date.size()+"몸무게 데이터 수:"+weight_list_date.size());
    }

    /* 년월일의 양옆에 있는 버튼 처리 */
    public void month_btn_click(View view) {
        String year, month;
        TextView year_text = findViewById(R.id.year_text);
        TextView month_text = findViewById(R.id.month_text);

        switch (view.getId()) {
            case R.id.next_month_btn:
                if (Integer.parseInt(month_text.getText().toString()) == 12) {
                    year = "" + (Integer.parseInt(year_text.getText().toString()) + 1);
                    month = "1";
                }
                else {
                    year = year_text.getText().toString();
                    month = "" + (Integer.parseInt(month_text.getText().toString()) + 1);
                }
                year_text.setText(year);
                month_text.setText(month);

                Runnable_gm=new GetReport_Month_Runnable(Handler_Report,getApplicationContext(),userId,Integer.parseInt(year),Integer.parseInt(month));
                Thread st1=new Thread(Runnable_gm);
                st1.start();
                break;
            case R.id.previous_month_btn:
                if (Integer.parseInt(month_text.getText().toString()) == 1) {
                    year = "" + (Integer.parseInt(year_text.getText().toString()) + -1);
                    month = "12";
                }
                else {
                    year = year_text.getText().toString();
                    month = "" + (Integer.parseInt(month_text.getText().toString()) - 1);
                }
                year_text.setText(year);
                month_text.setText(month);

                Runnable_gm=new GetReport_Month_Runnable(Handler_Report,getApplicationContext(),userId,Integer.parseInt(year),Integer.parseInt(month));
                Thread st2=new Thread(Runnable_gm);
                st2.start();
                break;
        }
    }

    /*혈압, 혈당, 체중 버튼 이벤트 처리*/
    public void graph_btn_click(View view) {
        switch (view.getId()){
            case R.id.pressure_btn:
                Current_Graph=1;
                pressure_btn.setSelected(true);
                sugar_btn.setSelected(false);
                weight_btn.setSelected(false);
                getSupportFragmentManager().beginTransaction().replace(R.id.graph_fragment, new PressureFragment(systolic_list,systolic_list_date,diastolic_list,diastolic_list_date)).commit();
                break;
            case R.id.sugar_btn:
                Current_Graph=2;
                pressure_btn.setSelected(false);
                sugar_btn.setSelected(true);
                weight_btn.setSelected(false);
                getSupportFragmentManager().beginTransaction().replace(R.id.graph_fragment, new SugarFragment(sugarlevels_list,sugarlevels_list_date)).commit();
                break;
            case R.id.weight_btn:
                Current_Graph=3;
                pressure_btn.setSelected(false);
                sugar_btn.setSelected(false);
                weight_btn.setSelected(true);
                getSupportFragmentManager().beginTransaction().replace(R.id.graph_fragment, new WeightFragment(weight_list,weight_list_date)).commit();
                break;
        }
    }

    public void close_btn_event(View view) {
        finish();
    }
}
