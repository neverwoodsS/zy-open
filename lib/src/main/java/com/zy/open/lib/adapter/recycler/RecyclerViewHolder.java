package com.zy.open.lib.adapter.recycler;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by zhangll on 16/8/12.
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    public SparseArray<View> viewCache;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        viewCache = new SparseArray<>();
    }

    public <T extends View> T getChildView(int id) {
        View child = viewCache.get(id);
        if (child == null) {
            child = itemView.findViewById(id);
            viewCache.put(id, child);
        }
        return (T) child;
    }

    public <T> void setOnItemClickListener(int viewId,
                                           final T data,
                                           final int position,
                                           final RecyclerAdapter.OnItemClickListener<T> onItemClickListener) {
        final View view = viewId == 0 ? itemView : getChildView(viewId);
        if (view != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClicked(view, data, position);
                }
            });
        }
    }

    public RecyclerViewHolder setText(int viewId, CharSequence charSequence) {
        TextView textView = getChildView(viewId);
        textView.setText(charSequence);
        return this;
    }

    public RecyclerViewHolder setTextColor(int viewId, int color) {
        TextView textView = getChildView(viewId);
        textView.setTextColor(color);
        return this;
    }

    public RecyclerViewHolder setTextColor(int viewId, ColorStateList color) {
        TextView textView = getChildView(viewId);
        textView.setTextColor(color);
        return this;
    }

    public RecyclerViewHolder setTextSize(int viewId, int textSize) {
        TextView textView = getChildView(viewId);
        textView.setTextSize(textSize);
        return this;
    }

    public RecyclerViewHolder setImageResource(int viewId, int imageId) {
        ImageView imageView = getChildView(viewId);
        imageView.setImageResource(imageId);
        return this;
    }

    public RecyclerViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView imageView = getChildView(viewId);
        imageView.setImageBitmap(bitmap);
        return this;
    }

    public RecyclerViewHolder setImageDrawable(int viewId, Drawable drawable) {
        ImageView imageView = getChildView(viewId);
        imageView.setImageDrawable(drawable);
        return this;
    }

    public RecyclerViewHolder setBackgroundColor(int viewId, int color) {
        getChildView(viewId).setBackgroundColor(color);
        return this;
    }

    public RecyclerViewHolder setBackgroundResource(int viewId, int backgroundResource) {
        getChildView(viewId).setBackgroundResource(backgroundResource);
        return this;
    }

    public RecyclerViewHolder setVisibility(int viewId, int visibility) {
        getChildView(viewId).setVisibility(visibility);
        return this;
    }

    public RecyclerViewHolder setTag(int viewId, Object tag) {
        getChildView(viewId).setTag(tag);
        return this;
    }

    public RecyclerViewHolder setTag(int viewId, int tagId, Object tag) {
        getChildView(viewId).setTag(tagId, tag);
        return this;
    }

    public RecyclerViewHolder setOnClickListener(int viewId, View.OnClickListener onClickListener) {
        getChildView(viewId).setOnClickListener(onClickListener);
        return this;
    }

    public RecyclerViewHolder setOnLongClickListener(int viewId, View.OnLongClickListener onLongClickListener) {
        getChildView(viewId).setOnLongClickListener(onLongClickListener);
        return this;
    }

    public RecyclerViewHolder setOnTouchListener(int viewId, View.OnTouchListener onTouchListener) {
        getChildView(viewId).setOnTouchListener(onTouchListener);
        return this;
    }
}