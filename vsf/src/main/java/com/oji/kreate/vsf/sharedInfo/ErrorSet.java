package com.oji.kreate.vsf.sharedInfo;

/**
 * Created by Kreate on 2017/12/28.
 */

interface ErrorSet {

    String VALUE_CLASS_NOT_MATCH = "导入的 value 值不属于 int,float,boolean,string 中的任何一个";

    String KEY_NOT_MATCH = "导入的 key 值不在该 sp 文件中，请检查 sp 文件名或 key 值";

}
