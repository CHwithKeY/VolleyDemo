package com.oji.kreate.vsf.publicView;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.oji.kreate.vsf.R;
import com.oji.kreate.vsf.publicClass.Methods;

/**
 * Created by KeY on 2016/10/12.
 */
public class ColorSnackBar {

    private Context context;

    private Snackbar snackbar;

    private int cos;

    private CoordinatorLayout snack_col;

//    public ColorSnackBar(Context context, int parent_resId) {
//        this.context = context;
//        if (parent_resId == 0) {
//            snack_col = (CoordinatorLayout) ((AppCompatActivity) context).findViewById(R.id.snack_col);
//        } else {
//            snack_col = (CoordinatorLayout) ((AppCompatActivity) context).findViewById(parent_resId);
//        }
//    }

    public ColorSnackBar(Context context) {
        this.context = context;

        cos = 0;
        snack_col = null;
    }

    private void LogInfo() {
        Log.i(getClass().getName(), "snack bar context is : " + context);
    }

    public ColorSnackBar setSnackViewParent(int parent_resId) {
        if (parent_resId == 0) {
            parent_resId = R.id.snack_col;
        }

        AppCompatActivity activity = (AppCompatActivity) context;
        snack_col = activity.findViewById(parent_resId);

        return this;
    }

    public void show(String text) {
        LogInfo();

        if (snack_col == null) {
            try {
                snack_col = ((AppCompatActivity) context).findViewById(R.id.snack_col);
            } catch (Exception e) {
                Log.e(getClass().getName(), "SnackBar没有引入布局");
                return;
            }
        }

        if (snackbar != null) {
            snackbar = null;
            cos++;
            if (cos > 5) {
                System.gc();
                cos = 0;
            }
        }

        snackbar = Snackbar.make(snack_col, text, Snackbar.LENGTH_SHORT);

        Snackbar.SnackbarLayout snackbarView = (Snackbar.SnackbarLayout) snackbar.getView();

        // get snack background color and text color from property file
        String snack_bgcolor = Methods.getSpecificProperty(context, "snack_bgcolor");
        String snack_text_color = Methods.getSpecificProperty(context, "snack_text_color");

        // set snack background color
        int bgcolor = 0xFF7EC8DB;
        if (snack_bgcolor != null && !snack_bgcolor.isEmpty()) {
            bgcolor = Integer.parseInt(snack_bgcolor);
        }
        try {
            snackbarView.setBackgroundColor(bgcolor);
        } catch (Exception e) {
            Log.e(getClass().getName(), "SnackView的背景色值异常---SnackView background color value wrong.");
            bgcolor = 0xFF7EC8DB;
            snackbarView.setBackgroundColor(bgcolor);
        }

        // set snack text color
        String text_color = "#FF2E4F92";
        if (snack_text_color != null && !snack_text_color.isEmpty()) {
            text_color = snack_text_color;
        }
        try {
            ((TextView) snackbarView.findViewById(R.id.snackbar_text)).setTextColor(Color.parseColor(text_color));
        } catch (Exception e) {
            Log.e(getClass().getName(), "SnackView的字体色值异常---SnackView text color value wrong.");
            text_color = "#FF2E4F92";
            ((TextView) snackbarView.findViewById(R.id.snackbar_text)).setTextColor(Color.parseColor(text_color));
        }

        snackbar.show();
    }

//    public void showCoordinate(int resId, String text) {
//        CoordinatorLayout snack_col = (CoordinatorLayout) ((AppCompatActivity) context).findViewById(resId);
//
//        if (snackbar != null) {
//            snackbar = null;
//            cos++;
//            if (cos > 5) {
//                System.gc();
//                cos = 0;
//            }
//        }
//
//        try {
//            snackbar = Snackbar.make(snack_col, text, Snackbar.LENGTH_SHORT);
//        } catch (NullPointerException exception) {
//            Log.e(getClass().getName(), "SnackBar 引入自定义 Coordinator 布局错误");
//            return;
//        }
//
//        Snackbar.SnackbarLayout snackbarView = (Snackbar.SnackbarLayout) snackbar.getView();
//        snackbarView.setBackgroundColor(0xFF7EC8DB);
//        ((TextView) snackbarView.findViewById(R.id.snackbar_text)).setTextColor(Color.parseColor("#FF2E4F92"));
//
//        snackbar.show();
//    }

}
