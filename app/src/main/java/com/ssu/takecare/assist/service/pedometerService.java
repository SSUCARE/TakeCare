package com.ssu.takecare.assist.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import com.ssu.takecare.ApplicationClass;
import com.ssu.takecare.R;
import static com.ssu.takecare.ApplicationClass.sharedPreferences;

public class pedometerService extends Service implements SensorEventListener {
    String TAG = "testService,Jdebug";
    NotificationManager mNotificationManager;
    NotificationCompat.Builder notification;
    SensorManager sensorManager;
    Sensor stepCountSensor;

    // 현재 걸음 수
    int pedometer_count = 0;
    int sensor_flag = 0;
    float sensor_value = 0.0f;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        pedometer_count=ApplicationClass.sharedPreferences.getInt("pedometer_count", 0);
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        stepCountSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        sensorManager.registerListener(this, stepCountSensor, SensorManager.SENSOR_DELAY_FASTEST);

        // PendingIntent를 이용하면 포그라운드 서비스 상태에서 알림을 누르면 앱의 MainActivity를 다시 열게 된다.
        Intent testIntent = new Intent(getApplicationContext(), com.ssu.takecare.ui.MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, testIntent, PendingIntent.FLAG_IMMUTABLE);

        // 오래오 윗버젼일 때는 아래와 같이 채널을 만들어 Notification과 연결해야 한다.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("channel", "play!!",
                    NotificationManager.IMPORTANCE_DEFAULT);

            // Notification과 채널 연걸
            mNotificationManager = ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE));
            mNotificationManager.createNotificationChannel(channel);

            // Notification 세팅
            notification
                    = new NotificationCompat.Builder(getApplicationContext(), "channel")
                    .setSmallIcon(R.drawable.pedometer_icon)
                    .setContentTitle(pedometer_count+"걸음")
                    .setContentIntent(pendingIntent)
                    .setContentText("목표 걸음 수는 "+Integer.toString(ApplicationClass.sharedPreferences.getInt("goal_steps", 0)).replaceAll("\\B(?=(\\d{3})+(?!\\d))", ",")+"입니다.");

            // id 값은 0보다 큰 양수가 들어가야 한다.
            mNotificationManager.notify(1, notification.build());
            // foreground에서 시작
            startForeground(1, notification.build());


            Thread t1 = new Thread() {
                @Override
                public void run() {
                    try {
                        while (true) {//만보기 기록은 2분마다 한번씩 확인하기로 바꾸기.
                            Thread.sleep(10000);
                            notification.setContentTitle(Integer.toString(pedometer_count).replaceAll("\\B(?=(\\d{3})+(?!\\d))", ",")+" 걸음");
                            mNotificationManager.notify(1, notification.build());
                            sharedPreferences.edit().putInt("pedometer_count",pedometer_count).apply();
                            Log.d(TAG, "서비스 실행중");
                        }
                    } catch (Exception e) {

                    }
                }
            };
            t1.start();
        }

        return START_STICKY;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        // 걸음 센서 이벤트 발생시
        if (sensorEvent.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            if (sensor_flag == 0) {
                sensor_value = sensorEvent.values[0];
                sensor_flag = 1;
            }

            if (sensorEvent.values[0] != sensor_value) {
                pedometer_count+=2;
                Log.d(TAG, "핸들러작동중");
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
