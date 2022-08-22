package com.ssu.takecare.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
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

public class HomeFragment extends Fragment {

    Date currentTime = Calendar.getInstance().getTime();
    String date = new SimpleDateFormat("M월 d일", Locale.getDefault()).format((currentTime));

    Boolean REPORT_FLAG;

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

        setValue(view);
        setStatus(view);

        return view;
    }

    public void setValue(View view) {
        String json = ApplicationClass.sharedPreferences.getString("sugarLevels", null);
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

        String s_systolic = ApplicationClass.sharedPreferences.getString("systolic", null);
        String s_diastolic = ApplicationClass.sharedPreferences.getString("diastolic", null);
        String s_sugar = s_sugarLevels.get(s_sugarLevels.size()-1);
        String s_weight = ApplicationClass.sharedPreferences.getString("weight", null);

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

        REPORT_FLAG = true;
    }
}
