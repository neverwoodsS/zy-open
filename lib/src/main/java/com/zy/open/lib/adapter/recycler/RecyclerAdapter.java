package com.zy.open.lib.adapter.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangll on 16/8/12.
 */
public class RecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder> {

    protected Context mContext;
    protected List<T> mData;
    protected LayoutInflater mInflater;
    protected SparseArray<AdapterDelegate<T>> delegates = new SparseArray<>();

    protected SparseArray<OnItemClickListener<T>> listeners = new SparseArray<>();

    protected int layoutRes;

    public RecyclerAdapter(Context context) {
        this.mData = new ArrayList<>();
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
    }

    public RecyclerAdapter(Context context, List<T> data) {
        this.mData = data;
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
    }

    public RecyclerAdapter(Context context, List<T> data, int layoutRes) {
        this.mData = data;
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.layoutRes = layoutRes;
    }

    public RecyclerAdapter(Context context, List<T> data, AdapterDelegate<T> delegate) {
        this.mData = data;
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        delegates.put(0, delegate);
    }

    public RecyclerAdapter<T> addDelegate(int type, AdapterDelegate<T> delegate) {
        delegates.put(type, delegate);
        return this;
    }

    public RecyclerAdapter<T> addDelegate(AdapterDelegate<T> delegate) {
        return addDelegate(0, delegate);
    }

    public void refresh(List<T> data) {
        try {
            this.mData = data;
            notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setOnItemClickListener(int viewId, OnItemClickListener<T> onItemClickListener) {
        listeners.put(viewId, onItemClickListener);
    }

    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        setOnItemClickListener(0, onItemClickListener);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (delegates.size() == 0) {
            return new RecyclerViewHolder(mInflater.inflate(layoutRes,
                    parent,
                    false));
        } else {
            return new RecyclerViewHolder(mInflater.inflate(delegates.get(viewType).getLayoutId(),
                    parent,
                    false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        T data = mData.get(position);
        if (delegates.size() == 0) {
            bind(holder, mData.get(position), position);
        } else {
            AdapterDelegate<T> delegate = delegates.get(getItemViewType(position));
            delegate.bind(holder, data, position);
        }
        setListeners(holder, data, position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return getItemViewType(mData.get(position));
    }

    /**
     * 由子类处理，默认返回 0
     * @param data
     * @return
     */
    protected int getItemViewType(T data) {
        return 0;
    }

    /**
     * 单类型时子类需实现的方法
     * 处理绑定 view
     * @param holder
     * @param data
     * @param position
     */
    protected void bind(RecyclerViewHolder holder, T data, int position) {

    }

    private void setListeners(RecyclerViewHolder holder, T data, int position) {
        int count = listeners.size();
        for (int i = count - 1; i >= 0; i--) {
            int viewId = listeners.keyAt(i);
            OnItemClickListener onItemClickListener = listeners.valueAt(i);
            holder.setOnItemClickListener(viewId, data, position, onItemClickListener);
        }
    }

    public interface OnItemClickListener<T> {
        void onItemClicked(View view, T data, int position);
    }
}