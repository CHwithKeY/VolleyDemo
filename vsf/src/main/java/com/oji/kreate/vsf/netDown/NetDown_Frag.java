//package com.oji.kreate.vsf.netDown;
//
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentTransaction;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//
//import com.oji.kreate.vsf.R;
//import com.oji.kreate.vsf.base.Base_Act;
//import com.oji.kreate.vsf.base.Base_Frag;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
///**
// * Created by KeY on 2016/11/4.
// */
//
//public final class NetDown_Frag extends Base_Frag implements View.OnClickListener, NetDownSet {
//
//    private NetDownAction netDownAction;
//
//    private String url;
//    private String responseTag;
//    private String response;
//
//    public void setPing(String url, String responseTag, String response) {
//        this.url = url;
//        this.responseTag = responseTag;
//        this.response = response;
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_public_net_down_layout, container, false);
//    }
//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        varInit();
//
//        setupRefreshImage(view);
//    }
//
//    private void varInit() {
//        netDownAction = new NetDownAction(getContext(), this);
//    }
//
//    private void setupRefreshImage(View view) {
//        ImageView refresh_img = view.findViewById(R.id.net_down_refresh_img);
//        refresh_img.setOnClickListener(this);
//    }
//
//    @Override
//    public void onMultiHandleResponse(String tag, String result) throws JSONException {
//        JSONObject obj = new JSONObject(result);
//        String ping_response = obj.getString(responseTag);
//
//        if (response.equals(ping_response)) {
//            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//            FragmentTransaction transaction = fragmentManager.beginTransaction();
//            transaction.remove(this);
//            transaction.commit();
//
//            ((Base_Act) getActivity()).varInit();
//        } else {
//            Log.e(getClass().getName(), ERROR_PING_BLOCK + " and response is : " + ping_response);
//        }
//    }
//
//    @Override
//    public void onNullResponse(String tag) throws JSONException {
//    }
//
//    @Override
//    public void setCustomTag(String tag) {
//    }
//
//    @Override
//    public String getCustomTag() {
//        return null;
//    }
//
//    @Override
//    public void onClick(View v) {
//        netDownAction.ping(url);
//    }
//}
