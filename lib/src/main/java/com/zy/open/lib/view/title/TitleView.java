package com.zy.open.lib.view.title;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.zy.open.lib.util.DisplayUtil;

/**
 * Created by zhangll on 16/4/11.
 */
public class TitleView extends RelativeLayout implements TitleViewInterface {

    private static int backGroundColor = Color.RED;
    private static int backArrowRes;
    private static int iconSize = 20;
    private static int marginSide = 10;

    private static int textColor = Color.WHITE;
    private static int sideTextSize = 14;
    private static int centerTextSize = 16;

    private ImageView leftIv;
    private TextView leftTv;
    private ViewGroup leftGroup;

    private ImageView rightIv;
    private TextView rightTv;
    private ViewGroup rightGroup;

    private ImageView centerIv;
    private TextView centerTv;
    private ViewGroup centerGroup;

    //部分参数设置默认值的方法

    public static void setDefaultLeftImage(int backArrowRes) {
        TitleView.backArrowRes = backArrowRes;
    }

    public static void setDefaultBackgroundColor(int backGroundColor) {
        TitleView.backGroundColor = backGroundColor;
    }

    public static void setDefaultIconSize(int iconSize) {
        TitleView.iconSize = iconSize;
    }

    public static void setDefaultMarginSide(int marginSide) {
        TitleView.marginSide = marginSide;
    }

    public static void setDefaultTextColor(int textColor) {
        TitleView.textColor = textColor;
    }

    public static void setDefaultSideTextSize(int sideTextSize) {
        TitleView.sideTextSize = sideTextSize;
    }

    public static void setDefaultCenterTextSize(int centerTextSize) {
        TitleView.centerTextSize = centerTextSize;
    }


    public TitleView(Context context) {
        super(context);
        setBackgroundColor(backGroundColor);
        addChildViews();
        setTitleStyle(TitleStyle.LEFT_ONLY);
        setLeftImage(backArrowRes);
    }

    @Override
    public void setTitleStyle(TitleStyle titleStyle) {
        switch (titleStyle) {
            case LEFT_ONLY:
                setLeftVisible(true);
                setRightVisible(false);
                break;
            case RIGHT_ONLY:
                setLeftVisible(false);
                setRightVisible(true);
                break;
            case BOTH:
                setLeftVisible(true);
                setRightVisible(true);
                break;
            case NETHER:
                setLeftVisible(false);
                setRightVisible(false);
                break;
        }
    }

    @Override
    public void setLeftText(CharSequence leftText) {
        leftTv.setText(leftText);
        leftTv.setVisibility(VISIBLE);
        leftIv.setVisibility(GONE);
    }

    @Override
    public void setLeftImage(int leftImage) {
        leftIv.setImageResource(leftImage);
        leftTv.setVisibility(GONE);
        leftIv.setVisibility(VISIBLE);
    }

    @Override
    public void setLeftVisible(boolean leftVisible) {
        leftGroup.setVisibility(leftVisible ? VISIBLE : GONE);
    }

    @Override
    public void setRightText(CharSequence rightText) {
        rightTv.setText(rightText);
        rightTv.setVisibility(VISIBLE);
        rightIv.setVisibility(GONE);
    }

    @Override
    public void setRightImage(int rightImage) {
        rightIv.setImageResource(rightImage);
        rightTv.setVisibility(GONE);
        rightIv.setVisibility(VISIBLE);
    }

    @Override
    public void setRightVisible(boolean rightVisible) {
        rightGroup.setVisibility(rightVisible ? VISIBLE : GONE);
    }

    @Override
    public void setCenterText(CharSequence centerText) {
        centerTv.setText(centerText);
        centerTv.setVisibility(VISIBLE);
        centerIv.setVisibility(GONE);
    }

    @Override
    public void setCenterImage(int centerImage) {
        centerIv.setImageResource(centerImage);
        centerTv.setVisibility(GONE);
        centerIv.setVisibility(VISIBLE);
    }

    @Override
    public void setCenterVisible(boolean centerVisible) {
        centerGroup.setVisibility(centerVisible ? VISIBLE : GONE);
    }

    @Override
    public void setOnLeftClickListener(OnClickListener onClickListener) {
        leftGroup.setOnClickListener(onClickListener);
    }

    @Override
    public void setOnRightClickListener(OnClickListener onClickListener) {
        rightGroup.setOnClickListener(onClickListener);
    }

    @Override
    public void setOnCenterClickListener(OnClickListener onClickListener) {
        centerGroup.setOnClickListener(onClickListener);
    }

    private void addChildViews() {
        addLeftGroup();
        addRightGroup();
        addCenterGroup();
    }

    private void addLeftGroup() {
        RelativeLayout layout = new RelativeLayout(getContext());

        leftTv = new TextView(getContext());
        leftTv.setTextSize(sideTextSize);
        leftTv.setTextColor(textColor);

        leftIv = new ImageView(getContext());

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = DisplayUtil.dip2px(marginSide);
        params.addRule(RelativeLayout.CENTER_VERTICAL);
        layout.addView(leftTv, params);

        int pxSize = DisplayUtil.dip2px(iconSize);
        LayoutParams iconParams = new LayoutParams(pxSize, pxSize);
        iconParams.leftMargin = DisplayUtil.dip2px(marginSide);
        iconParams.addRule(RelativeLayout.CENTER_VERTICAL);
        layout.addView(leftIv, iconParams);

        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(layout, layoutParams);

        leftGroup = layout;
    }

    private void addRightGroup() {
        RelativeLayout layout = new RelativeLayout(getContext());

        rightTv = new TextView(getContext());
        rightTv.setTextSize(sideTextSize);
        rightTv.setTextColor(textColor);

        rightIv = new ImageView(getContext());

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.rightMargin = DisplayUtil.dip2px(marginSide);
        params.addRule(RelativeLayout.CENTER_VERTICAL);
        layout.addView(rightTv, params);

        int pxSize = DisplayUtil.dip2px(iconSize);
        LayoutParams iconParams = new LayoutParams(pxSize, pxSize);
        iconParams.rightMargin = DisplayUtil.dip2px(marginSide);
        iconParams.addRule(RelativeLayout.CENTER_VERTICAL);
        layout.addView(rightIv, iconParams);

        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        addView(layout, layoutParams);

        rightGroup = layout;
    }

    private void addCenterGroup() {
        RelativeLayout layout = new RelativeLayout(getContext());

        centerTv = new TextView(getContext());
        centerTv.setTextSize(centerTextSize);
        centerTv.setTextColor(textColor);
        centerTv.setText("默认");

        centerIv = new ImageView(getContext());

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        layout.addView(centerTv, params);

        int pxSize = DisplayUtil.dip2px(iconSize);
        LayoutParams iconParams = new LayoutParams(pxSize, pxSize);
        iconParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        layout.addView(centerIv, iconParams);

        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        addView(layout, layoutParams);

        centerGroup = layout;
    }
}