package com.oji.kreate.vsf.publicFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oji.kreate.vsf.R;
import com.oji.kreate.vsf.base.BaseHttpFragment;
import com.oji.kreate.vsf.publicClass.Methods;

import org.json.JSONException;


/**
 * Created by spilkaka on 2018/4/12.
 */

public class EmptyDataFragment extends BaseHttpFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int empty_page_layout_id = Integer.parseInt(Methods.getSpecificProperty(getContext(), "empty_page_layout_id"));

        View view;
        try {
            view = inflater.inflate(empty_page_layout_id, container, false);
        } catch (Exception e) {
            view = inflater.inflate(R.layout.fragment_public_empty_data_layout, container, false);
        }

        return view;
        // return inflater.inflate(R.layout.page_public_empty_data, container, false);
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
