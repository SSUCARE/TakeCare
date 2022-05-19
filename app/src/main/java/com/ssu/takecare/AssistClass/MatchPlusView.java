package com.ssu.takecare.AssistClass;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.ssu.takecare.ApplicationClass;
import com.ssu.takecare.R;
import com.ssu.takecare.Retrofit.RetrofitCallback;

public final class MatchPlusView extends Activity implements SimpleCallbackInterface {
    @Override
    public void onButtonClicked() {
        alertDialogDemo();
    }

    void alertDialogDemo() {
        LayoutInflater li = LayoutInflater.from(getApplicationContext());
        View promptsView = li.inflate(R.layout.dialog_match, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getApplicationContext());
        alertDialogBuilder.setView(promptsView);
        final EditText userInput = (EditText) promptsView.findViewById(R.id.email_match);
        final Button ok_btn= (Button) promptsView.findViewById(R.id.btn_ok_matching_email);
        AlertDialog alertDialog = alertDialogBuilder.create();

        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ApplicationClass.retrofit_manager.careRequest(userInput.getText().toString(), new RetrofitCallback() {

                    @Override
                    public void onError(Throwable t) {
                    }
                    @Override
                    public void onSuccess(String message, String data) {
                        Toast.makeText(view.getContext(), data, Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFailure(int error_code) {
                    }
                });
                alertDialog.dismiss();

            }
        });
        alertDialog.show();
    }
}
