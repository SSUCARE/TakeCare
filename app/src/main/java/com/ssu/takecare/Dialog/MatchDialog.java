package com.ssu.takecare.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.ssu.takecare.ApplicationClass;
import com.ssu.takecare.R;
import com.ssu.takecare.Retrofit.Match.DataResponseGetUser;
import com.ssu.takecare.Retrofit.Match.ResponseGetUser;
import com.ssu.takecare.Retrofit.RetrofitCallback;
import com.ssu.takecare.Retrofit.RetrofitCustomCallback.RetrofitUserInfoCallback;

public class MatchDialog {
    Integer userId;

    private EditText txt;
    private Button btn;
    private Activity activity;
    private Dialog dialog;

    public MatchDialog(Activity activity) {
        this.activity = activity;
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
    }

    private void findViews(){
        txt = dialog.findViewById(R.id.email_match);
        btn = dialog.findViewById(R.id.btn_ok_matching_email);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email= txt.getText().toString();

                if (email != null || !email.equals("")) {
                    ApplicationClass.retrofit_manager.getByEmailUserInfo(email, new RetrofitUserInfoCallback() {

                        @Override
                        public void onError(Throwable t) {
                        }

                        @Override
                        public void onSuccess(String message, DataResponseGetUser data) {
                            userId = data.getId();
                            Log.d("MatchDialog : ", "userId : " + userId.toString());
                            ApplicationClass.retrofit_manager.careRequest(userId, new RetrofitCallback() {

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
                        }

                        @Override
                        public void onFailure(int error_code) {
                        }
                    });
                }

                dialog.dismiss();
            }
        });
    }
}