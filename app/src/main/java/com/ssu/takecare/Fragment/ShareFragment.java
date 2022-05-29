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

    TextView tv_name;

    List<String> UserName;
    List<Integer> Id;

    public ShareFragment(List<String> UserName, List<Integer> Id){
        this.UserName = UserName;
        this.Id = Id;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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

        RecyclerView listview = view.findViewById(R.id.share_listview);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        listview.setLayoutManager(gridLayoutManager);

        ShareGridAdapter adapter = new ShareGridAdapter(UserName, getActivity());
        listview.setAdapter(adapter);

        return view;
    }
}
