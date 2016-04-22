package com.jingtao.zhbj_jt.common;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreferences工具类
 * Created by jingtao on 16/4/15.
 */
public class SharedPreferencesUtils {

    public static final String PREF_Name="jingtao";

    public static boolean getBoolean(Context context,String key,boolean defaultValue){
        SharedPreferences sp = context.getSharedPreferences(PREF_Name, context.MODE_PRIVATE);
        return sp.getBoolean(key, defaultValue);
    }

    public static void setBoolean(Context context,String key,boolean value){
        SharedPreferences sp = context.getSharedPreferences(PREF_Name, context.MODE_PRIVATE);
        sp.edit().putBoolean(key,value).commit();
    }

    public static String getString(Context ctx, String key, String defaultValue) {
        SharedPreferences sp = ctx.getSharedPreferences(PREF_Name,
                Context.MODE_PRIVATE);
        return sp.getString(key, defaultValue);
    }

    public static void setString(Context ctx, String key, String value) {
        SharedPreferences sp = ctx.getSharedPreferences(PREF_Name,
                Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }

}
