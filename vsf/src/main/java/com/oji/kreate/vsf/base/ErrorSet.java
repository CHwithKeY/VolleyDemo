package com.oji.kreate.vsf.base;

/**
 * Created by Kreate on 2017/12/27.
 */

interface ErrorSet {

    String SET_HTTP_PARAMS_KEY_WRONG = "Http的key值是null或者为空";

    String TITLE_COLOR_RES_ID_NOT_FOUND = "标题颜色的资源ID值为空或错误";

    String TITLE_BACK_BUTTON_NOT_FOUND = "没有引入返回键 button 布局";

    String HANDLE_JSON_STRING_WRONG = "Json获取httpKey失败，请检查httpKey是否正确或Json数据是否有该key";

}
