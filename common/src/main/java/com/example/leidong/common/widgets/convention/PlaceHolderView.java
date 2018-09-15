package com.example.leidong.common.widgets.convention;

import android.support.annotation.StringRes;

/**
 * Created by Lei Dong on 2018/9/15.
 */
public interface PlaceHolderView {
    /**
     * 没有数据显示空布局
     */
    void triggerEmpty();

    /**
     * 网络错误显示一个网络错误的图标
     */
    void triggerNetError();

    /**
     * 加载错误并且显示错误位置
     *
     * @param strRes 错误信息
     */
    void triggerError(@StringRes int strRes);

    /**
     * 显示正在加载的状态
     */
    void triggerLoading();

    /**
     * 数据加载成功
     * 调用该方法应该隐藏当前的站位布局
     */
    void triggerOk();

    /**
     * 该方法如果传入的isOk为true则成功
     * 此时隐藏布局，否则显示空数据布局
     *
     * @param isOk
     */
    void triggerOkOrEmpty(boolean isOk);
}
