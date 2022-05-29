package com.ssu.takecare.UI;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.ssu.takecare.Fragment.PressureFragment;
import com.ssu.takecare.Fragment.SugarFragment;
import com.ssu.takecare.Fragment.WeightFragment;
import com.ssu.takecare.R;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ShareGraph extends AppCompatActivity {

    TextView year_graph, month_graph;
    Button pressure_btn, sugar_btn, weight_btn;

    Date now_date = Calendar.getInstance().getTime();
    String now_year = new SimpleDateFormat("yyyy", Locale.getDefault()).format((now_date));
    String now_month = new SimpleDateFormat("M", Locale.getDefault()).format((now_date));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_graph);

        getSupportFragmentManager().beginTransaction().replace(R.id.graph_fragment, new PressureFragment()).commit();

        pressure_btn = findViewById(R.id.pressure_btn);
        sugar_btn = findViewById(R.id.sugar_btn);
        weight_btn = findViewById(R.id.weight_btn);

        pressure_btn.setSelected(true);

        year_graph = findViewById(R.id.year_text);
        year_graph.setText(now_year);

        month_graph = findViewById(R.id.month_text);
        month_graph.setText(now_month);
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
                break;
        }
    }

    /*혈압, 혈당, 체중 버튼 이벤트 처리*/
    public void graph_btn_click(View view) {
        switch (view.getId()){
            case R.id.pressure_btn:
                pressure_btn.setSelected(true);
                sugar_btn.setSelected(false);
                weight_btn.setSelected(false);
                getSupportFragmentManager().beginTransaction().replace(R.id.graph_fragment, new PressureFragment()).commit();
                break;
            case R.id.sugar_btn:
                getSupportFragmentManager().beginTransaction().replace(R.id.graph_fragment, new SugarFragment()).commit();
                pressure_btn.setSelected(false);
                sugar_btn.setSelected(true);
                weight_btn.setSelected(false);
                break;
            case R.id.weight_btn:
                getSupportFragmentManager().beginTransaction().replace(R.id.graph_fragment, new WeightFragment()).commit();
                pressure_btn.setSelected(false);
                sugar_btn.setSelected(false);
                weight_btn.setSelected(true);
                break;
        }
    }

    public void close_btn_event(View view) {
        finish();
    }
}
