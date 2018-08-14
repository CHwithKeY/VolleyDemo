package com.oji.kreate.volleydemo.publicClass;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Display;

/**
 * Created by KeY on 2016/3/25.
 */
public class ScreenSize {

    private AppCompatActivity activity;

    private int width;
    private int height;

    public ScreenSize(Context context) {
        activity = (AppCompatActivity) context;
        getScreenSize();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    private void getScreenSize() {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            display.getRealSize(size);
        } else {
            display.getSize(size);
        }

        width = size.x;
        height = size.y;
    }

    public int getDPI() {
        DisplayMetrics metrics = activity.getResources().getDisplayMetrics();
        return metrics.densityDpi;
    }

}
