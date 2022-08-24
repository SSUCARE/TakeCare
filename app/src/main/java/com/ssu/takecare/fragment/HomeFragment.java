package com.ssu.takecare.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.ssu.takecare.ApplicationClass;
import com.ssu.takecare.R;
import org.json.JSONArray;
import org.json.JSONException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class HomeFragment extends Fragment implements SensorEventListener {

    SharedPreferences.Editor editor = ApplicationClass.sharedPreferences.edit();

    Date currentTime = Calendar.getInstance().getTime();
    String date = new SimpleDateFormat("M월 d일", Locale.getDefault()).format((currentTime));
    String step_date = new SimpleDateFormat("Md", Locale.getDefault()).format((currentTime));

    Boolean REPORT_FLAG;

    ImageView today_medicine, today_pedometer, today_healthnews;
    TextView today_medicine_content, today_pedometer_content, today_healthnews_content;

    SensorManager sensorManager;
    Sensor stepCountSensor;

    // 현재 걸음 수
    int todaySteps = 0;

    int sensor_flag = 0;
    float sensor_value = 0.0f;

    public HomeFragment(Boolean REPORT_FLAG){
        this.REPORT_FLAG = REPORT_FLAG;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home, container, false);

        TextView today_date = view.findViewById(R.id.tv_date);
        String report_date = "건강 리포트 (" + date + ")";
        today_date.setText(report_date);

        if (REPORT_FLAG) {
            setValue(view);
            setStatus(view);
        }

        today_medicine = view.findViewById(R.id.iv_medicine);
        today_pedometer = view.findViewById(R.id.iv_pedometer);
        today_healthnews = view.findViewById(R.id.iv_healthnews);

        today_medicine_content = view.findViewById(R.id.iv_medicine_content);
        today_pedometer_content = view.findViewById(R.id.iv_pedometer_content);
        today_healthnews_content = view.findViewById(R.id.iv_healthnews_content);

        // 걸음 센서 연결
        // * 옵션
        // - TYPE_STEP_DETECTOR:  리턴 값이 무조건 1, 앱이 종료되면 다시 0부터 시작
        // - TYPE_STEP_COUNTER : 앱 종료와 관계없이 계속 기존의 값을 가지고 있다가 1씩 증가한 값을 리턴
        sensorManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        stepCountSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        // 디바이스에 걸음 센서의 존재 여부 체크
        if (stepCountSensor == null) {
            Toast.makeText(getActivity(), "No Step Sensor", Toast.LENGTH_SHORT).show();
        }

        today_medicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // 길게 누르면 리셋 기능
        today_pedometer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                // 현재 걸음수 초기화
                todaySteps = 0;
                today_pedometer_content.setText(String.valueOf(todaySteps));
                return false;
            }
        });

        today_healthnews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }

    public void setValue(View view) {
        String json = ApplicationClass.sharedPreferences.getString("sugarLevels", "");
        ArrayList<String> s_sugarLevels = new ArrayList<String>();
        if (json != null) {
            try {
                JSONArray a = new JSONArray(json);
                for (int i = 0; i < a.length(); i++) {
                    String sugar = a.optString(i);
                    s_sugarLevels.add(sugar);
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }

        String s_systolic = ApplicationClass.sharedPreferences.getString("systolic", "");
        String s_diastolic = ApplicationClass.sharedPreferences.getString("diastolic", "");
        String s_sugar = s_sugarLevels.get(s_sugarLevels.size() - 1);
        String s_weight = ApplicationClass.sharedPreferences.getString("weight", "");

        TextView hp_input = view.findViewById(R.id.input_high_pressure);
        TextView lp_input = view.findViewById(R.id.input_low_pressure);
        TextView s_input = view.findViewById(R.id.input_sugar);
        TextView w_input = view.findViewById(R.id.input_weight);

        hp_input.setText(s_systolic);
        lp_input.setText(s_diastolic);
        s_input.setText(s_sugar);
        w_input.setText(s_weight);
    }

    public void setStatus(View view) {
        TextView status_p = view.findViewById(R.id.status_pressure);
        TextView status_s = view.findViewById(R.id.status_sugar);
        TextView status_w = view.findViewById(R.id.status_weight);
        Button btn_rp = view.findViewById(R.id.btn_report);

        status_p.setVisibility(View.VISIBLE);
        status_s.setVisibility(View.VISIBLE);
        status_w.setVisibility(View.VISIBLE);
        btn_rp.setVisibility(View.GONE);
    }

    @Override
    public void onStart() {
        super.onStart();

        if (stepCountSensor != null) {
            // 센서 속도 설정
            // * 옵션
            // - SENSOR_DELAY_NORMAL: 20,000 초 딜레이
            // - SENSOR_DELAY_UI: 6,000 초 딜레이
            // - SENSOR_DELAY_GAME: 20,000 초 딜레이
            // - SENSOR_DELAY_FASTEST: 딜레이 없음
            sensorManager.registerListener(this, stepCountSensor, SensorManager.SENSOR_DELAY_FASTEST);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (ApplicationClass.sharedPreferences.getInt("today_steps", 0) != 0) {
            int record_date = ApplicationClass.sharedPreferences.getInt("record_date", 0);
            if (record_date == 0) {
                editor.putInt("record_date", Integer.parseInt(step_date));
                editor.apply();
            }

            // 날짜가 바뀌었다면 0으로 초기화
            if (Integer.parseInt(step_date) - record_date == 0)
                todaySteps = ApplicationClass.sharedPreferences.getInt("today_steps", 0);
            else {
                todaySteps = 0;
                editor.putInt("record_date", Integer.parseInt(step_date));
                editor.putInt("today_steps", 0);
                editor.apply();
            }
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        // 걸음 센서 이벤트 발생시
        if (sensorEvent.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {

            if (sensor_flag == 0) {
                sensor_value = sensorEvent.values[0];
                sensor_flag = 1;
            }

            today_pedometer_content.setText(String.valueOf(todaySteps));

            if (sensorEvent.values[0] != sensor_value) {
                todaySteps++;
                editor.putInt("today_steps", todaySteps);
                editor.apply();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
