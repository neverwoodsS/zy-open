package com.zy.open.lib.view.title;

import android.view.View;

/**
 * Created by zhangll on 16/4/11.
 */
public interface TitleViewInterface {
    void setTitleStyle(TitleStyle titleStyle);

    void setLeftText(CharSequence leftText);
    void setLeftImage(int leftImage);
    void setLeftVisible(boolean leftVisible);

    void setRightText(CharSequence rightText);
    void setRightImage(int rightImage);
    void setRightVisible(boolean rightVisible);

    void setCenterText(CharSequence centerText);
    void setCenterImage(int centerImage);
    void setCenterVisible(boolean centerVisible);

    void setOnLeftClickListener(View.OnClickListener onClickListener);
    void setOnRightClickListener(View.OnClickListener onClickListener);
    void setOnCenterClickListener(View.OnClickListener onClickListener);
}
