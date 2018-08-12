package com.example.leidong.common.widgets.recycler;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.leidong.common.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Lei Dong on 2018/8/12.
 */
public abstract class RecyclerAdapter<Data>
        extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder<Data>>
        implements View.OnLongClickListener, View.OnClickListener, AdapterCallback<Data> {
    private List<Data> mDataList = new ArrayList();
    private AdapterListener<Data> mListener;

    public RecyclerAdapter() {
        this(null);
    }

    public RecyclerAdapter(AdapterListener listener) {
        this(new ArrayList<Data>(), listener);
    }

    public RecyclerAdapter(List<Data> dataList, AdapterListener listener) {
        this.mDataList = dataList;
        this.mListener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        return getItemViewType(position, mDataList.get(position));
    }

    @Override
    public ViewHolder<Data> onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View rootView = layoutInflater.inflate(viewType, parent, false);
        ViewHolder<Data> holder = onCreateViewHolder(rootView, viewType);

        rootView.setTag(R.id.tag_recycler_holder, holder);

        rootView.setOnClickListener(this);
        rootView.setOnLongClickListener(this);

        holder.unbinder = ButterKnife.bind(holder, rootView);
        holder.callback = this;

        return holder;
    }

    @LayoutRes
    abstract int getItemViewType(int position, Data data);

    abstract ViewHolder<Data> onCreateViewHolder(View rootView, int viewType);

    @Override
    public void onBindViewHolder(@NonNull ViewHolder<Data> viewHolder, int i) {
        // 得到需要绑定的数据
        Data data = mDataList.get(i);
        // 触发holder的绑定方法
        viewHolder.bind(data);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Override
    public void onClick(View view) {
        ViewHolder viewHolder = (ViewHolder) view.getTag(R.id.tag_recycler_holder);
        if (this.mListener != null) {
            int pos = viewHolder.getAdapterPosition();
            this.mListener.onItemClick(viewHolder, mDataList.get(pos));
        }
    }

    @Override
    public boolean onLongClick(View view) {
        ViewHolder viewHolder = (ViewHolder) view.getTag(R.id.tag_recycler_holder);
        if (this.mListener != null) {
            int pos = viewHolder.getAdapterPosition();
            this.mListener.onItemLongClick(viewHolder, mDataList.get(pos));
            return true;
        }
        return false;
    }

    /**
     * 设置适配器监听
     *
     * @param adapterListener
     */
    public void setListener(AdapterListener<Data> adapterListener) {
        this.mListener = adapterListener;
    }

    /**
     * 自定义监听器
     *
     * @param <Data>
     */
    public interface AdapterListener<Data> {
        // 当条目点击的时候触发
        void onItemClick(RecyclerAdapter.ViewHolder viewHolder, Data data);
        // 当条目长按的时候触发
        void onItemLongClick(RecyclerAdapter.ViewHolder viewHolder, Data data);
    }

    /**
     * 添加
     *
     * @param data
     */
    public void add(Data data) {
        mDataList.add(data);
        notifyItemInserted(mDataList.size() - 1);
    }

    /**
     * 添加
     *
     * @param dataList
     */
    public void add(Collection<Data> dataList) {
        if (dataList != null && dataList.size() > 0) {
            int startPos = mDataList.size();
            mDataList.addAll(dataList);
            notifyItemRangeChanged(startPos, dataList.size());
        }
    }

    /**
     * 清空
     */
    public void clear() {
        mDataList.clear();
        notifyDataSetChanged();
    }

    /**
     * 更换
     *
     * @param dataList
     */
    public void replace(Collection<Data> dataList) {
        mDataList.clear();
        if (dataList == null || dataList.size() == 0) {
            mDataList.addAll(dataList);
            notifyDataSetChanged();
        }
    }

    /**
     * 自定义的ViewHolder
     *
     * @param <Data>
     */
    public static abstract class ViewHolder<Data> extends RecyclerView.ViewHolder {
        private Unbinder unbinder;
        protected Data mData;
        private AdapterCallback callback;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        void bind(Data data) {
            this.mData = data;
            onBind(data);
        }

        /**
         * 当触发绑定数据的时候,必须重写
         *
         * @param data
         */
        abstract void onBind(Data data);

        protected void updateData(Data data) {
            if (this.callback != null) {
                this.callback.update(data, this);
            }
        }
    }
}
