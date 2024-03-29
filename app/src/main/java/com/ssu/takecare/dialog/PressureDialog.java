package com.ssu.takecare.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.ssu.takecare.R;

public class PressureDialog extends Dialog implements View.OnClickListener {

    private PressureDialogListener pressureDialogListener;

    EditText highPressure, lowPressure;
    Button okButton_p, cancelButton_p;
    Boolean report_flag;

    public PressureDialog(Context context, Boolean report_flag) {
        super(context);
        this.report_flag = report_flag;
    }

    public interface PressureDialogListener {
        void okClicked(String high_pressure, String low_pressure);
    }

    public void setPressureDialogListener(PressureDialogListener pressureDialogListener) {
        this.pressureDialogListener = pressureDialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_pressure);

        highPressure = findViewById(R.id.et_highPressure);
        lowPressure = findViewById(R.id.et_lowPressure);

        okButton_p = findViewById(R.id.btn_ok_p);
        cancelButton_p = findViewById(R.id.btn_cancel_p);

        if (report_flag) {
            okButton_p.setText("수정");
        }
        else {
            okButton_p.setText("확인");
        }

        okButton_p.setOnClickListener(this);
        cancelButton_p.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == okButton_p) {
            String hp_str = highPressure.getText().toString();
            String lp_str = lowPressure.getText().toString();

            pressureDialogListener.okClicked(hp_str, lp_str);
            dismiss();
        }
        else if (view == cancelButton_p) {
            cancel();
        }
    }
}