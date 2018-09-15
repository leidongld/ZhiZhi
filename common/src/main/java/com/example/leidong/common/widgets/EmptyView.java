package com.example.leidong.common.widgets;

import android.app.Application;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.leidong.common.R;
import com.example.leidong.common.widgets.convention.PlaceHolderView;

import net.qiujuer.genius.ui.widget.Loading;

/**
 * Created by Lei Dong on 2018/9/15.
 */
public class EmptyView extends LinearLayout implements PlaceHolderView {
    private ImageView mEmptyImg;
    private TextView mStatusText;
    private Loading mLoading;

    private int[] mDrawableIds = new int[]{0, 0};
    private int[] mTextIds = new int[]{0, 0, 0};

    private View[] mBindViews;

    public EmptyView(Context context) {
        super(context);
        init(null, 0);
    }

    public EmptyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public EmptyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    /**
     * 初始化
     *
     * @param attrs
     * @param defStyle
     */
    private void init(AttributeSet attrs, int defStyle) {
        inflate(getContext(), R.layout.lay_empty, this);
        mEmptyImg = (ImageView) findViewById(R.id.im_empty);
        mStatusText = (TextView) findViewById(R.id.txt_empty);
        mLoading = (Loading) findViewById(R.id.loading);

        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.EmptyView, defStyle, 0);

        mDrawableIds[0] = a.getInt(R.styleable.EmptyView_comEmptyDrawable, R.drawable.status_empty);
        mDrawableIds[1] = a.getInt(R.styleable.EmptyView_comErrorDrawable, R.drawable.status_empty);
        mTextIds[0] = a.getInt(R.styleable.EmptyView_comEmptyText, "Oh, it empty!");
        mTextIds[1] = a.getInt(R.styleable.EmptyView_comErrorText, "Sorry, you have error!");
        mTextIds[2] = a.getInt(R.styleable.EmptyView_comLoadingText, "Loading...");

        a.recycle();
    }

    /**
     * 绑定一些列数据显示的布局
     * 当前布局隐藏时（有数据时）自动显示绑定的数据布局
     * 当前数据加载时，自动显示Loading，并隐藏数据布局
     *
     * @param views
     */
    public void bind(View... views) {
        this.mBindViews = views;
    }

    /**
     * 更改绑定布局的显示状态
     *
     * @param visible
     */
    public void changeBindViewVisibility(int visible) {
        final View[] views = mBindViews;

        if (views == null || views.length == 0) {
            return;
        }

        for (View view : views) {
            view.setVisibility(visible);
        }
    }

    @Override
    public void triggerEmpty() {
        mLoading.setVisibility(GONE);
        mLoading.stop();
        mEmptyImg.setImageResource(mDrawableIds[0]);
        mStatusText.setText(mTextIds[0]);
        mEmptyImg.setVisibility(VISIBLE);
        setVisibility(VISIBLE);
        changeBindViewVisibility(GONE);
    }

    @Override
    public void triggerNetError() {
        mLoading.setVisibility(GONE);
        mLoading.stop();
        mEmptyImg.setImageResource(mDrawableIds[1]);
        mStatusText.setText(mTextIds[1]);
        mEmptyImg.setVisibility(VISIBLE);
        setVisibility(VISIBLE);
        changeBindViewVisibility(GONE);
    }

    @Override
    public void triggerError(int strRes) {
//        Application.showToast(strRes);
        setVisibility(VISIBLE);
        changeBindViewVisibility(GONE);
    }

    @Override
    public void triggerLoading() {
        mEmptyImg.setVisibility(GONE);
        mStatusText.setText(mTextIds[2]);
        setVisibility(VISIBLE);
        mLoading.setVisibility(VISIBLE);
        mLoading.start();
        changeBindViewVisibility(GONE);
    }

    @Override
    public void triggerOk() {
        setVisibility(GONE);
        changeBindViewVisibility(VISIBLE);
    }

    @Override
    public void triggerOkOrEmpty(boolean isOk) {
        if (isOk)
            triggerOk();
        else
            triggerEmpty();
    }
}
