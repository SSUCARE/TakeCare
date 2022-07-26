package com.ssu.takecare.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.ssu.takecare.R;

public class WeightDialog extends Dialog implements View.OnClickListener {

    private Context mContext;
    private WeightDialogListener weightDialogListener;

    EditText weight;
    Button okButton_w, cancelButton_w;

    public WeightDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public interface WeightDialogListener {
        void okClicked(String weight);
    }

    public void setWeightDialogListener(WeightDialogListener weightDialogListener) {
        this.weightDialogListener = weightDialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_weight);

        weight = findViewById(R.id.et_weight);

        okButton_w = findViewById(R.id.btn_ok_w);
        cancelButton_w = findViewById(R.id.btn_cancel_w);

        okButton_w.setOnClickListener(this);
        cancelButton_w.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == okButton_w) {
            String w_str = weight.getText().toString();

            weightDialogListener.okClicked(w_str);
            dismiss();
        }
        else if (view == cancelButton_w) {
            cancel();
        }
    }
}