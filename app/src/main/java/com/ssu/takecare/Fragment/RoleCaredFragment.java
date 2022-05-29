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
import com.ssu.takecare.ApplicationClass;
import com.ssu.takecare.R;
import com.ssu.takecare.UI.CalendarActivity;
import com.ssu.takecare.UI.PresciptionActivity;
import com.ssu.takecare.UI.ReportActivity;
import com.ssu.takecare.UI.ShareGraph;

// 두번째 탭의 피보호자 모드
public class RoleCaredFragment extends Fragment implements View.OnClickListener {

    View view;
    TextView tv_name;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_role_cared,container, false);

        String my_name = ApplicationClass.sharedPreferences.getString("name", "");
        tv_name = view.findViewById(R.id.protected_name);
        tv_name.setText(my_name);

        view.findViewById(R.id.cared_my_graph).setOnClickListener(this);
        view.findViewById(R.id.cared_my_calendar).setOnClickListener(this);
        view.findViewById(R.id.cared_my_presciption).setOnClickListener(this);
        view.findViewById(R.id.cared_my_report).setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cared_my_graph:
                startActivity(new Intent(getActivity(), ShareGraph.class));
                break;
            case R.id.cared_my_calendar:
                startActivity(new Intent(getActivity(), CalendarActivity.class));
                break;
            case R.id.cared_my_presciption:
                startActivity(new Intent(getActivity(), PresciptionActivity.class));
                break;
            case R.id.cared_my_report:
                startActivity(new Intent(getActivity(), ReportActivity.class));
                break;
        }
    }
}
