package com.oji.kreate.vsf.base;

import android.content.Context;
import android.util.Log;

import com.oji.kreate.vsf.publicView.ColorSnackBar;
import com.oji.kreate.vsf.sharedInfo.SharedAction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by KeY on 2016/6/3.
 */
public class BaseHttpAction implements ErrorSet {

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

    public BaseHttpAction(Context context) {
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

    protected ArrayList<BaseHttpObject> handleResponseList(String response, BaseHttpObject object) throws JSONException {
        ArrayList<BaseHttpObject> objectList = new ArrayList<>();

        JSONArray array = new JSONArray(response);

        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);
            objectList.add(object.wrap(obj));
        }

        return objectList;
    }

    protected BaseHttpObject handleResponseObject(String response, BaseHttpObject object) throws JSONException {
        try {
            JSONObject obj = new JSONObject(response);
            return object.wrap(obj);
        } catch (Exception e) {
            Log.e(getClass().getName(), HANDLE_JSON_OBJECT_WRONG);
            return null;
        }
    }

    protected String handleResponseString(String response, String httpKey) throws JSONException {
        JSONObject object = new JSONObject(response);
        try {
            return object.getString(httpKey);
        } catch (Exception e) {
            Log.e(getClass().getName(), HANDLE_JSON_STRING_WRONG);
            return "";
        }
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
