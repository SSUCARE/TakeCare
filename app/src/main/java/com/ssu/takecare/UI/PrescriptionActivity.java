package com.ssu.takecare.UI;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.ssu.takecare.ApplicationClass;
import com.ssu.takecare.AssistClass.SugarLevels_pre;
import com.ssu.takecare.R;
import com.ssu.takecare.Retrofit.GetReport.DataGetReport;
import com.ssu.takecare.Runnable.GetReport_Month_Runnable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/*혈압 ->수축기(Systolic)     //수축기 또는 이완기 둘중하나만으로도 판별가능
  정상:  x <=120
  전단계: 120< x <=139
  1단계: 139< x <=159
  2단계: 159< x

  혈당 -> 공복시: 70～110mg/dL, 식후: 140mg/dL 이하가 좋은 것 //모든 수치 통틀어 평균내기
  정상:  x <=139
  당뇨: 139< x <= 154
  2단계: 154< x <=215
  3단계: 215< x


  몸무게 -> 한달치 중 제일 마지막에 기록한걸로 하기기
  BMI 계산법: 몸무게(kg) % (신장(m) x 신장(m))
  저체중: x<=18.5
  정상: 18.5< x <=23
  과체중: 23< x <=25
  비만: 25< x <=30
  고도 비만: 30< x
  */

public class PrescriptionActivity extends AppCompatActivity {

    Date now_date = Calendar.getInstance().getTime();
    int now_year = Integer.parseInt(new SimpleDateFormat("yyyy", Locale.getDefault()).format((now_date)));
    int last_month = Integer.parseInt(new SimpleDateFormat("M", Locale.getDefault()).format((now_date)))-1;
    int userId;
    String userName;

    List<Integer> systolic_list=new ArrayList<>();
    List<Integer> diastolic_list=new ArrayList<>();
    List<SugarLevels_pre> sugarlevels_list=new ArrayList<>();
    List<Integer> weight_list=new ArrayList<>();

    int avg_systolic=0;
    int avg_sugarlevels=0;
    int last_weight;
    float height_m= ApplicationClass.sharedPreferences.getInt("height",0)/(float)100.0; //meter단위로 변경
    float BMI;

    Handler Handler_Report;
    GetReport_Month_Runnable Runnable_gm;

    TextView presciption_pressure_avg_tv;
    TextView presciption_sugar_avg_tv;
    TextView presciption_bmi_tv;
    TextView presciption_tv0;
    TextView presciption_tv1;

    ProgressDialog Circle_Dialog;

