package com.zy.open.lib.adapter.deprecated;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.zy.open.lib.adapter.recycler.RecyclerViewHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangll on 16/8/12.
 */
public abstract class RecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder> {

    protected Context mContext;
    protected List<T> mData;
    protected LayoutInflater mInflater;
    protected Map<Integer, Integer> mLayouts = new HashMap<>();

    public RecyclerAdapter(Context context, List<T> data, int layoutRes) {
        this.mData = data;
        this.mContext = context;
        this.mLayouts.put(0, layoutRes);

        this.mInflater = LayoutInflater.from(mContext);
    }

    public RecyclerAdapter(Context context, List<T> data, Map<Integer, Integer> layouts) {
        this.mData = data;
        this.mContext = context;
        this.mLayouts.putAll(layouts);

        this.mInflater = LayoutInflater.from(mContext);
    }

    public void refresh(List<T> data) {
        try {
            this.mData = data;
            notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(mInflater.inflate(mLayouts.get(viewType), parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        onBindData(holder, mData.get(position), position);
    }

    protected abstract void onBindData(RecyclerViewHolder holder, T data, int position);

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return getItemViewType(position, mData.get(position));
    }

    /**
     * 由子类处理，默认返回 super.getItemViewType(position)
     * @param position
     * @param data
     * @return
     */
    protected int getItemViewType(int position, T data) {
        return super.getItemViewType(position);
    }
}