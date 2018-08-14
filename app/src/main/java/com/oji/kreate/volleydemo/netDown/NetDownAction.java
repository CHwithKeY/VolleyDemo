package com.oji.kreate.volleydemo.netDown;

import android.content.Context;

import com.oji.kreate.volleydemo.R;
import com.oji.kreate.volleydemo.base.BaseAction;
import com.oji.kreate.volleydemo.base.Base_Frag;
import com.oji.kreate.volleydemo.http.HttpAction;
import com.oji.kreate.volleydemo.http.HttpHandler;
import com.oji.kreate.volleydemo.http.HttpSet;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by KeY on 2016/11/4.
 */

final class NetDownAction extends BaseAction implements NetDownSet {

    private Base_Frag base_frag;

    NetDownAction(Context context, Base_Frag base_frag) {
        super(context);
        this.base_frag = base_frag;
    }

    void ping(String url) {
        if (!checkNet()) {
            return;
        }

        String[] key = {""};
        String[] value = {""};

        HttpAction action = new HttpAction(context);
        action.setUrl(url);
        action.setMap(key, value);
        action.setHandler(new HttpHandler(context, base_frag));
        action.setDialog(REFRESH_TITLE, REFRESH_MSG);
        action.interaction();
    }
}
