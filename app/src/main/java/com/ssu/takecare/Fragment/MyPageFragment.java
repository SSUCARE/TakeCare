package com.ssu.takecare.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.ssu.takecare.ApplicationClass;
import com.ssu.takecare.R;
import com.ssu.takecare.Retrofit.GetReport.DataGetReport;
import com.ssu.takecare.Retrofit.RetrofitCustomCallback.RetrofitReportCallback;
import com.ssu.takecare.UI.MatchActivity;
import com.ssu.takecare.UI.ProfileActivity;

import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

public class MyPageFragment extends Fragment {
    private final String TAG="MyPageFragment,Deburg";
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
                int userId=ApplicationClass.sharedPreferences.getInt("userId", 0);
                if(userId!=0){
                    Date currentTime = Calendar.getInstance().getTime();
                    int date_year =Integer.parseInt(new SimpleDateFormat("yyyy", Locale.getDefault()).format((currentTime)));
                    int date_month=Integer.parseInt(new SimpleDateFormat("MM", Locale.getDefault()).format((currentTime)));

                    List<Integer> list_year=new ArrayList<>();
                    List<Integer> list_month=new ArrayList<>();

                    //최근 3달의 년도와 월을 저장
                    for(int i=2; i>0; i--){
                        if(date_month-i==-1){ //작년 11월로.
                            list_year.add(date_year-1);
                            list_month.add(11);
                        }else if(date_month-i==0){ //작년 12월로.
                            list_year.add(date_year-1);
                            list_month.add(12);
                        }else{
                            list_year.add(date_year);
                            list_month.add(date_month-i);
                        }
                    }
                    list_year.add(date_year);
                    list_month.add(date_month);
                    //DATE SYS	DIA	 B_SUGAR WEIGHT 형태로
                    StringBuilder stringBuilder=new StringBuilder();
                    stringBuilder.append("DATE"+","+"SYS"+","+"DIA"+","+"B_SUGAR"+","+"WEIGHT"+"\n");
                    ApplicationClass.retrofit_manager.getReport_Month(userId,list_year.get(0),list_month.get(0), new RetrofitReportCallback() {
                        @Override
                        public void onError(Throwable t) {
                        }

                        @Override
                        public void onSuccess(String message, List<DataGetReport> data) {
                            for (int i = 0; i < data.size(); i++) {
                                stringBuilder.append(data.get(i).getCreatedAt().split("T")[0] + ",");
                                stringBuilder.append(data.get(i).systolic + ",");
                                stringBuilder.append(data.get(i).getDiastolic() + ",");
                                stringBuilder.append(new String(data.get(i).getSugarLevels() + "").replace(",", "/") + ",");
                                stringBuilder.append(data.get(i).getWeight() + "\n");
                                Log.d(TAG,data.get(i).getCreatedAt());
                            }
                            ApplicationClass.retrofit_manager.getReport_Month(userId,list_year.get(1),list_month.get(1), new RetrofitReportCallback() {
                                @Override
                                public void onError(Throwable t) {
                                }

                                @Override
                                public void onSuccess(String message, List<DataGetReport> data) {
                                    for (int i = 0; i < data.size(); i++) {
                                        stringBuilder.append(data.get(i).getCreatedAt().split("T")[0] + ",");
                                        stringBuilder.append(data.get(i).systolic + ",");
                                        stringBuilder.append(data.get(i).getDiastolic() + ",");
                                        stringBuilder.append(new String(data.get(i).getSugarLevels() + "").replace(",", "/") + ",");
                                        stringBuilder.append(data.get(i).getWeight() + "\n");
                                        Log.d(TAG,data.get(i).getCreatedAt());
                                    }
                                    ApplicationClass.retrofit_manager.getReport_Month(userId,list_year.get(2),list_month.get(2), new RetrofitReportCallback() {
                                        @Override
                                        public void onError(Throwable t) {
                                        }

                                        @Override
                                        public void onSuccess(String message, List<DataGetReport> data) {
                                            for (int i = 0; i < data.size(); i++) {
                                                stringBuilder.append(data.get(i).getCreatedAt().split("T")[0] + ",");
                                                stringBuilder.append(data.get(i).systolic + ",");
                                                stringBuilder.append(data.get(i).getDiastolic() + ",");
                                                stringBuilder.append(new String(data.get(i).getSugarLevels() + "").replace(",", "/") + ",");
                                                stringBuilder.append(data.get(i).getWeight() + "\n");
                                                Log.d(TAG,data.get(i).getCreatedAt());
                                            }
                                            try{
                                                String filename=getActivity().getFilesDir().getAbsolutePath()+"/TakeCareRecord_3Months.csv";
                                                File file=new File(filename);
                                                file.createNewFile();
                                                PrintWriter writer = new PrintWriter(file);
                                                writer.write(stringBuilder.toString());
                                                writer.close();

                                                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                                                shareIntent.setType("application/excel");    // 엑셀파일 공유 시
                                                Uri contentUri = FileProvider.getUriForFile(getActivity().getApplicationContext(), getActivity().getApplicationContext().getPackageName() + ".fileprovider", file);
                                                shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
                                                startActivity(Intent.createChooser(shareIntent,"엑셀 공유"));
                                            }catch (Exception e){

                                            }
                                        }
                                        @Override
                                        public void onFailure(int error_code) {
                                        }
                                    });
                                }
                                @Override
                                public void onFailure(int error_code) {
                                }
                            });
                        }
                        @Override
                        public void onFailure(int error_code) {
                        }
                    });

                }
                else{
                    Log.d(TAG,"4");
                }
            }
        });

        return view;
    }
}