package com.oji.kreate.vsf.publicFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oji.kreate.vsf.R;
import com.oji.kreate.vsf.base.BaseFragment;
import com.oji.kreate.vsf.publicClass.Methods;

import org.json.JSONException;


/**
 * Created by spilkaka on 2018/4/12.
 */

public class NetDownFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.page_public_net_down, container, false);
        int netdown_page_layout_id = Integer.parseInt(Methods.getSpecificProperty(getContext(), "netdown_page_layout_id"));

        View view = null;
        try {
            view = inflater.inflate(netdown_page_layout_id, container, false);
        } catch (Exception e) {
            view = inflater.inflate(R.layout.fragment_public_net_down_layout, container, false);
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onMultiHandleResponse(String s, String s1) throws JSONException {

    }

    @Override
    public void onNullResponse(String s) throws JSONException {

    }
}
