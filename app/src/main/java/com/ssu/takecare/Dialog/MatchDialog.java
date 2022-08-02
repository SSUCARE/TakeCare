package com.ssu.takecare.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import com.ssu.takecare.ApplicationClass;
import com.ssu.takecare.AssistClass.ListViewMatchAdapter;
import com.ssu.takecare.R;
import com.ssu.takecare.Retrofit.Match.DataResponseGetUser;
import com.ssu.takecare.Retrofit.RetrofitCustomCallback.RetrofitUserInfoCallback;

public class MatchDialog {

    private Integer userId;
    private String userName;
    private String userGender;
    private Integer userAge;
    private EditText txt;
    private Button btn;
    private Activity activity;
    private Dialog dialog;
    private ListViewMatchAdapter adapter;

    public MatchDialog(Activity activity,ListViewMatchAdapter adapter) {
        this.activity = activity;
        this.adapter=adapter;
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
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_match);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes((WindowManager.LayoutParams) params);
    }

    private void findViews(){
        txt = dialog.findViewById(R.id.email_match);
        btn = dialog.findViewById(R.id.btn_ok_matching_email);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email= txt.getText().toString();

                if (!email.equals("")) {
                    ApplicationClass.retrofit_manager.getByEmailUserInfo(email, new RetrofitUserInfoCallback() {

                        @Override
                        public void onError(Throwable t) {
                        }

                        @Override
                        public void onSuccess(String message, DataResponseGetUser data) {
                            userId = data.getId();
                            userName = data.getName();
                            userGender = data.getGender();
                            userAge = data.getAge();

                            Log.d("MatchDialog : ", "userId : " + userId);
                            Log.d("MatchDialog : ", "userName : " + userName);
                            Log.d("MatchDialog : ", "userGender : " + userGender);
                            Log.d("MatchDialog : ", "userAge : " + userAge);

                            MatchFindUserDialog dialog2 = new MatchFindUserDialog(activity, userId, userName, userGender, userAge,adapter);
                            dialog2.showDialog();
                        }

                        @Override
                        public void onFailure(int error_code) {
                        }
                    });
                }
                dismiss();
            }
        });
    }
}