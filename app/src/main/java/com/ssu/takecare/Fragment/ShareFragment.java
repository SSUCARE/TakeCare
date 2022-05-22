package com.ssu.takecare.Fragment;

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
import com.ssu.takecare.R;
import com.ssu.takecare.UI.CalendarActivity;
import com.ssu.takecare.UI.PresciptionActivity;
import com.ssu.takecare.UI.ReportActivity;
import com.ssu.takecare.UI.ShareGraph;
import java.util.ArrayList;
import java.util.List;

public class ShareFragment extends Fragment {

    List<String> name = new ArrayList<>();
    TextView tv_name;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        name.add("홍길동");
        name.add("김말숙");
        name.add("이지은");
        name.add("박지수");
        name.add("나진우");

        View view=inflater.inflate(R.layout.fragment_share,container, false);

        String my_name = ApplicationClass.sharedPreferences.getString("name", "");
        tv_name = view.findViewById(R.id.share_my_name);
        tv_name.setText(my_name);

        ImageButton btn1=(ImageButton) view.findViewById(R.id.share_my_graph);
        ImageButton btn2=(ImageButton) view.findViewById(R.id.share_my_calendar);
        ImageButton btn3=(ImageButton) view.findViewById(R.id.share_my_presciption);
        ImageButton btn4=(ImageButton) view.findViewById(R.id.share_my_report);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(view.getContext(), ShareGraph.class));
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(view.getContext(), CalendarActivity.class));
            }
        });

        btn3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(view.getContext(), PresciptionActivity.class));
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(view.getContext(), ReportActivity.class));
            }
        });

        RecyclerView listview=(RecyclerView)view.findViewById(R.id.share_listview);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        listview.setLayoutManager(gridLayoutManager);

        ShareGridAdapter adapter=new ShareGridAdapter(name, getActivity());
        listview.setAdapter(adapter);
        return view;
    }
}
