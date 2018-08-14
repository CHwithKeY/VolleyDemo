package com.oji.kreate.vsf.publicClass.loadMore;


import com.oji.kreate.vsf.publicAdapter.BaseRycAdapter;

import java.util.ArrayList;

/**
 * Created by lenovo on 2016/11/15.
 */

public interface RemoveLoadMoreImpl {

    void removeLoadMoreItem(BaseRycAdapter adapter, ArrayList newDataList, int refreshTime);
}
