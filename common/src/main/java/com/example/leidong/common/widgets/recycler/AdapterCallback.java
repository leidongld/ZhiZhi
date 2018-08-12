package com.example.leidong.common.widgets.recycler;

/**
 * Created by Lei Dong on 2018/8/12.
 */
public interface AdapterCallback<Data> {
    void update(Data data, RecyclerAdapter.ViewHolder<Data> holder);
}
