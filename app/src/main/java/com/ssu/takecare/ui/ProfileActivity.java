package com.ssu.takecare.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
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
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProfileActivity extends AppCompatActivity {

    private String mCurrentPhotoPath;
    private static final int REQUEST_TAKE_PHOTO = 1;
    private static final int REQUEST_OPEN_ALBUM = 2;
    private String imgName = "my_profile_image.png";

    private Dialog dialog;
    private ImageView profileImage;
    private TextView tv_name, tv_gender, tv_age, tv_height, tv_role;
    private TextView tv_match_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileImage = findViewById(R.id.profile_img);
        getImage();

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

        setDialog();
        uploadImage();
    }

    // dialog 설정
    public void setDialog() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_profile_image);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes((WindowManager.LayoutParams) params);
        dialog.show();
    }

    // 사진 찍기 또는 사진 가져오기 선택
    public void uploadImage() {
        Button btnTakeImg = dialog.findViewById(R.id.btn_take_img);
        Button btnGetImg = dialog.findViewById(R.id.btn_get_img);

        btnTakeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                File photoFile = null;
                try {
                    photoFile = createImageFile();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }

                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(ProfileActivity.this, "com.ssu.takecare.fileprovider", photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                }

                dialog.dismiss();
            }
        });

        btnGetImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_OPEN_ALBUM);

                dialog.dismiss();
            }
        });
    }

    // 권한 요청 확인
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED ) {
            Log.d("ProfileActivity", "Permission: " + permissions[0] + "was " + grantResults[0]);
        }
    }

    // 사진을 직접 찍거나 앨범에서 사진을 가져옴
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        try {
            switch (requestCode) {
                case REQUEST_TAKE_PHOTO:
                    if (resultCode == RESULT_OK && intent != null) {
                        takePhoto();
                    }
                    break;
                case REQUEST_OPEN_ALBUM:
                    if (resultCode == RESULT_OK && intent != null) {
                        openAlbum(intent);
                    }
                    break;
            }
        }
        catch (Exception error) {
            error.printStackTrace();
        }
    }

    // 카메라로 사진을 찍어 프로필 사진으로 설정하고 내부저장소에 해당 사진을 저장
    private void takePhoto() {
        File file = new File(mCurrentPhotoPath);
        Bitmap bitmap;
        if (Build.VERSION.SDK_INT >= 29) {
            ImageDecoder.Source source = ImageDecoder.createSource(getContentResolver(), Uri.fromFile(file));
            try {
                bitmap = ImageDecoder.decodeBitmap(source);
                if (bitmap != null) {
                    profileImage.setImageBitmap(bitmap);
                    saveBitmapToJpeg(bitmap);
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.fromFile(file));
                if (bitmap != null) {
                    profileImage.setImageBitmap(bitmap);
                    saveBitmapToJpeg(bitmap);
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 앨범에서 선택한 사진을 프로필 사진으로 설정하고 내부저장소에 해당 사진을 저장
    private void openAlbum(Intent intent) {
        Uri fileUri = intent.getData();
        ContentResolver resolver = getContentResolver();

        try {
            InputStream instream = resolver.openInputStream(fileUri);
            Bitmap imgBitmap = BitmapFactory.decodeStream(instream);
            profileImage.setImageBitmap(imgBitmap);
            instream.close();
            saveBitmapToJpeg(imgBitmap);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 사진 촬영 후 이미지를 외부 저장소에 저장
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

    // 선택한 이미지를 내부 저장소에 저장
    private void saveBitmapToJpeg(Bitmap bitmap) {
        File tempFile = new File(getCacheDir(), imgName);
        try {
            tempFile.createNewFile();
            FileOutputStream out = new FileOutputStream(tempFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 설정된 프로필 사진 불러오기
    private void getImage() {
        try {
            File file = getCacheDir();
            File[] flist = file.listFiles();
            for (int i = 0; i < flist.length; i++) {
                if (flist[i].getName().equals(imgName)) {
                    Bitmap bitmap = BitmapFactory.decodeFile(String.valueOf(flist[i]));
                    profileImage.setImageBitmap(bitmap);
                }
            }
        }
        catch (Exception e) {
           e.printStackTrace();
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