package com.oji.kreate.vsf.base;

import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.TextView;

import com.oji.kreate.vsf.R;
import com.oji.kreate.vsf.publicAdapter.BaseRycAdapter;
import com.oji.kreate.vsf.publicClass.loadMore.RemoveLoadMoreImpl;
import com.oji.kreate.vsf.publicView.ColorSnackBar;

import org.json.JSONException;

import java.util.ArrayList;


/**
 * Created by KeY on 2016/6/6.
 */
public abstract class BaseFragment extends Fragment implements RemoveLoadMoreImpl {

    protected String tag;

    private ColorSnackBar snackBar;

    public BaseFragment() {
        snackBar = new ColorSnackBar(getContext());
    }

    // 根据不同的Handler可以处理不同的反馈结果，最强大
    public abstract void onMultiHandleResponse(String tag, String result) throws JSONException;

    public abstract void onNullResponse(String tag) throws JSONException;

    public void setCustomTag(String tag) {
        this.tag = tag;
    }

    public String getCustomTag() {
        return tag;
    }

    public void showSnack(int parent_resId, String text) {
        if (getView() != null) {
            if (snackBar == null) {
                snackBar = new ColorSnackBar(getContext());
            }

            snackBar.setSnackViewParent(parent_resId).show(text);
        }
    }

    //    public void removeLoadMoreItem(final BaseRycAdapter adapter, final ArrayList newDataList) {
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                adapter.setShowLoadItem(false);
//                ArrayList oldDataList = adapter.getDataList();
//
//                if (oldDataList != null && oldDataList.size() != 0 && oldDataList.get(oldDataList.size() - 1) == null) {
//                    oldDataList.remove(oldDataList.size() - 1);
//                }
//
//                if (newDataList != null) {
//                    oldDataList.addAll(newDataList);
//                }
//
//                adapter.notifyDataSetChanged();
//            }
//        }, 1500);
//    }

    @Override
    public void removeLoadMoreItem(final BaseRycAdapter adapter, final ArrayList newDataList, int refreshTime) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.setShowLoadItem(false);
                ArrayList oldDataList = adapter.getDataList();

                if (oldDataList != null && oldDataList.size() != 0 && oldDataList.get(oldDataList.size() - 1) == null) {
                    oldDataList.remove(oldDataList.size() - 1);
                }

                if (newDataList != null) {
                    oldDataList.addAll(newDataList);
                }

                adapter.notifyDataSetChanged();
            }
        }, refreshTime);
    }
}
