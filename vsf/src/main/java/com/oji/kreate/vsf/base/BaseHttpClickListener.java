package com.oji.kreate.vsf.base;

import android.content.Context;
import android.view.View;

import com.oji.kreate.vsf.sharedInfo.SharedAction;

/**
 * Created by KeY on 2016/6/5.
 */
public abstract class BaseHttpClickListener implements View.OnClickListener {

    protected Context context;
    protected SharedAction sharedAction;

    public BaseHttpClickListener(Context context, BaseHttpAction baseAction) {
        this.context = context;

        sharedAction = new SharedAction(context);
    };

    @Override
    public abstract void onClick(View v);
}
