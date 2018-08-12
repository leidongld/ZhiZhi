package com.example.leidong.zhizhi;

import android.widget.TextView;

import com.example.leidong.common.app.BaseActivity;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    @BindView(R.id.text)
    TextView textView;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        textView.setText("hahaha");
    }
}
