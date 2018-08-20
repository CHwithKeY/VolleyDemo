package com.oji.kreate.vsf.publicFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.oji.kreate.vsf.R;
import com.oji.kreate.vsf.base.BaseHttpActivity;
import com.oji.kreate.vsf.base.BaseHttpFragment;
import com.oji.kreate.vsf.publicClass.Methods;

import org.json.JSONException;


/**
 * Created by spilkaka on 2018/4/12.
 */

public class NetDownFragment extends BaseHttpFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.page_public_net_down, container, false);
        int netdown_page_layout_id = Integer.parseInt(Methods.getSpecificProperty(getContext(), "netdown_page_layout_id"));

        View view;
        try {
            view = inflater.inflate(netdown_page_layout_id, container, false);
        } catch (Exception e) {
            view = inflater.inflate(R.layout.fragment_public_net_down_layout, container, false);
        }

        return view;
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupRefreshImg(view);
    }

    private void setupRefreshImg(View view) {
        Log.i("Result", "view is : " + view + "----- getView is : " + getContext().getResources().getLayout(R.layout.fragment_public_net_down_layout));
        if (view == getContext().getResources().getLayout(R.layout.fragment_public_net_down_layout)) {
            final ImageView refresh_img = view.findViewById(R.id.frgment_nd_refresh_img);
            refresh_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((BaseHttpActivity) getContext()).handleNetDownAction();
                }
            });
        }
    }

    @Override
    public void onMultiHandleResponse(String s, String s1) throws JSONException {

    }

    @Override
    public void onNullResponse(String s) throws JSONException {

    }
}
