package com.ssu.takecare.assist.medicine;

import static android.content.Context.MODE_PRIVATE;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.ssu.takecare.R;
import java.util.LinkedHashMap;
import java.util.Map;

public class ListViewMedicineAdapter extends BaseAdapter {

    private Context context;
    private Map<String, String> name_period;
    private String[][] timeList;

    public ListViewMedicineAdapter(Context context) {
        super();
        this.context = context;
        name_period = new LinkedHashMap<>();
        timeList = new String[3][3];

        addNamePeriod(getCount());
    }

    public void addNamePeriod(int num) {
        for (int i = 0; i < num; i++) {
            name_period.put(context.getSharedPreferences("MedicineInfo" + (i + 1), MODE_PRIVATE).getString("medicine_name", "NONE"),
                    context.getSharedPreferences("MedicineInfo" + (i + 1), MODE_PRIVATE).getString("medicine_period", "NONE"));
            for (int j = 0; j < 3; j++) {
                timeList[i][j] = context.getSharedPreferences("MedicineInfo" + (i + 1), MODE_PRIVATE).getString("medicine_time" + (j + 1), "NONE");
            }
        }
    }

    public String getElementByIndex(Map<String, String> map, int index) {
        return map.get(map.keySet().toArray()[index]);
    }

    public String getKeyByIndex(Map<String, String> map, int index) {
        return (String) map.keySet().toArray()[index];
    }

    @Override
    public int getCount() {
        int numMedicine;
        if (context.getSharedPreferences("MedicineInfo1", MODE_PRIVATE).getString("medicine_name", "NONE").equals("NONE"))
            numMedicine = 0;
        else if (context.getSharedPreferences("MedicineInfo2", MODE_PRIVATE).getString("medicine_name", "NONE").equals("NONE"))
            numMedicine = 1;
        else if (context.getSharedPreferences("MedicineInfo3", MODE_PRIVATE).getString("medicine_name", "NONE").equals("NONE"))
            numMedicine = 2;
        else
            numMedicine = 3;

        return numMedicine;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.listview_medicine, parent, false);

        TextView MedicineName = view.findViewById(R.id.medicine_list_name);
        TextView MedicinePeriod = view.findViewById(R.id.medicine_list_period);
        TextView MedicineTime1 = view.findViewById(R.id.medicine_list_time1);
        TextView MedicineTime2 = view.findViewById(R.id.medicine_list_time2);
        TextView MedicineTime3 = view.findViewById(R.id.medicine_list_time3);

        String med_name = getKeyByIndex(name_period, position);
        MedicineName.setText(med_name);

        String med_period = getElementByIndex(name_period, position);
        MedicinePeriod.setText(med_period);

        MedicineTime1.setText(timeList[position][0]);
        MedicineTime2.setText(timeList[position][1]);
        MedicineTime3.setText(timeList[position][2]);

        switch (med_period) {
            case "설정 안 함":
                MedicineTime1.setVisibility(View.GONE);
                MedicineTime2.setVisibility(View.GONE);
                MedicineTime3.setVisibility(View.GONE);
                break;
            case "하루에 한 번":
                MedicineTime1.setVisibility(View.VISIBLE);
                MedicineTime2.setVisibility(View.GONE);
                MedicineTime3.setVisibility(View.GONE);
                break;
            case "하루에 두 번":
                MedicineTime1.setVisibility(View.VISIBLE);
                MedicineTime2.setVisibility(View.VISIBLE);
                MedicineTime3.setVisibility(View.GONE);
                break;
            case "하루에 세 번":
                MedicineTime1.setVisibility(View.VISIBLE);
                MedicineTime2.setVisibility(View.VISIBLE);
                MedicineTime3.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }

        ImageView MedicineClose = view.findViewById(R.id.close_medicine_list);
        MedicineClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                name_period.remove(med_name);
//                notifyDataSetChanged();
            }
        });

        ImageView MedicineAlarm = view.findViewById(R.id.alarm_medicine_list);
        if (context.getSharedPreferences("MedicineInfo" + (position + 1), MODE_PRIVATE).getString("medicine_alarm", "NONE").equals("ON"))
            MedicineAlarm.setImageResource(R.drawable.alarm_on);
        else
            MedicineAlarm.setImageResource(R.drawable.alarm_off);
        MedicineAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref = context.getSharedPreferences("MedicineInfo" + (position + 1), MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();

                if (pref.getString("medicine_alarm", "NONE").equals("ON")) {
                    MedicineAlarm.setImageResource(R.drawable.alarm_off);
                    editor.putString("medicine_alarm", "OFF");
                    editor.apply();
                }
                else {
                    MedicineAlarm.setImageResource(R.drawable.alarm_on);
                    editor.putString("medicine_alarm", "ON");
                    editor.apply();
                }
            }
        });

        ImageView MedicineCheck = view.findViewById(R.id.check_medicine_list);
        if (context.getSharedPreferences("MedicineInfo" + (position + 1), MODE_PRIVATE).getString("medicine_check", "NO").equals("YES"))
            MedicineCheck.setImageResource(R.drawable.check_o);
        MedicineCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MedicineCheck.setImageResource(R.drawable.check_o);

                SharedPreferences pref = context.getSharedPreferences("MedicineInfo" + (position + 1), MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("medicine_check", "YES");
                editor.apply();
            }
        });

        return view;
    }
}
