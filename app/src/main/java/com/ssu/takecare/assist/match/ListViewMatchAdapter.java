package com.ssu.takecare.assist.match;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.ssu.takecare.ApplicationClass;
import com.ssu.takecare.R;
import com.ssu.takecare.retrofit.RetrofitCallback;
import java.util.Map;

// 케어해주는 사람 화면(CARER)
public class ListViewMatchAdapter extends BaseAdapter {

    private Context mContext;
    private Map<String, Integer> arrData;
    private String acceptedOrNot;
    private Integer userId;

    public ListViewMatchAdapter(Context context, Map<String, Integer> arrData, String acceptedOrNot) {
        super();
        this.mContext = context;
        this.arrData = arrData;
        this.acceptedOrNot = acceptedOrNot;
    }

    public int getCount() {
        // return the number of records
        return arrData.size();
    }

    public void addItem(String UserName,int id){
        arrData.put(UserName,id);
    }

    public Integer getElementByIndex(Map<String, Integer> map, int index){
        return map.get( (map.keySet().toArray())[index] );
    }

    public String getKeyByIndex(Map<String, Integer> map, int index){
        return (String) map.keySet().toArray()[index];
    }

    public Integer getUserId() {
        return this.userId;
    }

    // getView method is called for each item of ListView
    public View getView(int position, View view, ViewGroup parent) {

        // inflate the layout for each item of listView
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.activity_listview1, parent, false);

        // get the reference of textView and button
        TextView txtListTitle = (TextView) view.findViewById(R.id.txtlistview);
        Button btnAction = (Button) view.findViewById(R.id.btnAction);

        String userName= getKeyByIndex(arrData, position);
        userId = getElementByIndex(arrData, position);

        txtListTitle.setText(userName);

        if (acceptedOrNot.equals("PENDING")) {
            btnAction.setText("취소");
        }
        else if (acceptedOrNot.equals("ACCEPTED")) {
            btnAction.setText("삭제");
        }

        // 취소 & 삭제
        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int list_userid=getElementByIndex(arrData, position);
                ApplicationClass.retrofit_manager.careDeleteRequest(list_userid, new RetrofitCallback() {
                    @Override
                    public void onError(Throwable t) {
                    }

                    @Override
                    public void onSuccess(String message, String data) {
                        if (acceptedOrNot.equals("PENDING")) {
                            Toast.makeText(v.getContext(), "취소 완료", Toast.LENGTH_SHORT).show();
                        }
                        else if (acceptedOrNot.equals("ACCEPTED")) {
                            Toast.makeText(v.getContext(), "삭제 완료", Toast.LENGTH_SHORT).show();
                        }

                        for (String key : arrData.keySet()) {
                            int value = arrData.get(key);
                            if (value == list_userid) {
                                arrData.remove(key);
                                break;
                            }
                        }
                        notifyDataSetChanged();
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
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
}