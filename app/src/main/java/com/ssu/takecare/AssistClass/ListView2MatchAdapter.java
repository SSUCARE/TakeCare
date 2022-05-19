package com.ssu.takecare.AssistClass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.ssu.takecare.ApplicationClass;
import com.ssu.takecare.R;
import com.ssu.takecare.Retrofit.RetrofitCallback;
import java.util.Map;

// 케어받는 사람 화면(CARED)
public class ListView2MatchAdapter extends BaseAdapter {

    private Context mContext;
    private Map<String, Integer> arrData;
    private String acceptedOrNot;
    private Integer userId;

    public ListView2MatchAdapter(Context context, Map<String, Integer> arrData, String acceptedOrNot) {
        super();
        this.mContext = context;
        this.arrData = arrData;
        this.acceptedOrNot=acceptedOrNot;
    }

    public int getCount() {
        // return the number of records
        return arrData.size();
    }


    public Integer getElementByIndex(Map<String, Integer> map, int index){
        return map.get( (map.keySet().toArray())[index] );
    }

    public String getKeyByIndex(Map<String, Integer> map,int index){
        return (String) map.keySet().toArray()[index];
    }

    public Integer getUserId() {
        return this.userId;
    }
    // getView method is called for each item of ListView
    public View getView(int position, View view, ViewGroup parent) {
        // inflate the layout for each item of listView
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.activity_listview2, parent, false);


        // get the reference of textView and button
        TextView txtlistTitle = (TextView) view.findViewById(R.id.txtlistview2);
        Button btnAction = (Button) view.findViewById(R.id.btnAction1);

        Button btnAction2 = (Button) view.findViewById(R.id.btnAction2);


        String userName= getKeyByIndex(arrData, position);
        userId = getElementByIndex(arrData, position);

        // Set the title and button name
        txtlistTitle.setText(userName);

        // Click listener of button
        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 수락
                ApplicationClass.retrofit_manager.careAcceptRequest(userId, new RetrofitCallback() {

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
        });
        // Click listener of button
        btnAction2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 거절
                ApplicationClass.retrofit_manager.careDeleteRequest(userId, new RetrofitCallback() {

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
        });


        return view;
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }
}