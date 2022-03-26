package com.designproject.takecare;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FragmentB extends Fragment {
    List<String>name=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        name.add("A님의 건강체크"); name.add("B님의 건강체크"); name.add("C님의 건강체크");
        name.add("D님의 건강체크"); name.add("E님의 건강체크");
        View view=inflater.inflate(R.layout.tab2_fragment,container,false);

        RecyclerView listview=(RecyclerView)view.findViewById(R.id.share_listview);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),2);
        listview.setLayoutManager(gridLayoutManager);

        ShareGridAdapter adapter=new ShareGridAdapter(name);
        listview.setAdapter(adapter);
        return view;
    }
}