    String str1,str2,str3,str4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_presciption);

        view_init();
        Get_UserId_AND_UserName();
        Get_Data();
        Log.d("디버그, PrescriptionActivity","이름:"+userName+"아이디:"+userId);
    }
    //view 초기화
    void view_init(){
        presciption_pressure_avg_tv=(TextView)findViewById(R.id.presciption_pressure_avg);
        presciption_sugar_avg_tv=(TextView)findViewById(R.id.presciption_sugar_avg);
        presciption_bmi_tv=(TextView)findViewById(R.id.presciption_bmi);
        presciption_tv1=(TextView)findViewById(R.id.presciption_tv1);
        presciption_tv0=(TextView)findViewById(R.id.presciption_tv0);
    }
    //UserId얻어오기
    void Get_UserId_AND_UserName(){
        userId=userId=getIntent().getIntExtra("USER_ID",-1);
        if(userId==-1){
            Toast.makeText(getApplicationContext(),"일시적 오류 발생",Toast.LENGTH_SHORT).show();
            finish();
        }

        userName=getIntent().getStringExtra("USER_NAME");
        if(userName==null || userName.equals("")){
            userName="이름 못 받았다.";
        }

    }

    //한달치 데이터 조회하고, 데이터 변수에 저장하고, 처방까지 하기.
    void Get_Data(){
        Handler_Report=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.arg1 == 1) {
                    Log.d("디버그, ShareGraph->handleMessage","실행");
                    Log.d("디버그, ShareGraph->handleMessage","데이터 개수:"+Runnable_gm.getResponse_getreport_list().size());
                    //우선 건강 리포트 하나만 있어도 받을 수 있도록 처방가능하도록 작성하기
                    if(Runnable_gm.getResponse_getreport_list().size()>0){
                        try{
                            Thread.sleep(3500);
                        }catch (Exception e){

                        }
                        Classify_Data_And_Average(Runnable_gm.getResponse_getreport_list());
                        picture_view_avg();
                        write_prescription();
                    }else{
                        try{
                            Thread.sleep(1500);
                        }catch (Exception e){

                        }
                        non_write_prescription();
                    }
                    Circle_Dialog.dismiss();
                }else if(msg.arg1==0){
                    //Toast.makeText(getApplicationContext(),"failure 발생",Toast.LENGTH_SHORT);
                    finish();
                    //failure 발생한 경우
                }else{
                    //Toast.makeText(getApplicationContext(),"error 발생",Toast.LENGTH_SHORT);
                    finish();
                    //error발생한 경우
                }
            }
        };
        Runnable_gm=new GetReport_Month_Runnable(Handler_Report,getApplicationContext(),userId,now_year,last_month+1);
        Thread st=new Thread(Runnable_gm);
        st.start();

        Circle_Dialog = new ProgressDialog(this);
        Circle_Dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        Circle_Dialog.setMessage("건강 레포트 분석중입니다...");
        Circle_Dialog.show();

    }

    //*_list에 데이터 뿌려주기.
    public void Classify_Data_And_Average(List<DataGetReport> data){
        int temp_sys=0; int temp_sugar=0;
        for(int i=0; i<data.size(); i++){
            if(data.get(i).getSystolic()!=0){
                avg_systolic+=data.get(i).getSystolic();
                temp_sys++;
            }
            if(data.get(i).getSugarLevels().size()!=0){
                for(int j=0; j<data.get(i).getSugarLevels().size(); j++){
                    avg_sugarlevels+=data.get(i).getSugarLevels().get(j);
                    temp_sugar++;
                }
            }
            if(data.get(i).getWeight()!=0){
                last_weight=data.get(i).getWeight();
            }

        }
        if(temp_sys!=0)
            avg_systolic/=temp_sys;

        if(avg_sugarlevels!=0)
            avg_sugarlevels/=temp_sugar;
        Log.d("디버그,Prescription","height:"+height_m);

        if(last_weight!=0){
            BMI= (float)last_weight/(height_m*height_m);
            BMI=(float)(Math.round(BMI*10)/10.0);
        }

    }

    //평균값 View 그리기
    void picture_view_avg(){
        if(avg_systolic<=120){
            presciption_pressure_avg_tv.setBackground(getApplicationContext().getDrawable(R.drawable.prescription_normal));
            presciption_pressure_avg_tv.setText("정상");
            str2="운동은 혈압을 낮추기 위한 가장 간단하면서도 좋은 방법이다. 규칙적인 운동은 심장이 효율적으로 혈액을 공급하도록 해 혈압을 낮추는 데 도움을 준다. 검증된 보고에 따르면 하루에 30분만 걸어도 혈압을 낮추는 데 도움을 주며, 운동을 더 많이 할수록 혈압은 더 많이 떨어진다.";
        }else if(avg_systolic>120&&avg_systolic<=139){
            presciption_pressure_avg_tv.setBackground(getApplicationContext().getDrawable(R.drawable.prescription_pressure_0));
            presciption_pressure_avg_tv.setText("고혈압 전단계");
            str2="과도한 나트륨 섭취는 혈관을 막히게 하여 혈압을 상승시키는 주원인이므로 평소 맵고 짜고 자극적인 음식은 피해서 관리해야 합니다.";
        }else if(avg_systolic>139&&avg_systolic<=159){
            presciption_pressure_avg_tv.setBackground(getApplicationContext().getDrawable(R.drawable.prescription_pressure_1));
            presciption_pressure_avg_tv.setText("고혈압 1기");
            str2="달콤하거나 짠 음식만 피하면 된다고 생각하기 쉽지만, 쌀이나 밀가루 같은 등 정제된 탄수화물도 우리 몸에 빠르게 당으로 흡수되면서 악영향을 미칩니다. 혈압약을 먹는 사람이 탄수화물 섭취를 줄이면 혈압이 더 많이 감소한다는 연구도 있습니다.";
        }else{
            presciption_pressure_avg_tv.setBackground(getApplicationContext().getDrawable(R.drawable.prescription_pressure_2));
            presciption_pressure_avg_tv.setText("고혈압 2기");
            str2="고혈압의 합병증 장기적인 고혈압은 뇌, 눈, 심장, 신장의 혈관을 손상시키고 뇌졸중, 협심증, 심근경색증, 실명, 심부전, 신부전등의 위험을 증가시킵니다. 주의해야 합니다.";
        }

        presciption_sugar_avg_tv.setText(Integer.toString(avg_sugarlevels));
        if(avg_sugarlevels<=139){
            presciption_sugar_avg_tv.setBackground(getApplicationContext().getDrawable(R.drawable.prescription_normal));
            presciption_sugar_avg_tv.setText("정상");
            str3="우선 주치의와 상담해 자신에게 맞는 혈당 수치를 알아둬야 한다. 그리고 하루에 최소한 2번은 혈당 수치를 체크하고 기록해야 합니다.";
        }else if(avg_sugarlevels>139 && avg_sugarlevels <=150){
            presciption_pressure_avg_tv.setBackground(getApplicationContext().getDrawable(R.drawable.prescription_diabetes_0));
            presciption_sugar_avg_tv.setText("당뇨전단계");
            str3="코엔자임 섭취를 추천드립니다.코엔자임 Q10은 항산화 기능성을 인정받아 혈관을 공격하는 활성산소 제거게 탁월하며, 연구를 통해 심장 건강 및 혈압 감소에 도움되는 것으로 밝혀져 있습니다.";
        }else if(avg_sugarlevels>154 && avg_sugarlevels <=215){
            presciption_sugar_avg_tv.setText("당뇨관리필요");
            presciption_pressure_avg_tv.setBackground(getApplicationContext().getDrawable(R.drawable.prescription_diabetes_1));
            str3="스트레스는 심장 질환부터 당뇨병까지 여러 가지 질환에 영향을 미칩니다. 스트레스는 혈압과 혈당 수치를 높여 심장 질환, 뇌졸중 등 심각한 질병을 유발하는 인자로 꼽힙니다. 스포츠 관전, 요가, 명상, 책 읽기 등 스트레스 해소를 위한 활동 시간을 가져야 합니다.";

        }else{
            presciption_sugar_avg_tv.setText("당뇨관리필요");
            presciption_pressure_avg_tv.setBackground(getApplicationContext().getDrawable(R.drawable.prescription_diabetes_2));
            str3=" 당뇨병이 생기면 혈당이 올라가고 남아도는 당분이 소변으로 빠져 나가면서 많은 양의 물을 함께 끌고 나갑니다. 이로 인해서 많은 양의 소변을 보게 되고(다뇨), 소변 양이 많아지면 우리 몸은 수분이 부족하다고 느껴서 갈증이 생기고 많은 양의 물을 마시게 됩니다(다음). 또한 음식을 먹어도 몸 안에서 포도당이 에너지원으로 이용되지 못하고 빠져 나가기 때문에 피로감을 느끼고 체중이 줄게 되며, 자꾸 배가 고파서 음식을 찾게 됩니다(다식). 이 밖에도 눈이 침침하거나 피부가 가려운 증상, 팔다리가 저리거나 상처가 쉽게 아물지 않는 등의 증상이 생길 수 있습니다.";
        }

        if(BMI<=18.5){
            presciption_bmi_tv.setBackground(getApplicationContext().getDrawable(R.drawable.prescription_weight_0));
            presciption_bmi_tv.setText("저체중");
            str4="저체중인 사람은 단백질·칼슘·비타민D 등의 영양소 섭취가 제대로 안 될 가능성이 큽니다. 이로 인해 근육세포가 위축되고 근육량이 줄어듭니다.";
        }else if(BMI>18.5 && BMI <= 23){
            presciption_bmi_tv.setBackground(getApplicationContext().getDrawable(R.drawable.prescription_weight_1));
            presciption_bmi_tv.setText("정상");
            str4="꾸준한 운동은 혈액순환으로 인한 질환을 일으키는 나쁜콜레스테롤인 저밀도 지단백 LDL 콜레스테롤의 수치를 감소시킵니다.";
        }else if(BMI>23 && BMI<= 25){
            presciption_bmi_tv.setBackground(getApplicationContext().getDrawable(R.drawable.prescription_weight_2));
            presciption_bmi_tv.setText("과체중");
            str4="지방분해효소 억제제는 음식물로 섭취한 지방이 장에서 흡수되는 것을 억제한다. 글루카곤양펩티드는 소장에서 분비되는 호르몬으로 포만감을 유발해 음식 섭취를 줄일 수 있습니다.";
        }else if(BMI>25 && BMI <=30){
            presciption_bmi_tv.setBackground(getApplicationContext().getDrawable(R.drawable.prescription_weight_3));
            presciption_bmi_tv.setText("비만");
            str4="비만일수록 탈모가 생길 가능성이 높습니다. 특히 복부 비만이라면 탈모 주범으로 지목되는 안드로겐 영향이 증가될 수 있기 때문입니다.";
        }else{
            presciption_bmi_tv.setBackground(getApplicationContext().getDrawable(R.drawable.prescription_weight_4));
            presciption_bmi_tv.setText("고도 비만");
            str4="비만한 경우 관절통, 숨찬 증상 등 겉으로 드러나는 증상 외에도 심혈관질환, 고혈압, 수면 무호흡증 등 합병증의 위험이 커집니다. 비만이 불러온 합병증은 또 다른 질병을 일으켜 건강을 더욱 악화시킬 수 있습니다.";

        }

    }

    //str2, 3, 4 각각은 혈압, 혈당, 몸무게에 대한 한마디씩 하기.
    void write_prescription(){
        str1=last_month+"월 한달 동안 "+userName+"님의 평균 혈압은 "+avg_systolic+"이고, 평균 혈당은 "+avg_sugarlevels+"이고, BMI 수치는 "+BMI+"입니다.";

        String final_str=str1+"\n\n\n"+str2+"\n\n\n"+str3+"\n\n\n"+str4;
        presciption_tv0.setText("최근 한달의 데이터를 분석해 전해드립니다.");
        presciption_tv1.setText(final_str);

    }
    void non_write_prescription(){

        presciption_tv1.setText("처방을 내리기 위한 데이터가 부족합니다."+"\n처방노트를 작성하기 위해서는 12개 이상의 건강 레포트가 필요합니다");
        presciption_tv1.setGravity(Gravity.CENTER);
    }
    public void back_btn_event(View view) {
        finish();
    }
}