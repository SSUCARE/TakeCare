package com.ssu.takecare.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.ssu.takecare.R;
import com.ssu.takecare.UI.MatchActivity;
import com.ssu.takecare.UI.ProfileActivity;

public class MyPageFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mypage, container, false);

        ConstraintLayout my_profile = (ConstraintLayout) view.findViewById(R.id.my_info);
        ImageView match_setting = (ImageView) view.findViewById(R.id.btn_match);

        my_profile.setOnClickListener(new View.OnClickListener() {
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

        return view;
    }
}
