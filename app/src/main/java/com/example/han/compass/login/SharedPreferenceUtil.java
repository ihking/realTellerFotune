package com.example.han.compass.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by home on 2016-09-16.
 */
public class SharedPreferenceUtil {
    static Context mContext;


    public SharedPreferenceUtil(Context c) {
        mContext = c;
    }

    public static void putSharedPreference
            (Context context, String key, String value)
    {
        SharedPreferences prefs =
                PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor = prefs.edit();

        editor.putString(key, value);
        editor.commit();
    }

    public static String getSharedPreference
            (Context context, String key)
    {
        SharedPreferences prefs =
                PreferenceManager.getDefaultSharedPreferences(context);

        return prefs.getString(key, "");
    }

}