package com.zy.open.lib.view;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;
import com.zy.open.lib.util.DisplayUtil;

/**
 * Created by zhangll on 15/12/2.
 * 根据大小和内容自适应字体大小的TextView
 */
public class AdjustTextView extends TextView {
    private static float DEFAULT_MIN_TEXT_SIZE = 6;
    private static float DEFAULT_MAX_TEXT_SIZE = 20;

    private Context context;

    // Attributes
    private Paint textPaint;
    private float minTextSize, maxTextSize;

    public AdjustTextView(Context context) {
        super(context);
        this.context = context;
        initialise();
    }

    public AdjustTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initialiseWithXml();
    }

    private void initialise() {
        textPaint = new Paint();
        textPaint.set(this.getPaint());
        minTextSize = DEFAULT_MIN_TEXT_SIZE;
    }

    private void initialiseWithXml() {
        textPaint = new Paint();
        textPaint.set(this.getPaint());

        maxTextSize = DisplayUtil.px2sp(this.getTextSize());
        minTextSize = DEFAULT_MIN_TEXT_SIZE;

        fixSize();
    }

    /**
     * Re size the font so the specified text fits in the text box * assuming
     * the text box is the specified width.
     */
    private void refitText(String text, int textWidth) {
        if (textWidth > 0)
        {
            int availableWidth = textWidth - this.getPaddingLeft() - this.getPaddingRight();
            int minPxSize = DisplayUtil.sp2px(minTextSize);
            float trySize = DisplayUtil.sp2px(maxTextSize);
            textPaint.setTextSize(trySize);
            while ((trySize > minPxSize) && (textPaint.measureText(text) > availableWidth)) {
                trySize -= 1;
                if (trySize <= minPxSize) {
                    trySize = minPxSize;
                    break;
                }
                textPaint.setTextSize(trySize);
            }
            this.setTextSize(TypedValue.COMPLEX_UNIT_PX, trySize);
        }
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int before, int after) {
        super.onTextChanged(text, start, before, after);
        refitText(text.toString(), this.getWidth());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (w != oldw)
        {
            refitText(this.getText().toString(), w);
        }
    }

    private void fixSize() {
        if (maxTextSize > DEFAULT_MAX_TEXT_SIZE) {
            maxTextSize = DEFAULT_MAX_TEXT_SIZE;
        }

        if (maxTextSize < DEFAULT_MIN_TEXT_SIZE) {
            maxTextSize = DEFAULT_MIN_TEXT_SIZE;
        }
    }

    public void setMaxTextSize(float maxTextSize) {
        this.maxTextSize = maxTextSize;
        fixSize();
    }

    public void setMinTextSize(float minTextSize) {
        this.minTextSize = minTextSize;
        fixSize();
    }
}