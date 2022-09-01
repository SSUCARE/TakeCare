package com.ssu.takecare.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.ssu.takecare.ApplicationClass;
import com.ssu.takecare.R;
import com.ssu.takecare.retrofit.report.DataGetReport;
import com.ssu.takecare.runnable.GetReport_Month_Runnable;
import java.text.SimpleDateFormat;
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
/*체중과 관련해서는 아직 만들지 않았음.*/
public class PrescriptionActivity extends AppCompatActivity {

    Date now_date = Calendar.getInstance().getTime();
    int now_year = Integer.parseInt(new SimpleDateFormat("yyyy", Locale.getDefault()).format((now_date)));
    int last_month = Integer.parseInt(new SimpleDateFormat("M", Locale.getDefault()).format((now_date)))-1;
    int userId;
    String userName;

    int avg_systolic=0;
    int avg_sugarlevels=0;
    int last_weight;
    float height_m= ApplicationClass.sharedPreferences.getInt("height",0)/(float)100.0; //meter단위로 변경
    float BMI;

    Handler Handler_Report;
    GetReport_Month_Runnable Runnable_gm;

    TextView prescription_pressure_avg_tv,prescription_sugar_avg_tv, prescription_bmi_tv;
    TextView prescription_month;
    TextView prescription_avg_tv;

    TextView prescription_subheading_tv1,prescription_subheading_tv2,prescription_subheading_tv3,prescription_subheading_tv4,prescription_subheading_tv5;
    TextView prescription_describe_tv1,prescription_describe_tv2,prescription_describe_tv3,prescription_describe_tv4,prescription_describe_tv5;
    ImageView prescription_img1,prescription_img2;
    ProgressDialog Circle_Dialog;

    int pressure_flag, sugar_flag, weight_flag; //이 중 가장 큰 값을 처방 노트에서 진단하기.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_presciption);

