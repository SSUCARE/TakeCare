package com.ssu.takecare.UI;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.ssu.takecare.ApplicationClass;
import com.ssu.takecare.AssistClass.ListView2MatchAdapter;
import com.ssu.takecare.AssistClass.ListViewMatchAdapter;
import com.ssu.takecare.R;
import com.ssu.takecare.Retrofit.CareListCallback;
import com.ssu.takecare.Retrofit.InfoCheck.ResponseInfoCheck;
import com.ssu.takecare.Retrofit.Match.ResponseCare;
import com.ssu.takecare.Retrofit.RetrofitCallback;
import com.ssu.takecare.Retrofit.UserInfoCallback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MatchActivity extends AppCompatActivity {
    private ListView mListview;
    private ListView mListview2;

    Map<String, Integer> mArrData;
    Map<String, Integer> mArrData2;

    private ListViewMatchAdapter mAdapter;
    private ListView2MatchAdapter mAdapter2;

    private String role;
    private Integer userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        mArrData = new HashMap<String, Integer>();
        mArrData2 = new HashMap<String, Integer>();

        //get login user data (role data)
        ApplicationClass.retrofit_manager.infoCheck(new UserInfoCallback() {
            @Override
            public void onError(Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(String message, ResponseInfoCheck data) {
                role = data.getDataInfocheck().getRole();
            }
            @Override
            public void onFailure(int error_code) {
                Toast.makeText(getApplicationContext(), "error code : " + error_code, Toast.LENGTH_SHORT).show();
            }
        });

        ApplicationClass.retrofit_manager.getCareMatchInfo(new CareListCallback() {
            @Override
            public void onError(Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(String message, List<ResponseCare> data) {
                if (role.equals("CARER")) {

                    int size= data.size();
                    for(int i=0;i<size; i++) {
                        //status = PENDING, ACCEPTED
                        //user name
                        if(data.get(i).getData().getStatus().equals("PENDING")) {
                            mArrData.put(data.get(i).getData().getUserName(), data.get(i).getData().getId());
                        }
                        else if(data.get(i).getData().getStatus().equals("ACCEPTED")) {
                            mArrData2.put(data.get(i).getData().getUserName(), data.get(i).getData().getId());
                        }
                    }


                    mListview = (ListView) findViewById(R.id.list_match_connected);
                    mAdapter = new ListViewMatchAdapter(MatchActivity.this, mArrData, "PENDING");
                    mListview.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();

                    mListview2 = (ListView) findViewById(R.id.list_match_waiting);
                    mAdapter = new ListViewMatchAdapter(MatchActivity.this, mArrData2, "ACCEPTED");
                    mListview2.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
                else if(role.equals("CARED")) {

                    int size= data.size();
                    for(int i=0;i<size; i++) {
                        //statue= PENDING, ACCEPTED
                        //user name
                        if(data.get(i).getData().getStatus().equals("PENDING")) {
                            mArrData.put(data.get(i).getData().getUserName(), data.get(i).getData().getId());
                        }
                        else if(data.get(i).getData().getStatus().equals("ACCEPTED")) {
                            mArrData2.put(data.get(i).getData().getUserName(), data.get(i).getData().getId());
                        }
                    }

                    mListview = (ListView) findViewById(R.id.list_match_connected);
                    mAdapter2 = new ListView2MatchAdapter(MatchActivity.this, mArrData, "PENDING");
                    mListview.setAdapter(mAdapter2);
                    mAdapter2.notifyDataSetChanged();

                    mListview2 = (ListView) findViewById(R.id.list_match_waiting);
                    mAdapter = new ListViewMatchAdapter(MatchActivity.this, mArrData2, "ACCEPTED");
                    mListview2.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(int error_code) {
                Toast.makeText(getApplicationContext(), "error code : " + error_code, Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void back_btn_event(View view) {
        finish();
    }
}
