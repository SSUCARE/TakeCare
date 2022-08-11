package com.ssu.takecare.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.database.DataSetObserver;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.ssu.takecare.ApplicationClass;
import com.ssu.takecare.assist.comment.Comment;
import com.ssu.takecare.assist.comment.CommentAdapter;
import com.ssu.takecare.R;
import com.ssu.takecare.retrofit.comment.DataGetComment;
import com.ssu.takecare.retrofit.report.DataGetReport;
import com.ssu.takecare.retrofit.RetrofitCallback;
import com.ssu.takecare.retrofit.customcallback.RetrofitCommentCallback;
import com.ssu.takecare.retrofit.customcallback.RetrofitCommentIdCallback;
import com.ssu.takecare.retrofit.customcallback.RetrofitReportCallback;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/*
해당 Activity는 Fragment Share, RoleCaredFragment, ShareGridAdapter 로부터는 Intent를 통해 USER_ID를 받아야하고,
CalendarActivity 에서는 'USER_ID','YEAR', 'MONTH', 'DAY' 를 Intent를 통해서 받아야한다.
*/
public class ReportActivity extends AppCompatActivity {

    Date currentTime = Calendar.getInstance().getTime();
    String date_year = new SimpleDateFormat("yyyy", Locale.getDefault()).format((currentTime));
    String date_month = new SimpleDateFormat("MM", Locale.getDefault()).format((currentTime));
    String date_day = new SimpleDateFormat("dd", Locale.getDefault()).format((currentTime));

    int find_year = Integer.parseInt(date_year);
    int find_month = Integer.parseInt(date_month);
    int find_day = Integer.parseInt(date_day);

    int userId;
    int reportId;

    CommentAdapter commentAdapter;

    TextView user_name, low_pressure, high_pressure, before_sugar, after_sugar, weight;

    HashMap<Integer, String> User_id_name;

