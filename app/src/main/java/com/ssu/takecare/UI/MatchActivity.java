package com.ssu.takecare.UI;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.ssu.takecare.ApplicationClass;
import com.ssu.takecare.AssistClass.Match.ListView2MatchAdapter;
import com.ssu.takecare.AssistClass.Match.ListView1MatchAdapter;
import com.ssu.takecare.Dialog.MatchDialog;
import com.ssu.takecare.R;
import com.ssu.takecare.Retrofit.RetrofitCustomCallback.RetrofitCareCallback;
import com.ssu.takecare.Retrofit.Match.ResponseCare;
import java.util.HashMap;
import java.util.Map;

public class MatchActivity extends AppCompatActivity {
    private ListView mListview;
    private ListView mListview2;

    Map<String, Integer> mArrData;
    Map<String, Integer> mArrData2;

    private MatchDialog dialog;
    private ListView1MatchAdapter mAdapter;
    private ListView2MatchAdapter mAdapter2;

    private String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        mArrData = new HashMap<String, Integer>();
        mArrData2 = new HashMap<String, Integer>();

        role = ApplicationClass.sharedPreferences.getString("role", "");

        Button plus = findViewById(R.id.plus_icon_button);

        if (role.equals("피보호자"))
            plus.setVisibility(View.GONE);
        else {
            plus.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dialog = new MatchDialog(MatchActivity.this);
                    dialog.showDialog();
                }
            });
        }

        ApplicationClass.retrofit_manager.getCareDBMatchInfo(new RetrofitCareCallback() {
            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onSuccess(String message, ResponseCare data) {
                int size = data.getData().size();
                for (int i = 0; i < size; i++) {
                    // statue= PENDING, ACCEPTED
                    // user name
                    if (data.getData().get(i).getStatus().equals("PENDING")) {
                        mArrData.put(data.getData().get(i).getUserName(), data.getData().get(i).getId());
                    }
                    else if (data.getData().get(i).getStatus().equals("ACCEPTED")) {
                        mArrData2.put(data.getData().get(i).getUserName(), data.getData().get(i).getId());
                    }
                }

                mListview = (ListView) findViewById(R.id.list_match_waiting);
                mListview2 = (ListView) findViewById(R.id.list_match_connected);

                if (role.equals("보호자")) {
                    mAdapter = new ListView1MatchAdapter(MatchActivity.this, mArrData, "PENDING"); // 취소
                    mListview.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();

                    mAdapter = new ListView1MatchAdapter(MatchActivity.this, mArrData2, "ACCEPTED"); // 삭제
                    mListview2.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
                else if (role.equals("피보호자")) {
                    mAdapter2 = new ListView2MatchAdapter(MatchActivity.this, mArrData, "PENDING");
                    mListview.setAdapter(mAdapter2);
                    mAdapter2.notifyDataSetChanged();

                    mAdapter = new ListView1MatchAdapter(MatchActivity.this, mArrData2, "ACCEPTED");
                    mListview2.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(int error_code) {
            }
        });
    }

    public void back_btn_event(View view) {
        finish();
    }
}
