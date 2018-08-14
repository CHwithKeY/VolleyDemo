package com.oji.kreate.vsf.base;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.oji.kreate.vsf.Public_ErrorSet;
import com.oji.kreate.vsf.R;
import com.oji.kreate.vsf.emptyPage.EmptyPage_Frag;
import com.oji.kreate.vsf.netDown.NetDown_Frag;
import com.oji.kreate.vsf.publicAdapter.BaseRycAdapter;
import com.oji.kreate.vsf.publicClass.loadMore.RemoveLoadMoreImpl;
import com.oji.kreate.vsf.sharedinfo.SharedAction;
import com.oji.kreate.vsf.sharedinfo.SharedSet;

import org.json.JSONException;

import java.util.ArrayList;

public abstract class Base_Act extends AppCompatActivity implements RemoveLoadMoreImpl, ErrorSet, Public_ErrorSet {

    protected Toolbar toolbar;

    protected SharedAction sharedAction;
    private SharedPreferences sp;

    private Snackbar snackbar;
    // cos = Count of SnackBar
    private int cos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = this.getSharedPreferences(SharedSet.NAME, MODE_PRIVATE);
        sharedAction = new SharedAction();
        sharedAction.setShared(sp);
    }

    public abstract void varInit();

    protected abstract void setupToolbar();

    private void tbInit() {
        toolbar = findViewById(R.id.toolbar_tb);
        // title_tv = (TextView) findViewById(R.id.toolbar_tv);

        toolbar.setTitle("");

        setSupportActionBar(toolbar);
    }

    /**
     * 重载了setTbTitle方法，和上一个方法不一样的地方在于，这个方法初始化了toolbar
     * 如果要单独设置setTbTitle，那就要先调用tbInit()
     *
     * @param title           要显示的标题文字
     * @param titleColorResId 要显示的标题颜色
     */
    protected void setTbTitle(String title, int titleColorResId) {
        tbInit();
        toolbar.setTitle(title);
        try {
            toolbar.setTitleTextColor(getResources().getColor(titleColorResId));
        } catch (Exception e) {
            Log.e(getLocalClassName(), TITLE_COLOR_RES_ID_NOT_FOUND);
        }
    }

    /**
     * 激活左上角的返回按钮
     */
    protected void setTbNavigation() {
        toolbar.setNavigationIcon(R.drawable.ic_toolbar_nav_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected void setBackBtn() {
        try {
            ImageButton back_btn = findViewById(R.id.public_back_imgbtn);
            back_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        } catch (Exception exception) {
            Log.e(getLocalClassName(), TITLE_BACK_BUTTON_NOT_FOUND);
        }
    }


    /**
     * @param parent_resId 如果当前 Activity 拥有 CoordinatorLayout 布局，那么可以直接导入该布局ID
     *                     如果使用默认布局，则直接输入0
     * @param text         Snack 所要提示的文本内容
     */
    public void showSnack(int parent_resId, CharSequence text) {
        if (parent_resId == 0) {
            parent_resId = R.id.snack_col;
        }

        CoordinatorLayout snack_col = findViewById(parent_resId);

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
            Log.e(getLocalClassName(), SNACK_NOT_IMPORT);
            return;
        }

        Snackbar.SnackbarLayout snackbarView = (Snackbar.SnackbarLayout) snackbar.getView();
        snackbarView.setBackgroundColor(0xFF7EC8DB);

        ((TextView) snackbarView.findViewById(R.id.snackbar_text)).setTextColor(Color.parseColor("#FF2E4F92"));

        snackbar.show();
    }


    /**
     * @param parent_resId 如果需要展示断网画面，输入当前 Activity 的根布局
     * @param url          断网后检测是否 ping 的通的url
     */
    public void showNetDownPage(int parent_resId, String url, String responseTag, String response) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        try {
            NetDown_Frag netDown_frag = new NetDown_Frag();
            netDown_frag.setPing(url, responseTag, response);
            transaction.add(parent_resId, netDown_frag, "netdown_frag");
        } catch (Exception exception) {
            Log.e(getLocalClassName(), NET_DOWN_PARENT_RES_ID_NOT_FOUND);
        }
        transaction.commit();
    }

    /**
     * @param parent_resId 如果需要展示断网画面，输入当前 Activity 的根布局
     */
    public void showEmptyPage(int parent_resId) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        try {
            transaction.add(parent_resId, new EmptyPage_Frag(), "emptypage_frag");
        } catch (Exception exception) {
            Log.e(getLocalClassName(), EMPTY_PAGE_PARENT_RES_ID_NOT_FOUND);
        }
        transaction.commit();
    }

    // 根据不同的Handler可以处理不同的反馈结果
    public abstract void onMultiHandleResponse(String tag, String result) throws JSONException;

    public abstract void onNullResponse(String tag) throws JSONException;

    public abstract void onPermissionAccepted(int permission_code);

    public abstract void onPermissionRefused(int permission_code);

    /**
     * @param adapter     导入当前 Activity 所拥有的 adapter
     * @param newDataList 导入新的数据List
     * @param refreshTime 刷新界面时间
     */
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
