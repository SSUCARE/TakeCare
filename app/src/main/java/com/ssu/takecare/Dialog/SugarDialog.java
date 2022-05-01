package com.ssu.takecare.Dialog;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.ssu.takecare.R;
import com.ssu.takecare.UI.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SugarDialog extends Dialog implements View.OnClickListener {

    private Context mContext;
    private SugarDialogListener sugarDialogListener;

    Date currentTime = Calendar.getInstance().getTime();
    String date = new SimpleDateFormat("M월 d일 E요일 H시 m분", Locale.getDefault()).format((currentTime));

    int inputHour = currentTime.getHours();
    int inputMinute = currentTime.getMinutes();

    TextView selectedTime;
    EditText beforeSugar, afterSugar;
    Button okButton_s, cancelButton_s;

    public SugarDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public interface SugarDialogListener {
        void okClicked(String before_sugar, String after_sugar, int input_hour, int input_minute);
    }

    public void setSugarDialogListener(SugarDialogListener sugarDialogListener) {
        this.sugarDialogListener = sugarDialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_sugar);

        selectedTime = findViewById(R.id.tv_time);
        selectedTime.setText(date);

        beforeSugar = findViewById(R.id.et_beforeSugar);
        afterSugar = findViewById(R.id.et_afterSugar);

        okButton_s = findViewById(R.id.btn_ok_s);
        cancelButton_s = findViewById(R.id.btn_cancel_s);

        selectedTime.setOnClickListener(this);
        okButton_s.setOnClickListener(this);
        cancelButton_s.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == selectedTime) {
            Calendar mCurrentTime = Calendar.getInstance();
            int hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mCurrentTime.get(Calendar.MINUTE);

            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    Date now = Calendar.getInstance().getTime();
                    String now_date = new SimpleDateFormat("M월 d일 E요일 ", Locale.getDefault()).format((now));

                    selectedTime.setText(now_date);
                    selectedTime.append(selectedHour + "시 " + selectedMinute + "분");

                    inputHour = selectedHour;
                    inputMinute = selectedMinute;
                }
            }, hour, minute, false); // true의 경우 24시간 형식의 TimePicker 출현

            mTimePicker.show();
        }
        else if (view == okButton_s) {
            String bs_str = beforeSugar.getText().toString();
            String as_str = afterSugar.getText().toString();

            sugarDialogListener.okClicked(bs_str, as_str, inputHour, inputMinute);
            dismiss();
        }
        else if (view == cancelButton_s) {
            cancel();
        }
    }
}