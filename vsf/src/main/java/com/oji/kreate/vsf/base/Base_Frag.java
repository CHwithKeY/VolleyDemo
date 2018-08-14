package com.oji.kreate.vsf.base;

import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.TextView;

import com.oji.kreate.vsf.Public_ErrorSet;
import com.oji.kreate.vsf.R;
import com.oji.kreate.vsf.emptyPage.EmptyPage_Frag;
import com.oji.kreate.vsf.netDown.NetDown_Frag;
import com.oji.kreate.vsf.publicAdapter.BaseRycAdapter;
import com.oji.kreate.vsf.publicClass.loadMore.RemoveLoadMoreImpl;

import org.json.JSONException;

import java.util.ArrayList;


/**
 * Created by KeY on 2016/6/6.
 */
public abstract class Base_Frag extends Fragment implements RemoveLoadMoreImpl,Public_ErrorSet {

    protected String tag;

    private Snackbar snackbar;
    // cos = Count of SnackBar
    private int cos = 0;

    // 根据不同的Handler可以处理不同的反馈结果，最强大
    public abstract void onMultiHandleResponse(String tag, String result) throws JSONException;

    public abstract void onNullResponse(String tag) throws JSONException;

    public void setCustomTag(String tag) {
        this.tag = tag;
    }

    public String getCustomTag() {
        return tag;
    }

    public void showEmptyPage(int parent_resId) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        try {
            if (fragmentManager.findFragmentByTag("emptyPage_Frag") == null) {
                transaction.add(parent_resId, new EmptyPage_Frag(), "emptyPage_Frag");
            }
        } catch (Exception exception) {
            Log.e(getClass().getName(), NET_DOWN_PARENT_RES_ID_NOT_FOUND);
        }
        transaction.commit();
    }

    public void removeEmptyPage() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        try {
            if (fragmentManager.findFragmentByTag("emptyPage_Frag") != null) {
                transaction.remove(fragmentManager.findFragmentByTag("emptyPage_Frag"));
            }
        } catch (Exception exception) {
            Log.e(getClass().getName(), EMPTY_PAGE_PARENT_RES_ID_NOT_FOUND);
        }
        transaction.commit();
    }

    public void showNetDownPage(int parent_resId, String url, String responseTag, String response) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        NetDown_Frag netDown_frag = new NetDown_Frag();
        netDown_frag.setPing(url, responseTag, response);

        try {
            transaction.add(parent_resId, new NetDown_Frag(), "netDown_Frag");
        } catch (Exception exception) {
            Log.e(getClass().getName(), NET_DOWN_PARENT_RES_ID_NOT_FOUND);
        }
        transaction.commit();
    }

    public void removeNetDownPage() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        try {
            if (fragmentManager.findFragmentByTag("netDown_Frag") != null) {
                transaction.remove(fragmentManager.findFragmentByTag("netDown_Frag"));
            }
        } catch (Exception exception) {
            Log.e(getClass().getName(), NET_DOWN_PARENT_RES_ID_NOT_FOUND);
        }
        transaction.commit();
    }

    public void showSnack(int parent_resId, CharSequence text) {
        if (getView() != null) {
            if (parent_resId == 0) {
                parent_resId = R.id.snack_col;
            }

            CoordinatorLayout snack_col = getView().findViewById(parent_resId);

            if (snackbar != null) {
                snackbar = null;
                cos++;
                if (cos > 5) {
                    System.gc();
                    cos = 0;
                }
            }

            try {
                snackbar = Snackbar.make(snack_col, text, Snackbar.LENGTH_SHORT);
            } catch (NullPointerException exception) {
                Log.e(getClass().getName(), "");
                return;
            }

            Snackbar.SnackbarLayout snackbarView = (Snackbar.SnackbarLayout) snackbar.getView();
            snackbarView.setBackgroundColor(0xFF7EC8DB);
            ((TextView) snackbarView.findViewById(R.id.snackbar_text)).setTextColor(Color.parseColor("#FF2E4F92"));

            snackbar.show();
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
