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
import com.ssu.takecare.UI.PrescriptionActivity;
import com.ssu.takecare.UI.ReportActivity;
import com.ssu.takecare.UI.ShareGraph;
import java.util.HashMap;
import java.util.List;

// 두번째 탭의 피보호자 모드
public class RoleCaredFragment extends Fragment implements View.OnClickListener {

    View view;
    TextView tv_name;
    Intent intent;

    List<String> Match_UserName_list;
    List<Integer> Match_UserId_list;
    HashMap<Integer, String> ID_NAME = new HashMap<Integer, String>();

    public RoleCaredFragment(List<String> UserName, List<Integer> UserId){
        this.Match_UserName_list = UserName;
        this.Match_UserId_list = UserId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_role_cared,container, false);

        String my_name = ApplicationClass.sharedPreferences.getString("name", "");
        tv_name = view.findViewById(R.id.protected_name);
        tv_name.setText(my_name);

        for (int i = 0; i < Match_UserId_list.size(); i++)
            ID_NAME.put(Match_UserId_list.get(i), Match_UserName_list.get(i));

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
                intent=new Intent(getActivity(), ShareGraph.class);
                intent.putExtra("USER_ID",ApplicationClass.sharedPreferences.getInt("userId",-1));
                startActivity(intent);
                break;
            case R.id.cared_my_calendar:
                intent=new Intent(getActivity(),CalendarActivity.class);
                intent.putExtra("USER_ID",ApplicationClass.sharedPreferences.getInt("userId",-1));
                intent.putExtra("USER_NAME",ApplicationClass.sharedPreferences.getString("name",""));
                intent.putExtra("ID_NAME", ID_NAME);
                startActivity(intent);
                break;
            case R.id.cared_my_presciption:
                intent=new Intent(getActivity(),PrescriptionActivity.class);
                intent.putExtra("USER_ID",ApplicationClass.sharedPreferences.getInt("userId",-1));
                intent.putExtra("USER_NAME",ApplicationClass.sharedPreferences.getString("name",""));
                startActivity(intent);
                break;
            case R.id.cared_my_report:
                Intent intent=new Intent(getActivity(), ReportActivity.class);
                intent.putExtra("USER_ID",ApplicationClass.sharedPreferences.getInt("userId",-1));
                intent.putExtra("ID_NAME", ID_NAME);
                startActivity(intent);
                break;
        }
    }
}
