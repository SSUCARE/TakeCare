package com.ssu.takecare.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.ssu.takecare.ApplicationClass;
import com.ssu.takecare.dialog.PressureDialog;
import com.ssu.takecare.dialog.SugarDialog;
import com.ssu.takecare.dialog.WeightDialog;
import com.ssu.takecare.fragment.HomeFragment;
import com.ssu.takecare.fragment.MyPageFragment;
import com.ssu.takecare.fragment.CaredShareFragment;
import com.ssu.takecare.fragment.CaringShareFragment;
import com.ssu.takecare.R;
import com.ssu.takecare.retrofit.report.DataGetReport;
import com.ssu.takecare.retrofit.match.DataResponseCare;
import com.ssu.takecare.retrofit.match.ResponseCare;
import com.ssu.takecare.retrofit.RetrofitCallback;
import com.ssu.takecare.retrofit.customcallback.RetrofitCareCallback;
import com.ssu.takecare.retrofit.customcallback.RetrofitReportCallback;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private long backKeyPressedTime = 0;

    SharedPreferences.Editor editor = ApplicationClass.sharedPreferences.edit();

    private ImageButton tab_btn1, tab_btn2, tab_btn3;
    private TextView hp_input, lp_input;
    private TextView bs_input, as_input;
    private TextView w_input;

    private String ROLE_CARED_OR_ROLE_CARER;

    Date currentTime = Calendar.getInstance().getTime();
    String date_year = new SimpleDateFormat("yyyy", Locale.getDefault()).format((currentTime));
    String date_month = new SimpleDateFormat("MM", Locale.getDefault()).format((currentTime));
    String date_day = new SimpleDateFormat("dd", Locale.getDefault()).format((currentTime));

    int find_year = Integer.parseInt(date_year);
    int find_month = Integer.parseInt(date_month);
    int find_day = Integer.parseInt(date_day);

    int userId;
    int reportId;

    Boolean REPORT_FLAG = false;
    int r_systolic = 0; int r_diastolic = 0; int r_weight = 0;
    List<Integer> r_sugarLevels = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ROLE_CARED_OR_ROLE_CARER = ApplicationClass.sharedPreferences.getString("role", "");

        tab_btn1 = findViewById(R.id.tab_btn1);
        tab_btn2 = findViewById(R.id.tab_btn2);
        tab_btn3 = findViewById(R.id.tab_btn3);

        init_getReport();

        getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment, new HomeFragment(REPORT_FLAG)).commit();
    }

    public void init_getReport(){
        userId = ApplicationClass.sharedPreferences.getInt("userId",-1);
        if (userId != -1) {
            ApplicationClass.retrofit_manager.getReport(userId, find_year, find_month, find_day, new RetrofitReportCallback() {
                @Override
                public void onError(Throwable t) {
                }

                @Override
                public void onSuccess(String message, List<DataGetReport> data) {
                    if (data.size() != 0) {
                        reportId = data.get(0).getReportId();
                        r_systolic = data.get(0).getSystolic();
                        r_diastolic = data.get(0).getDiastolic();
                        r_weight = data.get(0).getWeight();
                        r_sugarLevels = data.get(0).getSugarLevels();

                        REPORT_FLAG = true;
                        Button btn_rp = (Button) findViewById(R.id.btn_report);
                        btn_rp.setText("레포트 수정");
                    }
                    else {
                        REPORT_FLAG = false;
                    }
                }

                @Override
                public void onFailure(int error_code) {
                }
            });
        }
        else {
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        // 2초 이내에 뒤로가기 버튼을 한번 더 클릭시 앱 종료
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
        else {
            moveTaskToBack(true); // 태스크를 백그라운드로 이동
            finishAndRemoveTask(); // 액티비티 종료 + 태스크 리스트에서 지우기
            System.exit(0);
        }
    }

    public void inputPressure(View view) {
        PressureDialog pDialog = new PressureDialog(this);
        pDialog.setPressureDialogListener(new PressureDialog.PressureDialogListener() {
            @Override
            public void okClicked(String high_pressure, String low_pressure) {
                hp_input = findViewById(R.id.input_high_pressure);
                lp_input = findViewById(R.id.input_low_pressure);

                if (!high_pressure.equals(""))
                    hp_input.setText(high_pressure);

                if (!low_pressure.equals(""))
                    lp_input.setText(low_pressure);
            }
        });

        pDialog.show();
    }

    public void inputSugar(View view) {
        SugarDialog sDialog = new SugarDialog(this);
        sDialog.setSugarDialogListener(new SugarDialog.SugarDialogListener() {
            @Override
            public void okClicked(String before_sugar, String after_sugar) {
                bs_input = findViewById(R.id.input_before_sugar);
                as_input = findViewById(R.id.input_after_sugar);

                if (!before_sugar.equals(""))
                    bs_input.setText(before_sugar);

                if (!after_sugar.equals(""))
                    as_input.setText(after_sugar);
            }
        });

        sDialog.show();
    }

    public void inputWeight(View view) {
        WeightDialog wDialog = new WeightDialog(this);
        wDialog.setWeightDialogListener(new WeightDialog.WeightDialogListener() {
            @Override
            public void okClicked(String weight) {
                w_input = findViewById(R.id.input_weight);

                if (!weight.equals(""))
                    w_input.setText(weight);
            }
        });

        wDialog.show();
    }

    public void makeReport(View view) {
        hp_input = findViewById(R.id.input_high_pressure);
        lp_input = findViewById(R.id.input_low_pressure);
        bs_input = findViewById(R.id.input_before_sugar);
        as_input = findViewById(R.id.input_after_sugar);
        w_input = findViewById(R.id.input_weight);

        int systolic = 0;
        int diastolic = 0;
        List<Integer> sugarLevels = new ArrayList<>();
        int weight = 0;

        if (!hp_input.getText().toString().equals("____")) {
            systolic = Integer.parseInt(hp_input.getText().toString());
        }

        if (!lp_input.getText().toString().equals("____")) {
            diastolic = Integer.parseInt(lp_input.getText().toString());
        }

        if (!bs_input.getText().toString().equals("____")) {
            sugarLevels.add(Integer.parseInt(bs_input.getText().toString()));
        }

        if (!as_input.getText().toString().equals("____")) {
            sugarLevels.add(Integer.parseInt(as_input.getText().toString()));
        }

        if (!w_input.getText().toString().equals("____")) {
            weight = Integer.parseInt(w_input.getText().toString());
        }

        if (REPORT_FLAG) {
            r_sugarLevels.addAll(sugarLevels);

            // reportId, r_systolic, r_diastolic, r_sugarLevels, r_weight 기존값들 어떻게 사용할지는 조금 생각해보기
            // 수정하기 API 만들기
            ApplicationClass.retrofit_manager.updateReport(reportId, systolic, diastolic, r_sugarLevels, weight, new RetrofitCallback() {
                @Override
                public void onError(Throwable t) {
                }

                @Override
                public void onSuccess(String message, String token) {
                    String str = "____";
                    hp_input.setText(str);
                    lp_input.setText(str);
                    bs_input.setText(str);
                    as_input.setText(str);
                    w_input.setText(str);
                }

                @Override
                public void onFailure(int error_code) {
                }
            });

            REPORT_FLAG = true;
            Button btn_rp = (Button) findViewById(R.id.btn_report);
            btn_rp.setText("레포트 수정");
        }
        else {
            ApplicationClass.retrofit_manager.makeReport(systolic, diastolic, sugarLevels, weight, new RetrofitCallback() {
                @Override
                public void onError(Throwable t) {
                }

                @Override
                public void onSuccess(String message, String token) {
                    String str = "____";
                    hp_input.setText(str);
                    lp_input.setText(str);
                    bs_input.setText(str);
                    as_input.setText(str);
                    w_input.setText(str);
                    Toast.makeText(getApplicationContext(), "리포트가 작성되었습니다", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(int error_code) {
                }
            });

            REPORT_FLAG = true;
            Button btn_rp = (Button) findViewById(R.id.btn_report);
            btn_rp.setText("레포트 수정");
        }
    }

    public void logout(View view) {
        clearInfo();
        finish();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }

    public void clearInfo() {
        editor.putString("email_login", "");
        editor.putString("password_login", "");
        editor.putString("accessToken", "");
        editor.putInt("userId", 0);
        editor.putString("name", "");
        editor.putString("gender", "");
        editor.putInt("age", 0);
        editor.putInt("height", 0);
        editor.putString("role", "");
        editor.apply();
    }

    public void click_event(View view) {
        switch (view.getId()) {
            case R.id.tab_btn1:
                getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment, new HomeFragment(REPORT_FLAG)).commit();
                tab_btn1.setImageResource(R.drawable.tab_btn1_select);
                tab_btn2.setImageResource(R.drawable.tab_btn2);
                tab_btn3.setImageResource(R.drawable.tab_btn3);
                break;
            case R.id.tab_btn2:
                if (ROLE_CARED_OR_ROLE_CARER.equals("보호자")) {
                    ApplicationClass.retrofit_manager.getCareDBMatchInfo(new RetrofitCareCallback() {
                        @Override
                        public void onError(Throwable t) {
                        }

                        @Override
                        public void onSuccess(String message, ResponseCare data) {
                            int mapping_count = 0;
                            List<String> UserName = new ArrayList<>();
                            List<Integer> UserId = new ArrayList<>();
                            List<DataResponseCare> list = data.getData();

                            for (int i = 0; i < list.size(); i++){
                                if (list.get(i).getStatus().equals("ACCEPTED")) {
                                    UserName.add(list.get(i).getUserName());
                                    UserId.add(list.get(i).getUserId());
                                    mapping_count++;
                                }
                            }

                            editor.putInt("Mapping_Count", mapping_count);
                            editor.apply();

                            getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment, new CaringShareFragment(UserName, UserId)).commit();
                        }

                        @Override
                        public void onFailure(int error_code) {
                        }
                    });
                }
                else {
                    ApplicationClass.retrofit_manager.getCareDBMatchInfo(new RetrofitCareCallback() {
                        @Override
                        public void onError(Throwable t) {
                        }

                        @Override
                        public void onSuccess(String message, ResponseCare data) {
                            int mapping_count = 0;
                            List<String> UserName = new ArrayList<>();
                            List<Integer> UserId = new ArrayList<>();
                            List<DataResponseCare> list = data.getData();

                            for (int i = 0; i < list.size(); i++){
                                if (list.get(i).getStatus().equals("ACCEPTED")) {
                                    UserName.add(list.get(i).getUserName());
                                    UserId.add(list.get(i).getUserId());
                                    mapping_count++;
                                }
                            }

                            editor.putInt("Mapping_Count", mapping_count);
                            editor.apply();

                            getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment, new CaredShareFragment(UserName, UserId)).commit();
                        }

                        @Override
                        public void onFailure(int error_code) {
                        }
                    });
                }

                tab_btn1.setImageResource(R.drawable.tab_btn1);
                tab_btn2.setImageResource(R.drawable.tab_btn2_select);
                tab_btn3.setImageResource(R.drawable.tab_btn3);
                break;
            case R.id.tab_btn3:
                getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment, new MyPageFragment()).commit();
                tab_btn1.setImageResource(R.drawable.tab_btn1);
                tab_btn2.setImageResource(R.drawable.tab_btn2);
                tab_btn3.setImageResource(R.drawable.tab_btn3_select);
                break;
            default:
                break;
        }
    }
}