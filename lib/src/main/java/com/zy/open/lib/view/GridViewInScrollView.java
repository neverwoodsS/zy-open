package com.zy.open.lib.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * @Description: 解决ScrollView嵌套GridView item显示不全
 * @time:2015-4-23 下午9:34:32
 * @modification:
 */
public class GridViewInScrollView extends GridView {

	public GridViewInScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public GridViewInScrollView(Context context) {
		super(context);
	}

	public GridViewInScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}