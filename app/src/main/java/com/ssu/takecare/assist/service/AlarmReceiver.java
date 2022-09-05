package com.ssu.takecare.assist.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import com.ssu.takecare.R;
import com.ssu.takecare.ui.MainActivity;

public class AlarmReceiver extends BroadcastReceiver {
    public AlarmReceiver(){ }

    NotificationManager manager;
    NotificationCompat.Builder builder;

    // 오레오 이상은 반드시 채널을 설정해줘야 Notification이 작동함
    private static String CHANNEL_ID = "medicine_channel";
    private static String CHANNEL_NAME = "복용 관리";

    @Override
    public void onReceive(Context context, Intent intent) {
        builder = null;
        manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            manager.createNotificationChannel(
                    new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            );
            builder = new NotificationCompat.Builder(context, CHANNEL_ID);
        }
        else {
            builder = new NotificationCompat.Builder(context);
        }

        // 알림창 클릭 시 activity 화면 부름
        Intent intent2 = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
            pendingIntent = PendingIntent.getActivity(context,101, intent2, PendingIntent.FLAG_IMMUTABLE);
        }
        else {
            pendingIntent = PendingIntent.getActivity(context,101, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
        }

        // 알림창 제목
        builder.setContentTitle("약 알림");
        // 알림창 내용
        builder.setContentText("약 드실 시간이에요~!");
        // 알림창 아이콘
        builder.setSmallIcon(R.drawable.medicine_alarm_icon);
        // 알림창 터치시 자동 삭제
        builder.setAutoCancel(true);

        builder.setContentIntent(pendingIntent);

        Notification notification = builder.build();
        manager.notify(1, notification);

    }
}
