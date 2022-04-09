package com.designproject.takecare.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.designproject.takecare.R;
import java.util.ArrayList;
import java.util.List;

public class ShareFragment extends Fragment {
    List<String>name=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        name.add("홍길동");
        name.add("김말숙");
        name.add("이지은");
        name.add("박지수");
        name.add("나진우");

        View view=inflater.inflate(R.layout.fragment_share,container, false);

        RecyclerView listview=(RecyclerView)view.findViewById(R.id.share_listview);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),2);
        listview.setLayoutManager(gridLayoutManager);

        ShareGridAdapter adapter=new ShareGridAdapter(name);
        listview.setAdapter(adapter);
        return view;
    }
}
