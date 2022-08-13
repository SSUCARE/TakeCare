package com.ssu.takecare.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.ssu.takecare.assist.viewpager.CardNewsAdapter;
import com.ssu.takecare.R;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class HomeFragment extends Fragment {

    Date currentTime = Calendar.getInstance().getTime();
    String date = new SimpleDateFormat("M월 d일 E요일", Locale.getDefault()).format((currentTime));

    Boolean REPORT_FLAG;

    public HomeFragment(Boolean REPORT_FLAG){
        this.REPORT_FLAG = REPORT_FLAG;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home, container, false);

        TextView today_date = view.findViewById(R.id.tv_date);
        today_date.setText(date);

        if (REPORT_FLAG) {
            Button btn_rp = (Button) view.findViewById(R.id.btn_report);
            btn_rp.setText("레포트 수정");
        }

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        CardNewsAdapter adapter = new CardNewsAdapter(getActivity());
        viewPager.setAdapter(adapter);

        return view;
    }
}