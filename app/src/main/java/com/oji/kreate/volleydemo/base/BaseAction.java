package com.oji.kreate.volleydemo.base;

import android.content.Context;

import com.oji.kreate.volleydemo.R;
import com.oji.kreate.volleydemo.publicClass.Methods;
import com.oji.kreate.volleydemo.publicView.ColorSnackBar;
import com.oji.kreate.volleydemo.sharedinfo.SharedAction;


/**
 * Created by KeY on 2016/6/3.
 */
public class BaseAction {

    public final static int REQUEST_DEFAULT = 0;
    public final static int REQUEST_LOAD_MORE = 1;
    public final static int REQUEST_FILTER = 2;
    public final static int REQUEST_REFRESH = 3;

    public final static int ACTION_NET_DOWN = 0;
    public final static int ACTION_LACK = 1;
    public final static int ACTION_DONE = 2;

    protected Context context;
    //    protected LineToast toast;
    protected ColorSnackBar snackBar;
    protected SharedAction sharedAction;

    public BaseAction(Context context) {
        this.context = context;

        varInit();
    }

    private int request = REQUEST_DEFAULT;

    public int getRequest() {
        return request;
    }

    public void checkRequest(int request) {
        this.request = request;
        if (request != REQUEST_LOAD_MORE) {
            sharedAction.clearLastIdInfo();
        }
    }

    protected void varInit() {
        if (snackBar == null) {
            snackBar = new ColorSnackBar(context);
        }

        if (sharedAction == null) {
            sharedAction = new SharedAction(context);
        }
    }

    protected boolean checkNet() {
        varInit();
        if (!Methods.isNetworkAvailable(context)) {
            snackBar.show(context.getString(R.string.base_toast_net_down));
        }

        return Methods.isNetworkAvailable(context);
    }

//    protected void getStringRes(int resId) {
//        try {
//            if (context != null) {
//                context.getString(resId);
//            } else {
//                Log.e("Result", "没有给 context 初始化");
//            }
//        } catch (Exception exception) {
//            Log.e("Result", "没有找到对应的字符串资源ID");
//        }
//    }

    protected void showSnack(String snack_text) {
        if (snackBar == null) {
            snackBar = new ColorSnackBar(context);
        }

        snackBar.show(snack_text);
    }

}
