package com.oji.kreate.vsf.sharedInfo;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by KeY on 2016/6/3.
 */
public final class SharedAction implements ErrorSet {

    private SharedPreferences sp;

    public SharedAction() {
    }

    public SharedAction(Context context) {
        this.sp = context.getSharedPreferences("vsf_sp", 0);
    }

    public void setShared(SharedPreferences sp) {
        this.sp = sp;
    }

    public void clearLastIdInfo() {
        SharedPreferences.Editor editor = this.sp.edit();
        editor.putInt("last_id", 0);
        editor.apply();
    }

    public void clearInfo(String key) {
        SharedPreferences.Editor editor = this.sp.edit();
        editor.putString(key, "");
        editor.apply();
    }

    public void setInfo(String key, String value) {
        SharedPreferences.Editor editor = this.sp.edit();
        editor.putString(key, String.valueOf(value));
        editor.apply();
    }

    public String getInfo(String key) {
        return sp.getString(key, "");
    }


//    old methods
//
//    private String NAME_LIST = "name_list";
//
//    private SharedPreferences sp;
//
//    public SharedAction() {
//
//    }
//
//    public SharedAction(Context context) {
//        sp = context.getSharedPreferences(SharedSet.NAME, Context.MODE_PRIVATE);
//    }
//
//    public SharedAction(Context context, String sp_name) {
//        sp = context.getSharedPreferences(NAME_LIST, Context.MODE_PRIVATE);
//
//        int latest_id = sp.getAll().size();
//        SharedPreferences.Editor editor = sp.edit();
//        editor.putString("key" + latest_id, sp_name);
//        editor.apply();
//
//        sp = context.getSharedPreferences(sp_name, Context.MODE_PRIVATE);
//    }
//
//    public ArrayList<String> getFileNameList(Context context) {
//        ArrayList<String> nameList = new ArrayList<>();
//
//        sp = context.getSharedPreferences(NAME_LIST, Context.MODE_PRIVATE);
//        for (int i = 0; i < sp.getAll().size(); i++) {
//            nameList.add(sp.getString("key" + i, "Null"));
//        }
//
//        return nameList;
//    }
//
//    public void setShared(SharedPreferences sp) {
//        this.sp = sp;
//    }
//
//    public void clearLastIdInfo() {
//        SharedPreferences.Editor editor = sp.edit();
//        editor.putInt(SharedSet.KEY_LAST_ID, 0);
//        editor.apply();
//    }
//
//    public void setMap(String key, Object value) {
//        SharedPreferences.Editor editor = sp.edit();
//
//        if (value instanceof Integer) {
//            editor.putInt(key, Integer.valueOf("" + value));
//        } else if (value instanceof Float) {
//            editor.putFloat(key, Float.valueOf("" + value));
//        } else if (value instanceof Boolean) {
//            editor.putBoolean(key, Boolean.valueOf("" + value));
//        } else if (value instanceof String) {
//            editor.putString(key, String.valueOf(value));
//        } else {
//            Log.e(getClass().getName(), VALUE_CLASS_NOT_MATCH);
//            return;
//        }
//        editor.apply();
//    }
//
//    public <T> T getMap(T c, String key) {
//        if (c instanceof Integer) {
//            return Methods.cast(sp.getInt(key, 0));
//        }
//
//        if (c instanceof Float) {
//            return Methods.cast(sp.getFloat(key, 0));
//        }
//
//        if (c instanceof Boolean) {
//            return Methods.cast(sp.getBoolean(key, false));
//        }
//        if (c instanceof String) {
//            return Methods.cast(sp.getString(key, "Null"));
//        } else {
//            Log.e(getClass().getName(), KEY_NOT_MATCH);
//            return null;
//        }
//    }

}
