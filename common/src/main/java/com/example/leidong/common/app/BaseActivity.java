package com.example.leidong.common.app;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Lei Dong on 2018/8/12.
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 在界面初始化之前调用的初始话化数据
        initWindows();

        if (initArgs(getIntent().getExtras())) {
            int layoutId = getContentLayoutId();
            setContentView(layoutId);
            initWidget();
            initData();
        } else {
            finish();
        }
    }

    /**
     * 初始化窗口数据
     */
    protected void initWindows() {

    }

    /**
     * @param bundle
     * @return
     */
    protected boolean initArgs(Bundle bundle) {
        return true;
    }

    /**
     * 获取当前界面资源文件Id
     *
     * @return
     */
    @LayoutRes
    protected abstract int getContentLayoutId();

    /**
     * 初始化组件
     */
    protected void initWidget() {
        ButterKnife.bind(this);
    }

    /**
     * 初始化数据
     */
    protected void initData() {

    }

    @Override
    public boolean onSupportNavigateUp() {
        // 当点击界面导航返回时，Finish当前界面
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        // 得到当前Activity下的所有Fragment
        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        // 判断是否为空
        if (!fragmentList.isEmpty()) {
            for (Fragment fragment : fragmentList) {
                // 判断是否为我们能够处理的Fragment类型
                if (fragment instanceof BaseFragment) {
                    // 判断是否拦截了返回按钮
                    if (((BaseFragment) fragment).onBackPressed()) {
                        return;
                    }
                }
            }
        }
        super.onBackPressed();
        finish();
    }
}
