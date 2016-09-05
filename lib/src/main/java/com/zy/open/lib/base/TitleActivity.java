package com.zy.open.lib.base;

import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.zy.open.lib.util.DisplayUtil;
import com.zy.open.lib.view.title.TitleView;

/**
 * Created by zhangll on 16/9/5.
 */
public abstract class TitleActivity extends ZYActivity {

    private LinearLayout container;
    private TitleView titleView;
    private View contentView;

    protected abstract void setTitle(TitleView titleView);

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        contentView = layoutInflater.inflate(layoutResID, null);
        setContentView();
    }

    @Override
    public void setContentView(View view) {
        contentView = view;
        setContentView();
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        contentView = view;
        contentView.setLayoutParams(params);
        setContentView();
    }

    private void setContentView() {
        container = new LinearLayout(this);
        container.setOrientation(LinearLayout.VERTICAL);

        //添加标题
        titleView = new TitleView(this);
        LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                DisplayUtil.dip2px(44));
        container.addView(titleView, titleParams);

        //添加内容
        container.addView(contentView);

        //将容器提交给父类
        super.setContentView(container);

        //委托子类详细设置 title
        setTitle(titleView);
    }

    protected View getTitleView() {
        return titleView;
    }

    protected View getContentView() {
        return contentView;
    }
}