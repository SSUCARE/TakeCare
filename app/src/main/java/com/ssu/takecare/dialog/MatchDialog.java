package com.ssu.takecare.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ssu.takecare.ApplicationClass;
import com.ssu.takecare.assist.match.ListViewMatchAdapter;
import com.ssu.takecare.R;
import com.ssu.takecare.retrofit.match.DataResponseGetUser;
import com.ssu.takecare.retrofit.customcallback.RetrofitUserInfoCallback;
import java.util.Map;

public class MatchDialog {

    private Integer userId;
    private String userName;
    private String userGender;
    private Integer userAge;
    private EditText txt;
    private Button btn;
    private Dialog dialog;
    private Activity activity;
    private ListViewMatchAdapter adapter;
    private Map<String, Integer> pendingList, acceptedList;

    public MatchDialog(Activity activity, ListViewMatchAdapter adapter, Map<String, Integer> pendingList, Map<String, Integer> acceptedList) {
        this.activity = activity;
        this.adapter = adapter;
        this.pendingList = pendingList;
        this.acceptedList = acceptedList;
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
                            int flag = 0;

                            if (pendingList.containsKey(data.getName()))
                                flag = 1;

                            if (acceptedList.containsKey(data.getName()))
                                flag = 2;

                            switch (flag) {
                                case 0:
                                    userId = data.getId();
                                    userName = data.getName();
                                    userGender = data.getGender();
                                    userAge = data.getAge();

                                    MatchFindUserDialog dialog2 = new MatchFindUserDialog(activity, userId, userName, userGender, userAge, adapter);
                                    dialog2.showDialog();
                                    break;
                                case 1:
                                    Toast.makeText(activity.getApplicationContext(), "이미 요청을 보낸 사용자입니다", Toast.LENGTH_SHORT).show();
                                    break;
                                case 2:
                                    Toast.makeText(activity.getApplicationContext(), "이미 연결된 사용자입니다", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    break;
                            }
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