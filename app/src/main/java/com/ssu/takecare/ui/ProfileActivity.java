package com.ssu.takecare.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.ssu.takecare.ApplicationClass;
import com.ssu.takecare.R;
import android.graphics.Bitmap;
import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProfileActivity extends AppCompatActivity {

    String mCurrentPhotoPath;
    private static final int REQUEST_TAKE_PHOTO = 1;

    ImageView profileImage;
    TextView tv_name, tv_gender, tv_age, tv_height, tv_role;
    TextView tv_match_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileImage = findViewById(R.id.profile_img);

        int match_count=ApplicationClass.sharedPreferences.getInt("Mapping_Count",0);
        tv_match_count=findViewById(R.id.profile_matchcounting_tv);
        tv_match_count.setText(String.valueOf(match_count));

        String my_name = ApplicationClass.sharedPreferences.getString("name", "");
        tv_name = findViewById(R.id.profile_name);
        tv_name.setText(my_name);

        String my_gender = ApplicationClass.sharedPreferences.getString("gender", "");
        tv_gender = findViewById(R.id.profile_gender);
        tv_gender.setText(my_gender);

        int my_age = ApplicationClass.sharedPreferences.getInt("age", 0);
        tv_age = findViewById(R.id.profile_age);
        tv_age.setText(String.valueOf(my_age));
        tv_age.append(" 세");

        int my_height = ApplicationClass.sharedPreferences.getInt("height", 0);
        tv_height = findViewById(R.id.profile_height);
        tv_height.setText(String.valueOf(my_height));
        tv_height.append(" cm");

        String my_role = ApplicationClass.sharedPreferences.getString("role", "");
        tv_role = findViewById(R.id.profile_role);
        tv_role.setText(my_role);
    }

    public void getProfileImage(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.d("ProfileActivity", "권한 설정 완료");
            }
            else {
                Log.d("ProfileActivity", "권한 설정 요청");
                ActivityCompat.requestPermissions(ProfileActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }

        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_profile_image);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes((WindowManager.LayoutParams) params);
        dialog.show();

        Button btnTakeImg = dialog.findViewById(R.id.btn_take_img);
        Button btnGetImg = dialog.findViewById(R.id.btn_get_img);

        btnTakeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

        btnGetImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    // 권한 요청
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED ) {
            Log.d("ProfileActivity", "Permission: " + permissions[0] + "was " + grantResults[0]);
        }

    }

    // 카메라로 촬영한 영상을 가져오는 부분
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        try {
            switch (requestCode) {
                case REQUEST_TAKE_PHOTO:
                    if (resultCode == RESULT_OK) {
                        File file = new File(mCurrentPhotoPath);
                        Bitmap bitmap;
                        if (Build.VERSION.SDK_INT >= 29) {
                            ImageDecoder.Source source = ImageDecoder.createSource(getContentResolver(), Uri.fromFile(file));
                            try {
                                bitmap = ImageDecoder.decodeBitmap(source);
                                if (bitmap != null) {
                                    profileImage.setImageBitmap(bitmap);
                                }
                            }
                            catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.fromFile(file));
                                if (bitmap != null) {
                                    profileImage.setImageBitmap(bitmap);
                                }
                            }
                            catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    break;
            }
        }
        catch (Exception error) {
            error.printStackTrace();
        }
    }

    // 사진 촬영 후 썸네일만 띄워줌. 이미지를 파일로 저장해야 함
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    // 카메라 인텐트 실행하는 부분
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Log.d("ProfileActivity", "aaaa");
        File photoFile = null;
        Log.d("ProfileActivity", "bbbb");
        try {
            photoFile = createImageFile();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        if (photoFile != null) {
            Uri photoURI = FileProvider.getUriForFile(this, "com.ssu.takecare", photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
        }
    }

    public void click_event(View view) {
        Intent intent=new Intent(getApplicationContext(), InfoActivity.class);
        intent.putExtra("EXISTING_USER", 2);
        startActivity(intent);
    }

    public void back_btn_event(View view) {
        finish();
    }
}