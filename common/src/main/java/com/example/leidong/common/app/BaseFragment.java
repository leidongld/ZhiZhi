package com.example.leidong.common.app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Lei Dong on 2018/8/12.
 */
public abstract class BaseFragment extends android.support.v4.app.Fragment {
    protected View mRootView;
    protected Unbinder mRootUnbinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // 初始化參數
        initArgs(getArguments());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            int layoutId = getContentLayoutId();
            // 初始化当前的根布局
            View rootView = inflater.inflate(layoutId, container, false);
            initWidget(rootView);
            mRootView = rootView;
        } else {
            if (mRootView.getParent() != null) {
                // 把当前的Root从其父控件中移除
                ((ViewGroup) mRootView.getParent()).removeView(mRootView);
            }
        }
        return mRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 当View创建完成后初始化数据
        initData();
    }

    /**
     * @param bundle
     * @return
     */
    protected void initArgs(Bundle bundle) {
    }

    /**
     * 得到当前界面的资源文件Id
     *
     * @return
     */
    @LayoutRes
    protected abstract int getContentLayoutId();

    /**
     * 初始化组件
     *
     * @param rootView
     */
    protected void initWidget(View rootView) {
        mRootUnbinder = ButterKnife.bind(this, rootView);
    }

    /**
     * 初始化数据
     */
    protected void initData() {

    }

    /**
     * 返回按鍵触发时调用
     *
     * @return
     */
    public boolean onBackPressed() {
        return false;
    }
}
