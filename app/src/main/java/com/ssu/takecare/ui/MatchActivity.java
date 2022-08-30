package com.ssu.takecare.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.ssu.takecare.ApplicationClass;
import com.ssu.takecare.assist.match.ListView2MatchAdapter;
import com.ssu.takecare.assist.match.ListViewMatchAdapter;
import com.ssu.takecare.dialog.MatchDialog;
import com.ssu.takecare.R;
import com.ssu.takecare.retrofit.customcallback.RetrofitCareCallback;
import com.ssu.takecare.retrofit.match.ResponseCare;
import java.util.HashMap;
import java.util.Map;

public class MatchActivity extends AppCompatActivity {
    private ListView mListview;
    private ListView mListview2;

    Map<String, Integer> mArrData;
    Map<String, Integer> mArrData2;

    private MatchDialog dialog;
    private ListViewMatchAdapter mAdapter_accepted;
    private ListViewMatchAdapter mAdapter_pending;
    private ListView2MatchAdapter mAdapter_argree_or_refuse;
    private Button plus;

    private String role;
    private final String TAG="MatchActivity_debug";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        init();
        setting();

        ApplicationClass.retrofit_manager.getCareDBMatchInfo(new RetrofitCareCallback() {
            @Override
            public void onError(Throwable t) {

            }

            @Override // status = PENDING, ACCEPTED 두개뿐.
            public void onSuccess(String message, ResponseCare data) {
                int size=data.getData().size();
                if (role.equals("보호자")) {
                    for (int i = 0; i < size; i++) {
                        if (data.getData().get(i).getStatus().equals("PENDING")) {
                            mArrData.put(data.getData().get(i).getUserName(), data.getData().get(i).getId());
                           // Log.d(TAG,"id:"+data.getData().get(i).getId());
                        }
                        else if (data.getData().get(i).getStatus().equals("ACCEPTED")) {
                            mArrData2.put(data.getData().get(i).getUserName(), data.getData().get(i).getId());
                        }
                    }
                    mAdapter_pending.notifyDataSetChanged();
                }
                else{//피보호자
                    for (int i = 0; i < size; i++) {
                        if (data.getData().get(i).getStatus().equals("PENDING")) {
                            mArrData.put(data.getData().get(i).getUserName(), data.getData().get(i).getId());
                        } else if (data.getData().get(i).getStatus().equals("ACCEPTED")) {
                            mArrData2.put(data.getData().get(i).getUserName(), data.getData().get(i).getId());
                        }
                    }
                    mAdapter_argree_or_refuse.notifyDataSetChanged();
                }
                mAdapter_accepted.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int error_code) {
            }
        });
    }

    public void back_btn_event(View view) {
        finish();
    }

    public void init(){
        mListview2 = (ListView) findViewById(R.id.list_match_connected);
        mListview = (ListView) findViewById(R.id.list_match_waiting);
        plus = findViewById(R.id.plus_icon_button);

        mArrData = new HashMap<String, Integer>();
        mArrData2 = new HashMap<String, Integer>();

        role = ApplicationClass.sharedPreferences.getString("role", "");
    }

    public void setting(){
        if (role.equals("피보호자")){
            mAdapter_accepted = new ListViewMatchAdapter(MatchActivity.this, mArrData2, "ACCEPTED"); // 삭제
            mAdapter_argree_or_refuse = new ListView2MatchAdapter(MatchActivity.this, mArrData, "PENDING",mAdapter_accepted);
            mListview2.setAdapter(mAdapter_accepted);
            mListview.setAdapter(mAdapter_argree_or_refuse);

            plus.setVisibility(View.GONE);
        }else { //보호자
            mAdapter_accepted = new ListViewMatchAdapter(MatchActivity.this, mArrData2, "ACCEPTED");
            mAdapter_pending = new ListViewMatchAdapter(MatchActivity.this, mArrData, "PENDING");
            mListview2.setAdapter(mAdapter_accepted);
            mListview.setAdapter(mAdapter_pending);

            plus.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dialog = new MatchDialog(MatchActivity.this, mAdapter_pending, mArrData, mArrData2);
                    dialog.showDialog();
                }
            });
        }
    }
}
