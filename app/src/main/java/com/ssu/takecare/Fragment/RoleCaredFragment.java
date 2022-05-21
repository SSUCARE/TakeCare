package com.ssu.takecare.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.ssu.takecare.R;
import com.ssu.takecare.UI.CalendarActivity;
import com.ssu.takecare.UI.PresciptionNoteActivity;
import com.ssu.takecare.UI.ReportActivity;
import com.ssu.takecare.UI.ShareGraph;

public class RoleCaredFragment extends Fragment implements View.OnClickListener {

    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_role_cared,container, false);

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
                startActivity(new Intent(getActivity(), PresciptionNoteActivity.class));
                break;
            case R.id.cared_my_report:
                startActivity(new Intent(getActivity(), ReportActivity.class));
                break;
        }
    }
}
