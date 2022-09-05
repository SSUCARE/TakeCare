package com.ssu.takecare.dialog;

import static android.content.Context.MODE_PRIVATE;
import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import com.ssu.takecare.R;
import com.ssu.takecare.assist.medicine.ListViewMedicineAdapter;
import java.util.Calendar;

public class MedicineAddDialog {

    private Context context;
    private Activity activity;
    private Dialog dialog;
    private String[] items;
    private ListViewMedicineAdapter adapter;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private String numMedicine;

    public MedicineAddDialog(Context context, Activity activity, String numMedicine, ListViewMedicineAdapter adapter){
        this.context = context;
        this.activity = activity;
        this.numMedicine = numMedicine;
        this.adapter = adapter;
        setDialog();
        findViews();
    }

    public void showDialog(){
        dialog.show();
    }

    public void dismiss(){
        dialog.dismiss();
    }

    private void setDialog() {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_medicine);

        LinearLayout linearLayout_time = dialog.findViewById(R.id.layout_med_time);
        LinearLayout linearLayout_alarm = dialog.findViewById(R.id.layout_med_alarm);
        EditText editText1 = dialog.findViewById(R.id.et_med_time1);
        EditText editText2 = dialog.findViewById(R.id.et_med_time2);
        EditText editText3 = dialog.findViewById(R.id.et_med_time3);

        items = context.getResources().getStringArray(R.array.period_array);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = dialog.findViewById(R.id.sp_period);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                switch (position) {
                    case 0:
                        linearLayout_time.setVisibility(View.GONE);
                        linearLayout_alarm.setVisibility(View.GONE);
                        break;
                    case 1:
                        linearLayout_time.setVisibility(View.VISIBLE);
                        linearLayout_alarm.setVisibility(View.VISIBLE);
                        editText1.setVisibility(View.VISIBLE);
                        editText2.setVisibility(View.GONE);
                        editText3.setVisibility(View.GONE);
                        break;
                    case 2:
                        linearLayout_time.setVisibility(View.VISIBLE);
                        linearLayout_alarm.setVisibility(View.VISIBLE);
                        editText1.setVisibility(View.VISIBLE);
                        editText2.setVisibility(View.VISIBLE);
                        editText3.setVisibility(View.GONE);
                        break;
                    case 3:
                        linearLayout_time.setVisibility(View.VISIBLE);
                        linearLayout_alarm.setVisibility(View.VISIBLE);
                        editText1.setVisibility(View.VISIBLE);
                        editText2.setVisibility(View.VISIBLE);
                        editText3.setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        editText1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mCurrentTime = Calendar.getInstance();
                int hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mCurrentTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String state = "오전";
                        String str_hour = String.valueOf(selectedHour);
                        if (selectedHour > 12) {
                            state = "오후";
                            selectedHour -= 12;
                            if (selectedHour < 10) {
                                str_hour = "0" + selectedHour;
                            }
                            else
                                str_hour = String.valueOf(selectedHour);
                        }

                        String str_minute = String.valueOf(selectedMinute);
                        if (selectedMinute < 10) {
                            str_minute = "0" + selectedMinute;
                        }

                        editText1.setText(state + " " + str_hour + ":" + str_minute);
                    }
                }, hour, minute, false);

                mTimePicker.show();
            }
        });

        editText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mCurrentTime = Calendar.getInstance();
                int hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mCurrentTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String state = "오전";
                        String str_hour = String.valueOf(selectedHour);
                        if (selectedHour > 12) {
                            state = "오후";
                            selectedHour -= 12;
                            if (selectedHour < 10) {
                                str_hour = "0" + selectedHour;
                            }
                            else
                                str_hour = String.valueOf(selectedHour);
                        }

                        String str_minute = String.valueOf(selectedMinute);
                        if (selectedMinute < 10) {
                            str_minute = "0" + selectedMinute;
                        }

                        editText2.setText(state + " " + str_hour + ":" + str_minute);
                    }
                }, hour, minute, false);

                mTimePicker.show();
            }
        });

        editText3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mCurrentTime = Calendar.getInstance();
                int hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mCurrentTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String state = "오전";
                        String str_hour = String.valueOf(selectedHour);
                        if (selectedHour > 12) {
                            state = "오후";
                            selectedHour -= 12;
                            if (selectedHour < 10) {
                                str_hour = "0" + selectedHour;
                            }
                            else
                                str_hour = String.valueOf(selectedHour);
                        }

                        String str_minute = String.valueOf(selectedMinute);
                        if (selectedMinute < 10) {
                            str_minute = "0" + selectedMinute;
                        }

                        editText3.setText(state + " " + str_hour + ":" + str_minute);
                    }
                }, hour, minute, false);

                mTimePicker.show();
            }
        });

        pref = context.getSharedPreferences("MedicineInfo" + numMedicine, MODE_PRIVATE);
        editor = pref.edit();
        editor.putString("medicine_alarm", "ON");
        editor.apply();

        androidx.appcompat.widget.SwitchCompat medicine_switch = dialog.findViewById(R.id.med_alarm_on_off);
        medicine_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    editor.putString("medicine_alarm", "ON");
                    editor.apply();
                } else {
                    editor.putString("medicine_alarm", "OFF");
                    editor.apply();
                }
            }
        });
    }

    private void findViews() {
        Button btn_ok = dialog.findViewById(R.id.btn_ok_m);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText_name = dialog.findViewById(R.id.et_med_name);
                Spinner medicine_spinner = dialog.findViewById(R.id.sp_period);
                EditText medicine_time1 = dialog.findViewById(R.id.et_med_time1);
                EditText medicine_time2 = dialog.findViewById(R.id.et_med_time2);
                EditText medicine_time3 = dialog.findViewById(R.id.et_med_time3);

                if (editText_name.getText().toString().replace(" ", "").equals("")) {
                    Toast.makeText(context, "약 이름은 필수 기재사항입니다", Toast.LENGTH_SHORT).show();
                }
                else {
                    pref = context.getSharedPreferences("MedicineInfo" + numMedicine, MODE_PRIVATE);
                    editor = pref.edit();

                    editor.putString("medicine_name", editText_name.getText().toString());
                    editor.putString("medicine_period", items[medicine_spinner.getSelectedItemPosition()]);
                    editor.putString("medicine_time1", medicine_time1.getText().toString());
                    editor.putString("medicine_time2", medicine_time2.getText().toString());
                    editor.putString("medicine_time3", medicine_time3.getText().toString());
                    editor.putString("medicine_check", "NO");
                    editor.apply();

                    adapter.addMedicine(Integer.parseInt(numMedicine));
                    adapter.notifyDataSetChanged();

                    dismiss();
                }
            }
        });

        Button btn_cancel = dialog.findViewById(R.id.btn_cancel_m);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}
