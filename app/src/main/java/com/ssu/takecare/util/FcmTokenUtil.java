package com.ssu.takecare.util;

import static android.content.ContentValues.TAG;

import static com.ssu.takecare.util.SharedPreferenceManager.getString;
import static com.ssu.takecare.util.SharedPreferenceManager.saveString;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class FcmTokenUtil {
    public static void loadFcmToken() {
        FirebaseMessaging.getInstance().getToken()
            .addOnCompleteListener(new OnCompleteListener<String>() {
                @Override
                public void onComplete(@NonNull Task<String> task) {
                    if (!task.isSuccessful()) {
                        Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                        return;
                    }
                    // Get new FCM registration token
                    saveString("FcmToken", task.getResult());
                }
            });
    }

    public static String getFcmToken() {
        return getString("FcmToken");
    }
}