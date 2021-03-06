package com.ssu.takecare.UI;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ShareCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import com.ssu.takecare.ApplicationClass;
import com.ssu.takecare.BuildConfig;
import com.ssu.takecare.R;
import com.ssu.takecare.Retrofit.GetReport.DataGetReport;
import com.ssu.takecare.Retrofit.RetrofitCustomCallback.RetrofitReportCallback;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ShareInfoImageActivity extends AppCompatActivity {

    View view;
    Integer userId;

    boolean fileReadPermission;
    boolean fileWritePermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view =  (LinearLayout)getLayoutInflater().inflate(R.layout.activity_share_info_image,null);
        setContentView(view);

        ImageButton imageButton = (ImageButton) view.findViewById(R.id.back_btn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView pressure_txt = (TextView) view.findViewById(R.id.share_pressure);
        TextView sugar_txt = (TextView) view.findViewById(R.id.share_sugar);
        TextView weight_txt = (TextView) view.findViewById(R.id.share_weight);

        Date myDate = new Date();
        SimpleDateFormat dmyFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String dmy = dmyFormat.format(myDate);
        String[] time = dmy.split("-");

        userId = ApplicationClass.sharedPreferences.getInt("userId", 0);
        Log.d("ShareInfoImageActivity","userId :" + userId);

        // login??? user??? ???????????? ????????? ???????????? - ?????? image ??????/????????? ??????.
        try {
            ApplicationClass.retrofit_manager.getReport(userId, Integer.parseInt(time[0]), Integer.parseInt(time[1]), Integer.parseInt(time[2]), new RetrofitReportCallback() {
                @Override
                public void onError(Throwable t) {
                }

                @Override
                public void onSuccess(String message, List<DataGetReport> data) {
                    if (!pressure_txt.getText().equals("")) {
                        String text_pressure = "?????? (" + String.valueOf(data.get(0).getSystolic()) + " / " + String.valueOf(data.get(0).getDiastolic()) + " mmHg)";
                        pressure_txt.setText(text_pressure);
                    }

                    if (!sugar_txt.getText().equals("")) {
                        String text_sugar = "?????? (" + String.valueOf(data.get(0).getSugarLevels()) + " mg/dL)";
                        sugar_txt.setText(text_sugar);
                    }

                    if (!weight_txt.getText().equals("")) {
                        String text_weight = "?????? (" + String.valueOf(data.get(0).getWeight()) + " kg)";
                        weight_txt.setText(text_weight);
                    }

                    // ????????? xml ????????? ????????? ????????? ????????????.
                    File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "Android/data/com.ssu.takecare/files");

                    if (!dir.exists())
                        dir.mkdirs();

                    File file = new File(dir, "shareInfoImage.png");

                    ImageView view2 = view.findViewById(R.id.image_share);
                    view2.buildDrawingCache();
                    Bitmap bmap = view2.getDrawingCache();
                    Canvas canvas = new Canvas(bmap);
                    canvas.drawARGB(50, 0, 0, 0);

//                        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/SomeFont.ttf");
                    Paint textPaint = new Paint();
                    textPaint.setTextSize(60);
//                        textPaint.setTypeface(typeface);
                    canvas.drawText("????????? ???????????? ????????????...", 200, 900, textPaint);
                    canvas.drawText(pressure_txt.getText().toString(), 200, 1000, textPaint);
                    canvas.drawText(sugar_txt.getText().toString(), 200, 1100, textPaint);
                    canvas.drawText(weight_txt.getText().toString(), 200, 1200, textPaint);

                    view2.draw(canvas);

                    OutputStream outputStream = null;
                    try {
                        outputStream = new FileOutputStream(file);
                        bmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                        outputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (ContextCompat.checkSelfPermission(ShareInfoImageActivity.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        fileReadPermission = true;
                    }
                    if (ContextCompat.checkSelfPermission(ShareInfoImageActivity.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        fileWritePermission = true;
                    }

                    if (!fileReadPermission || !fileWritePermission) {
                        ActivityCompat.requestPermissions(ShareInfoImageActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
                    }

                    // ??????????????? ??? ????????? ??? ?????? ?????? ?????? ????????????, ???????????? ????????? ??? ????????? ??? ??? ??????.
                    // ????????? ??????.
                    if (fileReadPermission && fileWritePermission) {
                        Uri imageUri = FileProvider.getUriForFile(
                                ShareInfoImageActivity.this,
                                BuildConfig.APPLICATION_ID,
                                file);
                        //            Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        //            shareIntent.setType("image/png");
                        //            shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                        //            startActivity(Intent.createChooser(shareIntent, "Share Image"));

                        //            Uri uri = FileProvider.getUriForFile(this, "com.example.provider",file);
                        Intent share = ShareCompat.IntentBuilder.from(ShareInfoImageActivity.this)
                                .setStream(imageUri) // uri from FileProvider
                                .setType("image/png")
                                .getIntent()
                                .setAction(Intent.ACTION_SEND) //Change if needed
                                .setDataAndType(imageUri, "image/*")
                                .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                        startActivity(Intent.createChooser(share, "share image"));
                    } else {
                        showToast("permission??? ???????????? ????????? ?????? ????????? ????????????.");
                    }
                }

                @Override
                public void onFailure(int error_code) {
                }
            });
        }
        catch ( Exception | Error e ) {
            e.printStackTrace();
            showToast("???????????? ?????????????????? ????????????");
        }
    }

    private void showToast(String message){
        Toast toast=Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    public Canvas onDraw(Bitmap bitmap, String txt, String txt2, String txt3) {
        try {
            Canvas canvas= null;
            canvas.drawBitmap ( bitmap , 0 , 0 , null ) ;

            Paint paint = new Paint();
            paint.setColor(Color.BLACK);
            canvas.drawText(txt, 10, 10, paint);
            canvas.drawText(txt2, 10, 20, paint);
            canvas.drawText(txt3, 10, 30, paint);
            return canvas;

        }
        catch ( Exception | Error e ) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode==200 && grantResults.length>0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                fileReadPermission = true;
            if (grantResults[1] == PackageManager.PERMISSION_GRANTED)
                fileWritePermission = true;
        }
    }
}