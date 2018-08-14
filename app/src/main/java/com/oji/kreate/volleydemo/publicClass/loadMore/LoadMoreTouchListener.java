package com.oji.kreate.volleydemo.publicClass.loadMore;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import com.oji.kreate.volleydemo.publicAdapter.BaseRycAdapter;
import com.oji.kreate.volleydemo.publicClass.ScreenSize;

import java.util.ArrayList;

/**
 * Created by lenovo on 2016/11/14.
 */

public class LoadMoreTouchListener implements View.OnTouchListener {

    private float downY = 0;

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        RecyclerView recyclerView = (RecyclerView) view;

        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = motionEvent.getY();
                break;

            case MotionEvent.ACTION_UP:
                float upY = motionEvent.getY();
                BaseRycAdapter adapter = (BaseRycAdapter) recyclerView.getAdapter();

                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                ArrayList dataList = adapter.getDataList();

                if (dataList.size() == 0 || dataList.size() == 1) {
                    return false;
                }
                int recyclerViewHeight = recyclerView.getHeight();
                int screenHeight = new ScreenSize(recyclerView.getContext()).getHeight();

                int itemHeight;
                try {
                    itemHeight = recyclerViewHeight / (manager.findLastCompletelyVisibleItemPosition() - manager.findFirstCompletelyVisibleItemPosition());
                } catch (ArithmeticException exception1) {
                    try {
                        itemHeight = recyclerViewHeight / (manager.findLastVisibleItemPosition() - manager.findFirstVisibleItemPosition());
                    } catch (ArithmeticException exception2) {
                        return false;
                    }
                }
                int totalHeight = itemHeight * recyclerView.getChildCount();

                if ((downY - upY > 200)
                        && (!adapter.isShowLoadItem())
                        && (manager.findLastCompletelyVisibleItemPosition() == dataList.size() - 1)
                        && (totalHeight > screenHeight)) {
                    dataList.add(null);
                    adapter.notifyDataSetChanged();
                    adapter.setShowLoadItem(true);
                }

                break;
            default:
                break;
        }
        return false;
    }

}
