package com.oji.kreate.vsf.base;

import android.util.Log;

import org.json.JSONObject;

import java.lang.reflect.Field;

public class BaseHttpObject implements ErrorSet {

    public String[] getParamsName() {
        Field[] field = getClass().getDeclaredFields();
        String[] paramsName = new String[field.length];

        for (int i = 0; i < field.length; i++) {
            paramsName[i] = field[i].getName();
        }

        return paramsName;
    }

    public String getParamValue(BaseHttpObject obj, String paramName) {
        Field field;

        try {
            field = getClass().getDeclaredField(paramName);
            field.setAccessible(true);
            try {
                return (String) field.get(obj);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return null;
            }
        } catch (NoSuchFieldException | SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public void setParamValue(String paramName, String paramValue) {
        Field field;

        try {
            field = getClass().getDeclaredField(paramName);
            field.setAccessible(true);
            try {
                field.set(this, paramValue);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (NoSuchFieldException | SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public BaseHttpObject wrap(JSONObject obj) {
        String[] paramsName = getParamsName();

        for (int i = 0; i < obj.length(); i++) {
            try {
                String objValue = obj.getString(paramsName[i]);
                setParamValue(paramsName[i], objValue);
            } catch (Exception e) {
                Log.e(getClass().getName(), NOT_FIND_OBJECT_SPECIFIC_PARAMS_NAME_VALUE);
            }
        }

        return this;
    }

}
