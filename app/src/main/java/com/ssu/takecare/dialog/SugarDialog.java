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

    EditText sugar;
    Button okButton_s, cancelButton_s;

    public SugarDialog(Context context) {
        super(context);
    }

    public interface SugarDialogListener {
        void okClicked(String sugar);
    }

    public void setSugarDialogListener(SugarDialogListener sugarDialogListener) {
        this.sugarDialogListener = sugarDialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_sugar);

        sugar = findViewById(R.id.et_sugar);

        okButton_s = findViewById(R.id.btn_ok_s);
        cancelButton_s = findViewById(R.id.btn_cancel_s);

        okButton_s.setOnClickListener(this);
        cancelButton_s.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == okButton_s) {
            String s_str = sugar.getText().toString();

            sugarDialogListener.okClicked(s_str);
            dismiss();
        }
        else if (view == cancelButton_s) {
            cancel();
        }
    }
}