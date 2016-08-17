package com.zy.open.lib.adapter.recycler;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by zhangll on 16/8/12.
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    public RecyclerViewHolder(View itemView) {
        super(itemView);
    }

    public <T extends View> T getChildView(int id) {
        return (T) itemView.findViewById(id);
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