    private boolean side = false;
    private ListView listView;
    private EditText comment;
    private Button buttonSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_report);

        userId = getIntent().getIntExtra("USER_ID",-1);
        User_id_name = (HashMap<Integer, String>) getIntent().getSerializableExtra("ID_NAME");

        user_name = findViewById(R.id.report_name);
        high_pressure = findViewById(R.id.high_pressure_report);
        low_pressure = findViewById(R.id.low_pressure_report);
        before_sugar = findViewById(R.id.before_sugar_report);
        after_sugar = findViewById(R.id.after_sugar_report);
        weight = findViewById(R.id.weight_report);

        buttonSend = (Button) findViewById(R.id.btn_send);
        listView = (ListView) findViewById(R.id.list_view);
        comment = (EditText) findViewById(R.id.chatText);

        commentAdapter = new CommentAdapter(getApplicationContext(), R.layout.activity_comment);
        listView.setAdapter(commentAdapter);

        if (userId == ApplicationClass.sharedPreferences.getInt("userId", 0)) {
            String my_name = ApplicationClass.sharedPreferences.getString("name", "");
            user_name.setText(my_name);
        }
        else {
            user_name.setText(User_id_name.get(userId));
        }

        // CalendarActivity 로부터 넘어옴
        int cal_year=getIntent().getIntExtra("YEAR",-1);
        int cal_month=getIntent().getIntExtra("MONTH",-1);
        int cal_day=getIntent().getIntExtra("DAY",-1);

        if (cal_year != -1 && cal_month != -1 && cal_day != -1) {
            Log.d("ReportActivity","바뀌기전,"+" find_year:"+find_year+" find_month:"+find_month+" find_day:"+find_day);
            find_year=cal_year; find_month=cal_month; find_day=cal_day;
            Log.d("ReportActivity","바뀐 후,"+" find_year:"+find_year+" find_month:"+find_month+" find_day:"+find_day);
        }

        Log.d("ReportActivity","find_year : " + find_year+", find_month : " + find_month + ", find_day : " + find_day);
        ApplicationClass.retrofit_manager.getReport(userId, find_year, find_month, find_day, new RetrofitReportCallback() {
            @Override
            public void onError(Throwable t) {
                Log.d("ReportActivity", "에러 : " + t);
            }

            @Override
            public void onSuccess(String message, List<DataGetReport> data) {
                if (data.size() != 0) {
                    String str_high_pressure, str_low_pressure, str_before_sugar, str_after_sugar, str_weight;

                    Log.d("ReportActivity", "data - CreatedAt : " + data.get(0).getCreatedAt());
                    Log.d("ReportActivity", "data - ReportId : " + data.get(0).getReportId());
                    Log.d("ReportActivity", "data - Systolic : " + data.get(0).getSystolic());
                    Log.d("ReportActivity", "data - Diastolic : " + data.get(0).getDiastolic());
                    Log.d("ReportActivity", "data - SugarLevels : " + data.get(0).getSugarLevels());
                    Log.d("ReportActivity", "data - Weight : " + data.get(0).getWeight());

                    reportId = data.get(0).getReportId();

                    str_high_pressure = data.get(0).getSystolic() + " mmHg";
                    str_low_pressure = data.get(0).getDiastolic() + " mmHg";

                    if (data.get(0).getSugarLevels().size() == 0) {
                        str_before_sugar = "____ mg/dL";
                        str_after_sugar = "____ mg/dL";
                    }
                    else if (data.get(0).getSugarLevels().size() == 1) {
                        str_before_sugar = data.get(0).getSugarLevels().get(0) + " mg/dL";
                        str_after_sugar = "____ mg/dL";
                    }
                    else {
                        str_before_sugar = data.get(0).getSugarLevels().get(0) + " mg/dL";
                        str_after_sugar = data.get(0).getSugarLevels().get(1) + " mg/dL";
                    }

                    str_weight = data.get(0).getWeight() + " kg";

                    low_pressure.setText(str_low_pressure);
                    high_pressure.setText(str_high_pressure);
                    before_sugar.setText(str_before_sugar);
                    after_sugar.setText(str_after_sugar);
                    weight.setText(str_weight);

                    ApplicationClass.retrofit_manager.getComment(reportId, new RetrofitCommentCallback() {
                        @Override
                        public void onError(Throwable t) {
                            Log.d("ReportActivity", "에러 : " + t);
                        }

                        @Override
                        public void onSuccess(String message, List<DataGetComment> data) {
                            if (data.size() != 0) {
                                Log.d("ReportActivity", "data - CreatedAt : " + data.get(0).getCreatedAt());
                                Log.d("ReportActivity", "data - ModifiedAt : " + data.get(0).getModifiedAt());
                                Log.d("ReportActivity", "data - ReportId : " + data.get(0).getReportId());
                                Log.d("ReportActivity", "data - AuthorId : " + data.get(0).getAuthorId());
                                Log.d("ReportActivity", "data - CommentId : " + data.get(0).getCommentId());
                                Log.d("ReportActivity", "data - Content : " + data.get(0).getContent());

                                for (int i = 0; i < data.size(); i++) {
                                    // 나 자신이 쓴 채팅 구별
                                    if (data.get(i).getAuthorId() == getIntPreference("userId")) {
                                        commentAdapter.add(new Comment(false, data.get(i).getContent()));
                                        commentAdapter.addAuthorName(getStringPreference("name"));
                                    }
                                    else {
                                        commentAdapter.add(new Comment(true, data.get(i).getContent()));

                                        if (User_id_name.containsKey(data.get(i).getAuthorId()))
                                            commentAdapter.addAuthorName(User_id_name.get(data.get(i).getAuthorId()));
                                        else {
                                            Log.d("ReportActivity", "해당하는 id(" + data.get(i).getAuthorId() + ")의 정보가 없습니다.");
                                            commentAdapter.addAuthorName("다른 보호자");
                                        }
                                    }

                                    commentAdapter.addCommentId(data.get(i).getCommentId());
                                }
                            }
                            else
                                Log.d("ReportActivity", "해당 리포트에 저장된 댓글이 없습니다.");
                        }

                        @Override
                        public void onFailure(int error_code) {
                            Log.d("ReportActivity", "실패 : " + error_code);
                        }
                    });
                }
                else
                    Log.d("ReportActivity", "해당 날짜로 생성된 리포트가 없습니다.");
            }

            @Override
            public void onFailure(int error_code) {
                Log.d("ReportActivity", "실패 : " + error_code);
            }
        });

        // enter 키를 누르면 메세지가 전송된다. (영문만 바로 보내지는 현상, 한글은 줄바꿈 현상)
        comment.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    return sendComment();
                }

                return false;
            }
        });

        // send 버튼을 누르면 메세지가 전송된다.
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                sendComment();
            }
        });

        // 메세지가 추가되면 listView에서 메시지를 스크롤할 수 있다.
        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        listView.setAdapter(commentAdapter);

        commentAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(commentAdapter.getCount() - 1);
            }
        });

        // 해당 메세지를 길게 누르면 메세지를 삭제할 수 있다
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String author = commentAdapter.getAuthorName(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(ReportActivity.this, R.style.MyDialogTheme);
                builder.setTitle("메세지 삭제");
                builder.setMessage("이 메시지를 삭제할까요?");

                builder.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        if (author.equals(ApplicationClass.sharedPreferences.getString("name", ""))) {
                            int delete_commentId = commentAdapter.getCommentId(position);

                            ApplicationClass.retrofit_manager.deleteComment(delete_commentId, new RetrofitCallback() {
                                @Override
                                public void onError(Throwable t) {
                                    Log.d("ReportActivity", "에러 : " + t);
                                }

                                @Override
                                public void onSuccess(String message, String data) {
                                    commentAdapter.remove(position);
                                    commentAdapter.notifyDataSetChanged(); // 새로고침
                                }

                                @Override
                                public void onFailure(int error_code) {
                                    Log.d("ReportActivity", "실패 : " + error_code);
                                }
                            });
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "자신이 쓴 메세지만 삭제할 수 있습니다", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.show();

                return false;
            }
        });
    }

    private boolean sendComment() {
        ApplicationClass.retrofit_manager.makeComment(comment.getText().toString(), reportId, new RetrofitCommentIdCallback() {
            @Override
            public void onError(Throwable t) {
                Log.d("ReportActivity", "에러 : " + t);
            }

            @Override
            public void onSuccess(String message, int commentId) {
                Log.d("ReportActivity", "message : " + message + ", commentId : " + commentId);

                commentAdapter.addAuthorName(getStringPreference("name"));
                commentAdapter.addCommentId(commentId);
                commentAdapter.add(new Comment(side, comment.getText().toString()));
                comment.setText("");
            }

            @Override
            public void onFailure(int error_code) {
                Log.d("ReportActivity", "실패 : " + error_code);
            }
        });

        return true;
    }

    //화면 터치 시 키보드 내려감
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View focusView = getCurrentFocus();
        if (focusView != null) {
            Rect rect = new Rect();
            focusView.getGlobalVisibleRect(rect);
            int x = (int) ev.getX(), y = (int) ev.getY();
            if (!rect.contains(x, y)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (imm != null)
                    imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
                focusView.clearFocus();
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public void back_btn_event(View view) {
        finish();
    }

    public int getIntPreference(String flag) {
        return ApplicationClass.sharedPreferences.getInt(flag, 0);
    }

    public String getStringPreference(String flag) {
        return ApplicationClass.sharedPreferences.getString(flag, "");
    }
}