package com.ssu.takecare.UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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
import com.ssu.takecare.Retrofit.Comment.Comment;
import com.ssu.takecare.Retrofit.Comment.CommentAdapter;
import com.ssu.takecare.R;
import com.ssu.takecare.Retrofit.Comment.DataGetComment;
import com.ssu.takecare.Retrofit.GetReport.DataGetReport;
import com.ssu.takecare.Retrofit.RetrofitCallback;
import com.ssu.takecare.Retrofit.RetrofitCustomCallback.RetrofitCommentCallback;
import com.ssu.takecare.Retrofit.RetrofitCustomCallback.RetrofitReportCallback;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ReportActivity extends AppCompatActivity {

    SharedPreferences.Editor editor = ApplicationClass.sharedPreferences.edit();

    Date currentTime = Calendar.getInstance().getTime();
    String date_year = new SimpleDateFormat("yyyy", Locale.getDefault()).format((currentTime));
    String date_month = new SimpleDateFormat("MM", Locale.getDefault()).format((currentTime));
    String date_day = new SimpleDateFormat("dd", Locale.getDefault()).format((currentTime));

    int find_year = Integer.parseInt(date_year);
    int find_month = Integer.parseInt(date_month);
    int find_day = Integer.parseInt(date_day);

    int userId;
    int reportId;

    TextView user_name, low_pressure, high_pressure, before_sugar, after_sugar, weight;

    private boolean side = false;

    CommentAdapter commentAdapter;

    private ListView listView;
    private EditText comment;
    private Button buttonSend;
    private Button buttonEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_report);

        String my_name = ApplicationClass.sharedPreferences.getString("name", "");
        this.user_name = findViewById(R.id.report_name);
        this.user_name.setText(my_name);

        low_pressure = findViewById(R.id.low_pressure_report);
        high_pressure = findViewById(R.id.high_pressure_report);
        before_sugar = findViewById(R.id.before_sugar_report);
        after_sugar = findViewById(R.id.after_sugar_report);
        weight = findViewById(R.id.weight_report);

        buttonSend = (Button) findViewById(R.id.btn_send);
        listView = (ListView) findViewById(R.id.list_view);
        comment = (EditText) findViewById(R.id.chatText);

        commentAdapter = new CommentAdapter(getApplicationContext(), R.layout.activity_comment);
        listView.setAdapter(commentAdapter);

        userId = ApplicationClass.sharedPreferences.getInt("userId", 0);
        ApplicationClass.retrofit_manager.getReport(userId, find_year, find_month, find_day, new RetrofitReportCallback() {
            @Override
            public void onError(Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
                Log.d("ReportActivity", "에러 : " + t);
            }

            @Override
            public void onSuccess(String message, List<DataGetReport> data) {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                if (data.size() != 0) {
                    Log.d("ReportActivity", "data - CreatedAt : " + data.get(0).getCreatedAt());
                    Log.d("ReportActivity", "data - ReportId : " + data.get(0).getReportId());
                    Log.d("ReportActivity", "data - Systolic : " + data.get(0).getSystolic());
                    Log.d("ReportActivity", "data - Diastolic : " + data.get(0).getDiastolic());
                    Log.d("ReportActivity", "data - SugarLevels : " + data.get(0).getSugarLevels());
                    Log.d("ReportActivity", "data - Weight : " + data.get(0).getWeight());

                    reportId = data.get(0).getReportId();

                    String str_low_pressure = data.get(0).getDiastolic() + " mmHg";
                    String str_high_pressure = data.get(0).getSystolic() + " mmHg";
                    String str_before_sugar = data.get(0).getSugarLevels().get(0) + " mg/dL";
                    String str_after_sugar = data.get(0).getSugarLevels().get(1) + " mg/dL";
                    String str_weight = data.get(0).getWeight() + " kg";

                    low_pressure.setText(str_low_pressure);
                    high_pressure.setText(str_high_pressure);
                    before_sugar.setText(str_before_sugar);
                    after_sugar.setText(str_after_sugar);
                    weight.setText(str_weight);

                    ApplicationClass.retrofit_manager.getComment(reportId, new RetrofitCommentCallback() {
                        @Override
                        public void onError(Throwable t) {
                            Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
                            Log.d("ReportActivity", "에러 : " + t);
                        }

                        @Override
                        public void onSuccess(String message, List<DataGetComment> data) {
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                            if (data.size() != 0) {
                                Log.d("ReportActivity", "data - CreatedAt : " + data.get(0).getCreatedAt());
                                Log.d("ReportActivity", "data - ModifiedAt : " + data.get(0).getModifiedAt());
                                Log.d("ReportActivity", "data - ReportId : " + data.get(0).getReportId());
                                Log.d("ReportActivity", "data - AuthorId : " + data.get(0).getAuthorId());
                                Log.d("ReportActivity", "data - CommentId : " + data.get(0).getCommentId());
                                Log.d("ReportActivity", "data - Content : " + data.get(0).getContent());

                                for (int i = 0; i < data.size(); i++) {
                                    if (data.get(i).getAuthorId() == getPreference("userId")) {
                                        commentAdapter.add(new Comment(false, data.get(i).getContent()));
                                    }
                                    else {
                                        commentAdapter.add(new Comment(true, data.get(i).getContent()));
                                    }

                                    commentAdapter.addCommentId(data.get(i).getCommentId());
                                }
                            }
                            else
                                Log.d("ReportActivity", "해당 리포트에 저장된 댓글이 없습니다.");
                        }

                        @Override
                        public void onFailure(int error_code) {
                            Toast.makeText(getApplicationContext(), "error code : " + error_code, Toast.LENGTH_SHORT).show();
                            Log.d("ReportActivity", "실패 : " + error_code);
                        }
                    });
                }
                else
                    Log.d("ReportActivity", "해당 날짜로 생성된 리포트가 없습니다.");
            }

            @Override
            public void onFailure(int error_code) {
                Toast.makeText(getApplicationContext(), "error code : " + error_code, Toast.LENGTH_SHORT).show();
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

        // 해당 메세지를 짧게 누르면 메세지 수정 버튼이 보이게 됨
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                buttonEdit = view.findViewById(R.id.btn_edit);
//                buttonEdit.setVisibility(View.VISIBLE);
            }
        });
        
        // 해당 메세지를 길게 누르면 메세지 삭제됨
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ReportActivity.this, R.style.MyDialogTheme);
                builder.setTitle("삭제하기");
                builder.setMessage("이 메시지를 삭제할까요?");

                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        int delete_commentId = commentAdapter.getCommentId(position);
                        ApplicationClass.retrofit_manager.deleteComment(delete_commentId, new RetrofitCallback() {
                            @Override
                            public void onError(Throwable t) {
                                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
                                Log.d("ReportActivity", "에러 : " + t);
                            }

                            @Override
                            public void onSuccess(String message, String data) {
                                commentAdapter.remove(position);
                                commentAdapter.notifyDataSetChanged(); // 새로고침
                            }

                            @Override
                            public void onFailure(int error_code) {
                                Toast.makeText(getApplicationContext(), "error code : " + error_code, Toast.LENGTH_SHORT).show();
                                Log.d("ReportActivity", "실패 : " + error_code);
                            }
                        });
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
        ApplicationClass.retrofit_manager.makeComment(comment.getText().toString(), reportId, new RetrofitCallback() {
            @Override
            public void onError(Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
                Log.d("ReportActivity", "에러 : " + t);
            }

            @Override
            public void onSuccess(String message, String data) {
                Toast.makeText(getApplicationContext(), data, Toast.LENGTH_SHORT).show();
                Log.d("ReportActivity", message + data);

                commentAdapter.add(new Comment(side, comment.getText().toString()));
                comment.setText("");
                //side = !side;
            }

            @Override
            public void onFailure(int error_code) {
                Toast.makeText(getApplicationContext(), "error code : " + error_code, Toast.LENGTH_SHORT).show();
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

    public int getPreference(String flag) {
        return ApplicationClass.sharedPreferences.getInt(flag, 0);
    }
}