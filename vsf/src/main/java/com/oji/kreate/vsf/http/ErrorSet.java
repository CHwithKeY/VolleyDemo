package com.oji.kreate.vsf.http;

public interface ErrorSet {

    String NET_DOWN = "网络不可用，请检查网络";
    String NET_DISCONNECT = "网络连接不稳定，请稍后再试";

    String PARAMS_IS_NULL_EMPTY = "参数不得为null或为空---Params is null or empty.";
    String DEFAULT_CONNECT_WRONG = "DC失败，DC只能用于AppCompatActivity中，Fragment和Service中无法使用！---Default connect failed. DC can only used in AppCompatActivity";
}
