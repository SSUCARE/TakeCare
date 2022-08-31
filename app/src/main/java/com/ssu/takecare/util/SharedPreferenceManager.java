package com.ssu.takecare.util;

import static com.ssu.takecare.ApplicationClass.sharedPreferences;

public class SharedPreferenceManager {
    public static void saveString(String key, String body) {
        sharedPreferences.edit().putString(key, body).apply();
    }

    public static String getString(String key) {
        return sharedPreferences.getString(key, null);
    }
}
