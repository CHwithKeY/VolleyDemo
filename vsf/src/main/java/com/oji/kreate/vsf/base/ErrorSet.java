package com.oji.kreate.vsf.base;

/**
 * Created by Kreate on 2017/12/27.
 */

interface ErrorSet {

    // BaseHttpActivity
    String SET_HTTP_PARAMS_KEY_WRONG = "Http的key值是null或者为空---Http params key is null or empty.";

    String TITLE_COLOR_RES_ID_NOT_FOUND = "标题颜色的资源ID值为空或错误---Title color resource id is wrong or empty.";

    String TITLE_BACK_BUTTON_NOT_FOUND = "没有引入返回键 button 布局---Back button is not imported.";

    String COLLAPSE_VIEW_IMPORTED_WRONG = "要点击回收输入法的view导入有问题，请确认该view的存在---The imported view to collapse IME is wrong, please check if the view is exist.";

    // BaseHttpAction
    String HANDLE_JSON_STRING_WRONG = "Json获取httpKey失败，请检查httpKey是否正确或Json数据是否有该key---Json got httpKey failed, please check if httpKey was right.";

    String HANDLE_JSON_OBJECT_WRONG = "获取到的JsonObject有问题---The gotten json object was wrong.";

    // BaseHttpObject
    String NOT_FIND_OBJECT_SPECIFIC_PARAMS_NAME_VALUE = "没有在JsonObject找到指定Object的参数对应的值---Not find object params name in JsonObject.";

}
