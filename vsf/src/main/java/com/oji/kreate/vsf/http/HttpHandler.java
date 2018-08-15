package com.oji.kreate.vsf.http;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.oji.kreate.vsf.base.BaseActivity;
import com.oji.kreate.vsf.base.BaseFragment;
import com.oji.kreate.vsf.base.BaseService;
import com.oji.kreate.vsf.publicClass.Methods;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by KeY on 2016/3/29.
 */
public final class HttpHandler extends Handler {

    private Context context;

    private BaseFragment fragment;
    private BaseService service;

    // Activity的请求时候的构造函数
    public HttpHandler(Context context) {
        this.context = context;
    }

    // Fragment的请求时候的构造函数
    public HttpHandler(Context context, BaseFragment fragment) {
        this.context = context;
        this.fragment = fragment;
    }

    // Service的请求时候的构造函数
    public HttpHandler(Context context, BaseService service) {
        this.context = context;
        this.service = service;
    }

    public void handleResponse(String tag, String result) throws JSONException {
        // 根据判断决定是哪一方的请求
        if (fragment != null) {
            fragment.onMultiHandleResponse(tag, result);
        } else if (service != null) {
            service.onMultiHandleResponse(tag, result);
        } else {
            ((BaseActivity) context).onMultiHandleResponse(tag, result);
        }
    }

    public void handleNullMsg(String tag) throws JSONException {
        if (fragment != null) {
            fragment.onNullResponse(tag);
        } else if (service != null) {
            service.onNullResponse(tag);
        } else {
            ((BaseActivity) context).onNullResponse(tag);
        }
    }

    @Override
    public void handleMessage(Message msg) {

        switch (msg.what) {
            case HttpSet.httpResponse:
                HashMap<String, String> map = Methods.cast(msg.obj);

                String tag = "";
                Set set = map.keySet();
                for (Object aSet : set) {
                    tag = (String) aSet;
                }

                String result = map.get(tag);

                try {
                    handleResponse(tag, result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case HttpSet.httpNull:
                String null_tag = Methods.cast(msg.obj);
                try {
                    handleNullMsg(null_tag);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            default:
                break;
        }
    }

}
