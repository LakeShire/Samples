package com.example.samples.view;

import android.content.Context;
import android.graphics.Canvas;
import android.text.Layout;
import android.text.Selection;
import android.text.SpannableString;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @author mark.zhang
 */
public class StaticLayoutView extends View {
    private int width;
    private int height;

    @Nullable
    private Layout layout = null;
    @Nullable
    private CharSequence mText;

    public StaticLayoutView(Context context) {
        super(context);
    }

    public StaticLayoutView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StaticLayoutView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setLayout(Layout layout) {
        if (layout == null)
            return;

        this.layout = layout;
        mText = this.layout.getText();
        if (this.layout.getWidth() != width || this.layout.getHeight() != height) {
            width = this.layout.getWidth();
            height = this.layout.getHeight();
            requestLayout();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        if (layout != null) {
            try {
                layout.draw(canvas, null, null, 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (layout == null)
            return true;

        int action = event.getAction();
        boolean updateSelection = (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_DOWN) && mText instanceof SpannableString;
        if (updateSelection) {
            int x = (int) event.getX();
            int y = (int) event.getY();


            int line = layout.getLineForVertical(y);
            int off = layout.getOffsetForHorizontal(line, x);

            ClickableSpan[] link = ((SpannableString) mText).getSpans(off, off, ClickableSpan.class);

            if (link.length != 0) {
                if (action == MotionEvent.ACTION_UP) {
                    link[0].onClick(this);
                } else {
                    Selection.setSelection(((SpannableString) mText), ((SpannableString) mText).getSpanStart(link[0]), ((SpannableString) mText).getSpanEnd(link[0]));
                }

                return true;
            } else {
                Selection.removeSelection(((SpannableString) mText));
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (layout != null) {
            setMeasuredDimension(layout.getWidth(), layout.getHeight());

        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }

    }

}
