package com.ssu.takecare.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.ssu.takecare.ApplicationClass;
import com.ssu.takecare.assist.share.ShareGridAdapter;
import com.ssu.takecare.R;
import com.ssu.takecare.ui.CalendarActivity;
import com.ssu.takecare.ui.PrescriptionActivity;
import com.ssu.takecare.ui.ReportActivity;
import com.ssu.takecare.assist.graph.ShareGraph;
import java.util.HashMap;
import java.util.List;

public class CaringShareFragment extends Fragment {

    Intent intent;
    TextView tv_name;
    List<String> Match_UserName_list;
    List<Integer> Match_UserId_list;
    HashMap<Integer, String> ID_NAME = new HashMap<Integer, String>();

    public CaringShareFragment(List<String> UserName, List<Integer> UserId){
        this.Match_UserName_list = UserName;
        this.Match_UserId_list = UserId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_share_caring,container, false);

        String my_name = ApplicationClass.sharedPreferences.getString("name", "");
        tv_name = view.findViewById(R.id.share_my_name);
        tv_name.setText(my_name);

        for (int i = 0; i < Match_UserId_list.size(); i++)
            ID_NAME.put(Match_UserId_list.get(i), Match_UserName_list.get(i));

        ImageButton btn1=(ImageButton) view.findViewById(R.id.share_my_graph);
        ImageButton btn2=(ImageButton) view.findViewById(R.id.share_my_calendar);
        ImageButton btn3=(ImageButton) view.findViewById(R.id.share_my_presciption);
        ImageButton btn4=(ImageButton) view.findViewById(R.id.share_my_report);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent=new Intent(getActivity(), ShareGraph.class);
                intent.putExtra("USER_ID",ApplicationClass.sharedPreferences.getInt("userId",-1));
                startActivity(intent);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent=new Intent(getActivity(),CalendarActivity.class);
                intent.putExtra("USER_ID",ApplicationClass.sharedPreferences.getInt("userId",-1));
                intent.putExtra("USER_NAME",ApplicationClass.sharedPreferences.getString("name",""));
                intent.putExtra("ID_NAME", ID_NAME);
                startActivity(intent);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                intent=new Intent(getActivity(),PrescriptionActivity.class);
                intent.putExtra("USER_ID",ApplicationClass.sharedPreferences.getInt("userId",-1));
                intent.putExtra("USER_NAME",ApplicationClass.sharedPreferences.getString("name",""));
                startActivity(intent);
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent=new Intent(getActivity(), ReportActivity.class);
                intent.putExtra("USER_ID", ApplicationClass.sharedPreferences.getInt("userId",-1));
                intent.putExtra("ID_NAME", ID_NAME);
                startActivity(intent);
            }
        });

        RecyclerView listview = view.findViewById(R.id.share_listview);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        listview.setLayoutManager(gridLayoutManager);

        ShareGridAdapter adapter = new ShareGridAdapter(Match_UserName_list, Match_UserId_list, getActivity());
        listview.setAdapter(adapter);

        return view;
    }
}
