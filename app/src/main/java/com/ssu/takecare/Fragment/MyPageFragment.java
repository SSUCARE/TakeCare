package com.ssu.takecare.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.ssu.takecare.Runnable.ShareRunnable;
import com.ssu.takecare.UI.MatchActivity;
import com.ssu.takecare.UI.PasswordActivity;
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
        ConstraintLayout password_setting = (ConstraintLayout) view.findViewById(R.id.layout_password);
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

        password_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PasswordActivity.class);
                startActivity(intent);
            }
        });

        match_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MatchActivity.class);
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
                int userId = ApplicationClass.sharedPreferences.getInt("userId", 0);
                if (userId != 0) {
                    Date currentTime = Calendar.getInstance().getTime();
                    int date_year = Integer.parseInt(new SimpleDateFormat("yyyy", Locale.getDefault()).format((currentTime)));
                    int date_month = Integer.parseInt(new SimpleDateFormat("MM", Locale.getDefault()).format((currentTime)));
                    List<Integer> list_year = new ArrayList<>(); List<Integer> list_month = new ArrayList<>();
                    int count=0;

                    //최근 3달의 년도와 월을 저장
                    for (int i = 2; i > 0; i--) {
                        if (date_month - i == -1) { //작년 11월로.
                            list_year.add(date_year - 1);
                            list_month.add(11);
                        } else if (date_month - i == 0) { //작년 12월로.
                            list_year.add(date_year - 1);
                            list_month.add(12);
                        } else {
                            list_year.add(date_year);
                            list_month.add(date_month - i);
                        }
                    }
                    list_year.add(date_year);
                    list_month.add(date_month);

                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("DATE" + "," + "SYS" + "," + "DIA" + "," + "B_SUGAR" + "," + "WEIGHT" + "\n");

                    Handler handler3 = new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            try {
                                String filename = getActivity().getFilesDir().getAbsolutePath() + "/TakeCareRecord_3Months.csv";
                                File file = new File(filename);
                                file.createNewFile();
                                PrintWriter writer = new PrintWriter(file);
                                writer.write(stringBuilder.toString());
                                writer.close();

                                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                                shareIntent.setType("application/excel");    // 엑셀파일 공유 시
                                Uri contentUri = FileProvider.getUriForFile(getActivity().getApplicationContext(), getActivity().getApplicationContext().getPackageName() + ".fileprovider", file);
                                shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
                                startActivity(Intent.createChooser(shareIntent, "엑셀 공유"));
                            } catch (Exception e) {

                            }
                        }
                    };

                    Handler handler2 = new Handler() {
                        @Override
                        public void handleMessage(Message msg) {Log.d(TAG,"2");
                            Thread thread = new Thread(new ShareRunnable(stringBuilder, userId, list_year.get(count+2), list_month.get(count+2), handler3));
                            thread.start();
                        }
                    };

                    Handler handler1 = new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            Thread thread = new Thread(new ShareRunnable(stringBuilder, userId, list_year.get(count+1), list_month.get(count+1), handler2));
                            thread.start();
                        }
                    };

                    Thread thread = new Thread(new ShareRunnable(stringBuilder, userId, list_year.get(count), list_month.get(count), handler1));
                    thread.start();
                }else{
                    Toast.makeText(getActivity().getApplicationContext(),"기능을 사욯할 수 없습니다. 다시 로그인 해주세요!",Toast.LENGTH_SHORT);
                }
            }
        });


        return view;
    }
}