        view_init();
        Get_UserId_AND_UserName();
        Get_Data();
    }
    //view 초기화
    void view_init(){
        prescription_pressure_avg_tv=(TextView)findViewById(R.id.presciption_pressure_avg);
        prescription_sugar_avg_tv=(TextView)findViewById(R.id.presciption_sugar_avg);
        prescription_bmi_tv=(TextView)findViewById(R.id.presciption_bmi);
        prescription_avg_tv=(TextView) findViewById(R.id.prescription_avg_tv);
        prescription_month=(TextView)findViewById(R.id.prescription_month);
        prescription_subheading_tv1=(TextView)findViewById(R.id.prescription_subheading_tv1);
        prescription_subheading_tv2=(TextView)findViewById(R.id.prescription_subheading_tv2);
        prescription_subheading_tv3=(TextView)findViewById(R.id.prescription_subheading_tv3);
        prescription_subheading_tv4=(TextView)findViewById(R.id.prescription_subheading_tv4);
        prescription_subheading_tv5=(TextView)findViewById(R.id.prescription_subheading_tv5);
        prescription_describe_tv1=(TextView)findViewById(R.id.prescription_describe_tv1);
        prescription_describe_tv2=(TextView)findViewById(R.id.prescription_describe_tv2);
        prescription_describe_tv3=(TextView)findViewById(R.id.prescription_describe_tv3);
        prescription_describe_tv4=(TextView)findViewById(R.id.prescription_describe_tv4);
        prescription_describe_tv5=(TextView)findViewById(R.id.prescription_describe_tv5);
        prescription_img1=(ImageView)findViewById(R.id.prescription_img1);
        prescription_img2=(ImageView)findViewById(R.id.prescription_img2);
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
            Toast.makeText(getApplicationContext(),"일시적 오류 발생",Toast.LENGTH_SHORT).show();
            finish();
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

        Runnable_gm = new GetReport_Month_Runnable(Handler_Report, getApplicationContext(), userId, now_year, last_month);
        Thread st = new Thread(Runnable_gm);
        st.start();

        Circle_Dialog = new ProgressDialog(this);
        Circle_Dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        Circle_Dialog.setMessage("건강 레포트 분석중입니다...");
        Circle_Dialog.show();

    }

    //혈압, 혈당, 몸무게 평균값 구하기.
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

        if(last_weight!=0){
            BMI= (float)last_weight/(height_m*height_m);
            BMI=(float)(Math.round(BMI*10)/10.0);
        }

    }

    //평균값 View 그리기
    void picture_view_avg(){
        prescription_month.setText("최근 한달의 건강레포트 분석결과");
        if(avg_systolic<=120){
            prescription_pressure_avg_tv.setBackground(getApplicationContext().getDrawable(R.drawable.prescription_normal));
            prescription_pressure_avg_tv.setText("혈압 정상");
            pressure_flag=0;
        }else if(avg_systolic>120&&avg_systolic<=139){
            prescription_pressure_avg_tv.setBackground(getApplicationContext().getDrawable(R.drawable.prescription_pressure_0));
            prescription_pressure_avg_tv.setText("고혈압 전단계");
            pressure_flag=1;
        }else if(avg_systolic>139&&avg_systolic<=159){
            prescription_pressure_avg_tv.setBackground(getApplicationContext().getDrawable(R.drawable.prescription_pressure_1));
            prescription_pressure_avg_tv.setText("고혈압 1기");
            pressure_flag=2;
        }else{
            prescription_pressure_avg_tv.setBackground(getApplicationContext().getDrawable(R.drawable.prescription_pressure_2));
            prescription_pressure_avg_tv.setText("고혈압 2기");
            pressure_flag=3;
        }

        prescription_sugar_avg_tv.setText(Integer.toString(avg_sugarlevels));
        if(avg_sugarlevels<=139){
            prescription_sugar_avg_tv.setBackground(getApplicationContext().getDrawable(R.drawable.prescription_normal));
            prescription_sugar_avg_tv.setText("혈당 정상");
            sugar_flag=0;
        }else if(avg_sugarlevels>139 && avg_sugarlevels <=150){
            prescription_pressure_avg_tv.setBackground(getApplicationContext().getDrawable(R.drawable.prescription_diabetes_0));
            prescription_sugar_avg_tv.setText("당뇨전단계");
            sugar_flag=1;
        }else if(avg_sugarlevels>154 && avg_sugarlevels <=215){
            prescription_sugar_avg_tv.setText("당뇨관리필요");
            prescription_pressure_avg_tv.setBackground(getApplicationContext().getDrawable(R.drawable.prescription_diabetes_1));
            sugar_flag=2;
        }else{
            prescription_sugar_avg_tv.setText("당뇨관리필요");
            prescription_pressure_avg_tv.setBackground(getApplicationContext().getDrawable(R.drawable.prescription_diabetes_2));
            sugar_flag=3;
        }

        if(BMI<=18.5){
            prescription_bmi_tv.setBackground(getApplicationContext().getDrawable(R.drawable.prescription_weight_0));
            prescription_bmi_tv.setText("저체중");
            weight_flag=-1;
        }else if(BMI>18.5 && BMI <= 23){
            prescription_bmi_tv.setBackground(getApplicationContext().getDrawable(R.drawable.prescription_weight_1));
            prescription_bmi_tv.setText("체중 정상");
            weight_flag=0;
         }else if(BMI>23 && BMI<= 25){
            prescription_bmi_tv.setBackground(getApplicationContext().getDrawable(R.drawable.prescription_weight_2));
            prescription_bmi_tv.setText("과체중");
            weight_flag=1;
         }else if(BMI>25 && BMI <=30){
            prescription_bmi_tv.setBackground(getApplicationContext().getDrawable(R.drawable.prescription_weight_3));
            prescription_bmi_tv.setText("비만");
            weight_flag=2;
        }else{
            prescription_bmi_tv.setBackground(getApplicationContext().getDrawable(R.drawable.prescription_weight_4));
            prescription_bmi_tv.setText("고도 비만");
            weight_flag=3;
        }
    }

    //str2, 3, 4 각각은 혈압, 혈당, 몸무게에 대한 한마디씩 하기.
    void write_prescription(){
        String final_str=last_month+"월 한달 동안 "+userName+"님은"+" 총 "+Runnable_gm.getResponse_getreport_list().size()+"개의 건강레포트를 작성하였습니다. "+"평균 혈압은 "+avg_systolic+"이고, 평균 혈당은 "+avg_sugarlevels+"이고, BMI 수치는 "+BMI+"입니다.";

        if(pressure_flag==1&&sugar_flag==1&&weight_flag==1){//모두 정상인 상황
            final_str="모두 정상적인 수치로 혈압, 혈당, 체중의 정상입니다.";
            prescription_avg_tv.setText(avg_tv_spannable_normal(final_str));
        }else if(pressure_flag>=sugar_flag&&pressure_flag>=weight_flag){//혈압이 가장 문제가 되는 상황
            final_str+=" 그중에서 가장 관리가 필요한 것은 혈압입니다. 아래는 이와 관련된 내용들입니다.";
            pressure_prescription();
            prescription_avg_tv.setText(avg_tv_spannable_non_normal(final_str));
        }else if(sugar_flag>=pressure_flag&&sugar_flag>=weight_flag){ //혈당이 가장 문제가 되는 상황
            final_str+=" 그중에서 관리가 필요한 것은 혈당입니다. 아래는 이와 관련된 내용들입니다.";
            sugar_prescription();
            prescription_avg_tv.setText(avg_tv_spannable_non_normal(final_str));
        }else if(pressure_flag==1&&sugar_flag==1&&weight_flag==-1){ //혈압, 혈당 정상인데 몸무게가 저체중인 상황
            final_str+=" 그중에서 관리가 필요한 것은 체중입니다. 아래는 이와 관련된 내용들입니다.";
            prescription_avg_tv.setText(avg_tv_spannable_non_normal(final_str));
        }else{ //몸무게가 가장 문제가 되는 상황
            final_str+=" 그중에서 관리가 필요한 것은 체중입니다. 아래는 이와 관련된 내용들입니다.";
            prescription_avg_tv.setText(avg_tv_spannable_non_normal(final_str));
        }


    }
    void non_write_prescription(){
        prescription_avg_tv.setText("처방을 내리기 위한 데이터가 부족합니다."+"\n처방노트를 작성하기 위해서는 12개 이상의 건강 레포트가 필요합니다");
        prescription_avg_tv.setGravity(Gravity.CENTER);
    }
    public void back_btn_event(View view) {
        finish();
    }
    private void sugar_prescription(){
        prescription_subheading_tv1.setText("피드백");
        // prescription_avg_tv가 처리.
        prescription_subheading_tv2.setText("증상");
        prescription_describe_tv1.setText("당뇨병에 걸리면 소변으로 포도당이 빠져나가는데, 이때 수분도 같이 빠져나가서 소변량이 늘어나게 됩니다. 필요이상으로 많은 수분이 빠져나가 심한 갈증을 느끼게 됩니다. 또 수분과 함께 영양분도 같이 빠져나가서 피로감을 잘느끼며 체중이 감소하는 경향이 있습니다.");
        prescription_describe_tv2.setText("잦은 배뇨, 갈증, 공복, 피곤함(무기력증), 흐려지는 시야, 상처회복의 더딤, 저림, 따끔거림등이 당뇨의 증상들입니다. 당뇨는 망막, 신장, 신경, 말초신경, 발등에서 다양한 합병증을 유발할 수 있기에 위와 같은 증상이 있다면, 혈당관리가 반드시 필요합니다.");
        prescription_subheading_tv3.setText("당뇨병 완치여부");
        prescription_describe_tv3.setText("지난 30년 동안 많은 당뇨병약이 개발됐습니다. 가장 최근에 개발된 약들은 식욕을 떨어뜨려 음식물 섭취를 줄여 주고, 당 소화 흡수를 억제하고, 당의 재흡수를 막으며, 인슐린 분비를 증가시켜 혈당을 낮추는데 목표를 두고 있습니다. 그러나 혈당을 낮추는 것은 증상에 대한 일시적인 처방일 뿐, 당뇨병의 진행을 막을 수 없어 근본적으로 치료되지 않습니다.당뇨병의 큰 원인은 바로 췌장에서 인슐린을 만들어내는 베타세포의 사멸 또는 기능 상실입니다. 베타세포가 퇴화해 사멸하면 재생시킬 수 없어 당뇨병은 완치가 힘듭니다. 그래서 당뇨 환자들은 약으로 치료를 받으면서 식이 요법과 운동 등을 통해 당뇨병 진행을 최대한 늦출 수는 있지만 한번 진행된 당뇨병을 완치시킬 수는 없습니다.그렇기에 당뇨에 걸리기전에 미리 관리를 해야합니다.");
        prescription_subheading_tv4.setText("혈당 낮추는 방법");
        prescription_describe_tv4.setText("◇허벅지 근육 단련하기+\n+근육은 우리 몸의 신체 장기·조직 중 포도당을 가장 많이 소모하는데,허벅지 근육이 섭취한 포도당의 70%를 소모하는 것으로 알려진다.+\n+◇아침 식사를 거르지 말라+\n+아침 식사는 누구한테나 중요하지만 특히 당뇨병 환자에게 더 그렇다. 너무 오래 식사를 하지 않으면 저혈당증이 나타날 수 있다. 탄수화물보다 단백질이 많은 음식이 혈당을 낮추는 데 좋다. 아침 식사는 체중을 줄이는 효과도 있다.");
        prescription_subheading_tv5.setText("식단추천");
        prescription_describe_tv5.setText("당뇨병에 좋은 음식은 당연히 당지수가 낮은 음식(혈당을 천천히 올리는 음식)들 이다. 해조류, 녹조류, 두유, 아몬드, 땅콩, 콩류, 저지방우유, 달걀, 치즈, 딸기, 아보카도, 토마토,오이, 시금치등 단맛 적은 과일과 채소 등이다.");

        prescription_img1.setImageResource(R.drawable.prescription_sugar_img);
        prescription_img2.setImageResource(R.drawable.prescription_sugar_diet);
    }
    private void pressure_prescription(){
        prescription_subheading_tv1.setText("피드백");
        // prescription_avg_tv가 처리.
        prescription_subheading_tv2.setText("증상");
        prescription_describe_tv1.setText("어지럼증, 두통, 코피, 어깨결림, 가슴통증, 피로, 성 기능장애등 다양한 증상을 호소하는 경우가 있으나, 이는 일반적인 고혈압에서 볼 수 있는 전형적인 증상은 아닙니다. 무증상인 경우도 많으니 당장에는 증상이 없다고 가볍게 여기시면 안됩니다.");
        prescription_describe_tv2.setText("혈압이 높은 상태가 장기적으로 지속되면 신체 각 부위에 다양한 합병증이 발생할 수 있으며, 이들 중 상당수는 심장발작이나 뇌졸중처럼 치명적인 문제를 발생시킵니다.");
        prescription_subheading_tv3.setText("주의사항");
        prescription_describe_tv3.setText("과도한 알코올 섭취는 고혈압 및 뇌졸중의 중요한 위험 인자이며, 약물 요법의 효과를 약화시키므로 피해야 합니다. 또한 칼슘 섭취량을 증가시키고, 섬유소와 불포화지방산의 섭취 비율을 증가시키며, 카페인을 적절히 제한하도록 권장합니다.");
        prescription_subheading_tv4.setText("혈압 낮추는 방법");
        prescription_describe_tv4.setText("◇종아리 주무르기+\n+종아리 마사지는 자기 전, 아래에서 위로 약간 아플 정도로만 5~10회 정도 누르면 된다.+\n+ ◇짧은 낮잠 자기+\n+한 시간 이내 낮잠도 혈압을 낮추는 효과가 있다.");
        prescription_subheading_tv5.setText("식단추천");
        prescription_describe_tv5.setText("혈압이 높은 사람에게는 해산물 주임의 지중해식 식단이 좋다. 저포화지방, 고식이섬유 위주의 식단인 과일, 야채, 곡물, 견과류들을 말한다. 붉은색 육류는 가끔 먹는 편이 좋으며 소량의 붉은 와인과 함께 하면 더욱 좋다.");

        prescription_img1.setImageResource(R.drawable.prescription_pressure_img);
        prescription_img2.setImageResource(R.drawable.prescription_pressure_diet);
    }
    private SpannableString avg_tv_spannable_non_normal(String str){
        SpannableString spannableString=new SpannableString(str);
        int start=str.indexOf(userName);
        int end=start+userName.length();
        spannableString.setSpan(new UnderlineSpan(),start,end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new RelativeSizeSpan(1.2f),start,end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        start=str.indexOf(Integer.toString(avg_systolic));
        end=start+Integer.toString(avg_systolic).length();
        spannableString.setSpan(new StyleSpan(Typeface.BOLD_ITALIC),start,end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new UnderlineSpan(),start,end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        if(avg_systolic==avg_sugarlevels){ //혈압과 혈당의 평균이 혹시라도 같은 경우를 대비
            start=str.indexOf(Integer.toString(avg_sugarlevels),2);
        }else{
            start=str.indexOf(Integer.toString(avg_sugarlevels));
        }
        end=start+Integer.toString(avg_sugarlevels).length();
        spannableString.setSpan(new StyleSpan(Typeface.BOLD_ITALIC),start,end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new UnderlineSpan(),start,end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        start=str.indexOf(Float.toString(BMI));
        end=start+Float.toString(BMI).length();
        spannableString.setSpan(new StyleSpan(Typeface.BOLD_ITALIC),start,end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new UnderlineSpan(),start,end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        start=str.indexOf("필요한 것은 ")+7;
        end=start+2;
        spannableString.setSpan(new RelativeSizeSpan(1.2f),start,end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF0000")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD_ITALIC),start,end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }
    private SpannableString avg_tv_spannable_normal(String str){
        SpannableString spannableString=new SpannableString(str);
        int start=str.indexOf(userName);
        int end=start+userName.length();
        spannableString.setSpan(new UnderlineSpan(),start,end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new RelativeSizeSpan(1.2f),start,end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        start=str.indexOf(Integer.toString(avg_systolic));
        end=start+Integer.toString(avg_systolic).length();
        spannableString.setSpan(new StyleSpan(Typeface.BOLD_ITALIC),start,end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new UnderlineSpan(),start,end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        if(avg_systolic==avg_sugarlevels){ //혈압과 혈당의 평균이 혹시라도 같은 경우를 대비
            start=str.indexOf(Integer.toString(avg_sugarlevels),2);
        }else{
            start=str.indexOf(Integer.toString(avg_sugarlevels));
        }
        end=start+Integer.toString(avg_sugarlevels).length();
        spannableString.setSpan(new StyleSpan(Typeface.BOLD_ITALIC),start,end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new UnderlineSpan(),start,end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        start=str.indexOf(Float.toString(BMI));
        end=start+Float.toString(BMI).length();
        spannableString.setSpan(new StyleSpan(Typeface.BOLD_ITALIC),start,end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new UnderlineSpan(),start,end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        start=str.indexOf("정상");
        end=start+"정상".length();
        spannableString.setSpan(new RelativeSizeSpan(1.2f),start,end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#3391D1")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD_ITALIC),start,end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }
}