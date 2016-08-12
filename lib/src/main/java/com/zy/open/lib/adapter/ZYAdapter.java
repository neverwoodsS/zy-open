package com.zy.open.lib.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 封装的便捷使用的 adapter
 * 配合包中的 ViewHolder
 * 供 ListView、GridView 使用
 * @param <T> Item 数据模型
 */
public abstract class ZYAdapter<T> extends BaseAdapter {
	
	protected List<T> mData;
	protected Context mContext;
	protected LayoutInflater mInflater;
	protected Map<Integer, Integer> mLayouts = new HashMap<>();

	public ZYAdapter(Context context, List<T> data, int layoutRes) {
		this.mData = data;
		this.mContext = context;
		this.mLayouts.put(0, layoutRes);
		
		this.mInflater = LayoutInflater.from(mContext);
	}

	public ZYAdapter(Context context, List<T> data, Map<Integer, Integer> layouts) {
		this.mData = data;
		this.mContext = context;
		this.mLayouts.putAll(layouts);

		this.mInflater = LayoutInflater.from(mContext);
	}

	/**
	 * 刷新 adapter
	 * 考虑到可能会发生数据完全改变的情况，故提供此方法
	 * @param data
     */
	public void refresh(List<T> data) {
		try {
			this.mData = data;
	        notifyDataSetChanged();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public T getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		try {
			if (convertView == null) {
				//以 ItemViewType 为 key 从 mLayouts 中取出布局文件的 id
				convertView = mInflater.inflate(mLayouts.get(getItemViewType(position)), parent, false);
			}

			//交由子类渲染布局
			setItem(convertView, getItem(position), position);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return convertView;
	}

	protected  <T extends View> T getChildView(View view, int id) {
		return ViewHolder.get(view, id);
	}

	@Override
	public int getItemViewType(int position) {
		return getItemViewType(position, getItem(position));
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

	/**
	 * itemTypeCount 由传入的布局数量决定
	 * @return
     */
	@Override
	public int getViewTypeCount() {
		return mLayouts.size();
	}

	protected abstract void setItem(View convertView, T data, int position);
}