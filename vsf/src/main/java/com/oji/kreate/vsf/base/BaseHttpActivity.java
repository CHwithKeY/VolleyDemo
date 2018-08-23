package com.oji.kreate.vsf.base;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.oji.kreate.vsf.R;
import com.oji.kreate.vsf.publicAdapter.BaseRycAdapter;
import com.oji.kreate.vsf.publicClass.Methods;
import com.oji.kreate.vsf.publicClass.loadMore.RemoveLoadMoreImpl;
import com.oji.kreate.vsf.publicFragment.EmptyDataFragment;
import com.oji.kreate.vsf.publicFragment.NetDownFragment;
import com.oji.kreate.vsf.publicView.ColorSnackBar;
import com.oji.kreate.vsf.sharedInfo.SharedAction;
import com.oji.kreate.vsf.sharedInfo.SharedSet;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class BaseHttpActivity extends AppCompatActivity implements RemoveLoadMoreImpl, ErrorSet {

    static final int SET_HTTP_PARAMS_SUCCESS = 2000;
    static final int SET_HTTP_PARAMS_FAIL = 2100;

    protected Toolbar toolbar;

    protected SharedAction sharedAction;
    private SharedPreferences sp;

    //    private Snackbar snackbar;
    private ColorSnackBar snackBar;
    // cos = Count of SnackBar
    private int cos = 0;

    //
    private HashMap<String, String> httpParamsMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = this.getSharedPreferences(SharedSet.NAME, MODE_PRIVATE);
        sharedAction = new SharedAction();
        sharedAction.setShared(sp);

        snackBar = new ColorSnackBar(this);

        httpParamsMap = new HashMap<>();

        varInit();

        viewInit();

        setupToolbar();
    }

    public abstract void varInit();

    public abstract void viewInit();

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

    protected void setTbTitle(String title) {
        tbInit();
        toolbar.setTitle(title);
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

    protected void collapseIME(int viewResId) {
        View view = findViewById(viewResId);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Methods.collapseIME(BaseHttpActivity.this);
            }
        });
    }

    /**
     * @param parent_resId 如果当前 Activity 拥有 CoordinatorLayout 布局，那么可以直接导入该布局ID
     *                     如果使用默认布局，则直接输入0
     * @param text         Snack 所要提示的文本内容
     */
    public void showSnack(int parent_resId, String text) {
        if (snackBar == null) {
            snackBar = new ColorSnackBar(this);
        }

        snackBar.setSnackViewParent(parent_resId).show(text);

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
//            Log.e(getLocalClassName(), "SnackBar没有引入布局");
//            return;
//        }
//
//        Snackbar.SnackbarLayout snackbarView = (Snackbar.SnackbarLayout) snackbar.getView();
//        snackbarView.setBackgroundColor(0xFF7EC8DB);
//
//        ((TextView) snackbarView.findViewById(R.id.snackbar_text)).setTextColor(Color.parseColor("#FF2E4F92"));
//
//        snackbar.show();
    }

    protected void showEmptyView(int parent_id) {
        EmptyDataFragment emptyFragment = new EmptyDataFragment();

        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentByTag("emptyFragment");
        Log.i("Result", "frag is : " + fragment);
        if (fragment == null) {
            FragmentTransaction transaction = manager.beginTransaction();
//            transaction.replace(R.id.main_task_fl, emptyFragment, "emptyFragment");
            transaction.replace(parent_id, emptyFragment, "emptyFragment");
            transaction.commit();
        }
    }

    protected void removeEmptyView() {
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentByTag("emptyFragment");
        if (fragment != null) {
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.remove(fragment);
            transaction.commit();
        }
    }

    protected void showNetDownView(int parent_id) {
        NetDownFragment netDownFragment = new NetDownFragment();

        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentByTag("netDownFragment");
        Log.i("Result", "frag is : " + fragment);
        if (fragment == null) {
            FragmentTransaction transaction = manager.beginTransaction();
//            transaction.replace(R.id.main_task_fl, emptyFragment, "emptyFragment");
            transaction.replace(parent_id, netDownFragment, "netDownFragment");
            transaction.commit();
        }
    }

    protected void removeNetDownPage() {
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentByTag("netDownFragment");
        if (fragment != null) {
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.remove(fragment);
            transaction.commit();
        }
    }

    public abstract void handleNetDownAction();

    // 根据不同的Handler可以处理不同的反馈结果
    public abstract void onMultiHandleResponse(String tag, String result) throws JSONException;

    public abstract void onNullResponse(String tag) throws JSONException;

    public abstract void onPermissionAccepted(int permission_code);

    public abstract void onPermissionRefused(int permission_code);

    public int setHttpParams(String key, String value) {
        if (key != null && value != null && !key.isEmpty()) {
            httpParamsMap.put(key, value);

            return SET_HTTP_PARAMS_SUCCESS;
        }

        Log.e(getClass().getName(), SET_HTTP_PARAMS_KEY_WRONG);
        return SET_HTTP_PARAMS_FAIL;
    }

    public HashMap<String, String> getHttpParams() {
        return httpParamsMap;
    }

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
