package com.ssu.takecare.assist.medicine;

import static android.content.Context.MODE_PRIVATE;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.ssu.takecare.R;
import com.ssu.takecare.assist.service.AlarmReceiver;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class ListViewMedicineAdapter extends BaseAdapter {

    private Context context;
    private Activity activity;
    private Map<String, String> name_period;
    private Map<String, String[]> time;

    public ListViewMedicineAdapter(Context context, Activity activity) {
        super();
        this.context = context;
        this.activity = activity;
        name_period = new LinkedHashMap<>();
        time = new LinkedHashMap<>();

        addMedicine(getMedicineCount());
    }

    public void addMedicine(int num) {
        for (int i = 0; i < num; i++) {
            String[] timeList = new String[3];
            for (int j = 0; j < 3; j++) {
                timeList[j] = context.getSharedPreferences("MedicineInfo" + (i + 1), MODE_PRIVATE).getString("medicine_time" + (j + 1), "NONE");
            }
            name_period.put(context.getSharedPreferences("MedicineInfo" + (i + 1), MODE_PRIVATE).getString("medicine_name", "NONE"),
                    context.getSharedPreferences("MedicineInfo" + (i + 1), MODE_PRIVATE).getString("medicine_period", "NONE"));
           time.put(context.getSharedPreferences("MedicineInfo" + (i + 1), MODE_PRIVATE).getString("medicine_name", "NONE"), timeList);
        }

        checkMedicine();
    }

    public void checkMedicine() {
        int numMedicine;
        if (activity.getSharedPreferences("MedicineInfo1", MODE_PRIVATE).getString("medicine_name", "NONE").equals("NONE"))
            numMedicine = 0;
        else if (activity.getSharedPreferences("MedicineInfo2", MODE_PRIVATE).getString("medicine_name", "NONE").equals("NONE"))
            numMedicine = 1;
        else if (activity.getSharedPreferences("MedicineInfo3", MODE_PRIVATE).getString("medicine_name", "NONE").equals("NONE"))
            numMedicine = 2;
        else
            numMedicine = 3;

        String[] check = new String[numMedicine];
        String[] check_temp = new String[numMedicine];
        for (int i = 0; i < numMedicine; i++) {
            check[i] = "YES";
            check_temp[i] = activity.getSharedPreferences("MedicineInfo" + (i + 1), MODE_PRIVATE).getString("medicine_check", "NO");
        }

        ImageView today_medicine = activity.findViewById(R.id.iv_medicine);
        if (numMedicine != 0 && Arrays.equals(check, check_temp)) {
            today_medicine.setImageResource(R.drawable.medicine_complete);
        }
        else {
            today_medicine.setImageResource(R.drawable.medicine);
        }
    }

    public String getElementByIndex(Map<String, String> map, int index) {
        return map.get(map.keySet().toArray()[index]);
    }

    public String getKeyByIndex(Map<String, String> map, int index) {
        return (String) map.keySet().toArray()[index];
    }

    public int getMedicineCount() {
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
    public int getCount() {
       return name_period.size();
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

        MedicineTime1.setText(time.get(time.keySet().toArray()[position])[0]);
        MedicineTime2.setText(time.get(time.keySet().toArray()[position])[1]);
        MedicineTime3.setText(time.get(time.keySet().toArray()[position])[2]);

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
                int count = getCount();
                if (count > 0 && position < count) {
                    SharedPreferences pref = context.getSharedPreferences("MedicineInfo" + (position + 1), MODE_PRIVATE);

                    int num;
                    if (context.getSharedPreferences("MedicineInfo" + (position + 1), MODE_PRIVATE).getInt("medicine_alarm_id_1", 0) != 0)
                        num = 0;
                    else if (context.getSharedPreferences("MedicineInfo" + (position + 1), MODE_PRIVATE).getInt("medicine_alarm_id_2", 0) != 0)
                        num = 1;
                    else if (context.getSharedPreferences("MedicineInfo" + (position + 1), MODE_PRIVATE).getInt("medicine_alarm_id_3", 0) != 0)
                        num = 2;
                    else
                        num = 3;

                    for (int i = 0; i < num; i++)
                        cancelAlarm(context, pref.getInt("medicine_alarm_id_" + (i + 1), 0));

                    SharedPreferences.Editor editor = pref.edit();
                    editor.clear();
                    editor.apply();

                    switch (position) {
                        case 0:
                            copySharedPreferences(context.getSharedPreferences("MedicineInfo1", MODE_PRIVATE), context.getSharedPreferences("MedicineInfo2", MODE_PRIVATE));
                            copySharedPreferences(context.getSharedPreferences("MedicineInfo2", MODE_PRIVATE), context.getSharedPreferences("MedicineInfo3", MODE_PRIVATE));
                            break;
                        case 1:
                            copySharedPreferences(context.getSharedPreferences("MedicineInfo2", MODE_PRIVATE), context.getSharedPreferences("MedicineInfo3", MODE_PRIVATE));
                        case 2:
                            clearSharedPreferences(context.getSharedPreferences("MedicineInfo3", MODE_PRIVATE));
                    }

                    name_period.remove(med_name);
                    time.remove(med_name);
                    notifyDataSetChanged();

                    checkMedicine();
                }
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
                    int num;
                    if (context.getSharedPreferences("MedicineInfo" + (position + 1), MODE_PRIVATE).getInt("medicine_alarm_id_1", 0) != 0)
                        num = 0;
                    else if (context.getSharedPreferences("MedicineInfo" + (position + 1), MODE_PRIVATE).getInt("medicine_alarm_id_2", 0) != 0)
                        num = 1;
                    else if (context.getSharedPreferences("MedicineInfo" + (position + 1), MODE_PRIVATE).getInt("medicine_alarm_id_3", 0) != 0)
                        num = 2;
                    else
                        num = 3;

                    for (int i = 0; i < num; i++)
                        cancelAlarm(context, pref.getInt("medicine_alarm_id_" + (i + 1), 0));

                    MedicineAlarm.setImageResource(R.drawable.alarm_off);
                    editor.putString("medicine_alarm", "OFF");
                }
                else {
                    MedicineAlarm.setImageResource(R.drawable.alarm_on);
                    editor.putString("medicine_alarm", "ON");
                }

                editor.apply();
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

    public void cancelAlarm(Context context, int alarmId) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getBroadcast(context, alarmId, intent, PendingIntent.FLAG_IMMUTABLE);
        } else {
            pendingIntent = PendingIntent.getBroadcast(context, alarmId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
        alarmManager.cancel(pendingIntent);
    }

    public void clearSharedPreferences(SharedPreferences pref) {
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.apply();
    }

    public void copySharedPreferences(SharedPreferences to, SharedPreferences from) {
        SharedPreferences.Editor editor = to.edit();
        editor.clear();

        for (Map.Entry<String, ?> entry : from.getAll().entrySet()) {
            Object v = entry.getValue();
            String key = entry.getKey();

            if(v instanceof Boolean)
                editor.putBoolean(key, (Boolean) v);
            else if(v instanceof Float)
                editor.putFloat(key, (Float) v);
            else if(v instanceof Integer)
                editor.putInt(key, (Integer) v);
            else if(v instanceof Long)
                editor.putLong(key, (Long) v);
            else if(v instanceof String)
                editor.putString(key, (String) v);
        }
        editor.apply();

        clearSharedPreferences(from);
    }
}
