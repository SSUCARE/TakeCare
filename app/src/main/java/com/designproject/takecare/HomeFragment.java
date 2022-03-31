package com.designproject.takecare;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class HomeFragment extends Fragment {

    Date currentTime = Calendar.getInstance().getTime();
    String date = new SimpleDateFormat("MM월 dd일 EE요일", Locale.getDefault()).format((currentTime));

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home, container, false);

        TextView today_date = (TextView) view.findViewById(R.id.tv_date);
        today_date.setText(date);

        return view;
    }
}
