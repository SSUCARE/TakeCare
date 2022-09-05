package com.ssu.takecare.assist.viewpager;

import android.content.Context;
import android.content.SharedPreferences;
import com.ssu.takecare.ApplicationClass;

public class WelcomeSession {

    Context _context;
    SharedPreferences.Editor editor = ApplicationClass.sharedPreferences.edit();

    private static final String IS_FIRST_TIME = "IsFirstTime";

    public WelcomeSession(Context context) {
        this._context = context;
    }

    public void setFirstTime(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTime() {
        return ApplicationClass.sharedPreferences.getBoolean(IS_FIRST_TIME, true);
    }
}
