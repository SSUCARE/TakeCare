package com.ssu.takecare.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.ssu.takecare.ApplicationClass;
import com.ssu.takecare.R;
import com.ssu.takecare.Retrofit.RetrofitCallback;

public class MatchFindUserDialog {

    private Integer userId;
    private TextView userNameText;
    private TextView userGenderText;
    private TextView userAgeText;
    private Button btnOK, btnNO;

    private Activity activity;
    private Dialog dialog;

    public MatchFindUserDialog(Activity activity, Integer userId, String userName, String userGender, Integer userAge) {
        this.activity = activity;
        this.userId = userId;
        setDialog();
        findViews(userName, userGender, userAge);
    }

    public void showDialog(){
        dialog.show();
    }

    public void dismiss(){
        dialog.dismiss();
    }

    private void setDialog() {
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_showprofile);
    }

    private void findViews(String userName, String userGender, Integer userAge){
        userNameText = dialog.findViewById(R.id.user_name_by_email);
        userGenderText = dialog.findViewById(R.id.user_gender_by_email);
        userAgeText = dialog.findViewById(R.id.user_age_by_email);
        userNameText.setText(userName);
        userGenderText.setText(userGender);
        userAgeText.setText(userAge.toString());

        btnOK = dialog.findViewById(R.id.OK_button);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApplicationClass.retrofit_manager.careRequest(userId, new RetrofitCallback() {

                    @Override
                    public void onError(Throwable t) {
                    }

                    @Override
                    public void onSuccess(String message, String data) {
                        Toast.makeText(view.getContext(), "요청 완료", Toast.LENGTH_SHORT).show();
                        dismiss();
                    }

                    @Override
                    public void onFailure(int error_code) {
                    }
                });
            }
        });

        btnNO = dialog.findViewById(R.id.NO_button);
        btnNO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}