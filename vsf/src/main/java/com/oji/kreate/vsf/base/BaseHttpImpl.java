package com.oji.kreate.vsf.base;

public interface BaseHttpImpl {

    void onMultiHandleResponse(String tag, String result);

    void onNullResponse(String tag);

}
