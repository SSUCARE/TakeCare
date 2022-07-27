package com.ssu.takecare.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.ssu.takecare.ApplicationClass;
import com.ssu.takecare.R;
import com.ssu.takecare.UI.MatchActivity;
import com.ssu.takecare.UI.ProfileActivity;

public class MyPageFragment extends Fragment {

    TextView tv_name;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mypage, container, false);

        String my_name = ApplicationClass.sharedPreferences.getString("name", "");
        tv_name = view.findViewById(R.id.user_name);
        tv_name.setText(my_name);

        ConstraintLayout profile_setting = (ConstraintLayout) view.findViewById(R.id.layout_profile);
        ConstraintLayout match_setting = (ConstraintLayout) view.findViewById(R.id.layout_match);
        ConstraintLayout alarm_setting = (ConstraintLayout) view.findViewById(R.id.layout_alarm);
        ConstraintLayout share_setting = (ConstraintLayout) view.findViewById(R.id.layout_share);

        profile_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                startActivity(intent);
            }
        });

        match_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), MatchActivity.class);
                startActivity(intent);
            }
        });

        alarm_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        share_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }
}
