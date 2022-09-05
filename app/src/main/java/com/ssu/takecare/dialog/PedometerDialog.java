package com.ssu.takecare.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ssu.takecare.ApplicationClass;
import com.ssu.takecare.R;

public class PedometerDialog extends Dialog implements View.OnClickListener {

    private PedometerDialog.PedometerDialogListener pedometerDialogListener;

    EditText goal_pedometer;
    Button okButton_p, cancelButton_p;

    public PedometerDialog(Context context) {
        super(context);
    }

    public interface PedometerDialogListener {
        void okClicked(String goalStep);
    }

    public void setPedometerDialogListener(PedometerDialog.PedometerDialogListener pedometerDialogListener) {
        this.pedometerDialogListener = pedometerDialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_pedometer);

        goal_pedometer = findViewById(R.id.et_pedometer);

        okButton_p = findViewById(R.id.btn_ok_p);
        cancelButton_p = findViewById(R.id.btn_cancel_p);

        okButton_p.setOnClickListener(this);
        cancelButton_p.setOnClickListener(this);

        if (ApplicationClass.sharedPreferences.getInt("goal_steps", 0) != 0) {
            goal_pedometer.setText(String.valueOf(ApplicationClass.sharedPreferences.getInt("goal_steps", 0)));
            okButton_p.setText("수정");
        }
        else {
            okButton_p.setText("확인");
        }
    }

    @Override
    public void onClick(View view) {
        if (view == okButton_p) {
            String gp_str = goal_pedometer.getText().toString();

            pedometerDialogListener.okClicked(gp_str);
            dismiss();
        }
        else if (view == cancelButton_p) {
            cancel();
        }
    }
}
