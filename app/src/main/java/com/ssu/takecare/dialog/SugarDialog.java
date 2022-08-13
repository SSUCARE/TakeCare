package com.ssu.takecare.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.ssu.takecare.R;

public class SugarDialog extends Dialog implements View.OnClickListener {

    private SugarDialogListener sugarDialogListener;

    EditText beforeSugar, afterSugar;
    Button okButton_s, cancelButton_s;

    public SugarDialog(Context context) {
        super(context);
    }

    public interface SugarDialogListener {
        void okClicked(String before_sugar, String after_sugar);
    }

    public void setSugarDialogListener(SugarDialogListener sugarDialogListener) {
        this.sugarDialogListener = sugarDialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_sugar);

        beforeSugar = findViewById(R.id.et_beforeSugar);
        afterSugar = findViewById(R.id.et_afterSugar);

        okButton_s = findViewById(R.id.btn_ok_s);
        cancelButton_s = findViewById(R.id.btn_cancel_s);

        okButton_s.setOnClickListener(this);
        cancelButton_s.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == okButton_s) {
            String bs_str = beforeSugar.getText().toString();
            String as_str = afterSugar.getText().toString();

            sugarDialogListener.okClicked(bs_str, as_str);
            dismiss();
        }
        else if (view == cancelButton_s) {
            cancel();
        }
    }
}