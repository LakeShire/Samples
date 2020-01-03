package com.example.samples.view;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.style.ReplacementSpan;

public class TagSpan extends ReplacementSpan {
    private int mTextSize;
    /**
     * 需要设置span的宽度
     */
    private int mSize;
    /**
     * 背景颜色
     */
    private int mBackgroundColor;
    /**
     * 字体颜色
     */
    private int mTextColor;
    /**
     * 圆角
     */
    private int mRadius;
    /**
     * 设置左右内边距
     * 默认2
     */
    private int mPaddingLR = 2;
    /**
     * 设置上下边距
     */
    private int mPaddingTB = 2;

    /**
     * @param backgroundColor 背景颜色
     * @param radius          圆角半径
     */
    public TagSpan(int backgroundColor, int textColor, int radius, int size) {
        this.mBackgroundColor = backgroundColor;
        this.mTextColor = textColor;
        this.mRadius = radius;
        this.mTextSize = (int) (size * 0.8F);
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        //根据传入起始截至位置获得截取文字的宽度，最后加上左右两个圆角的半径得到span宽度
        paint.setTextSize(mTextSize);
        mSize = (int) (paint.measureText(text, start, end) + mPaddingLR);
        return mSize;
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        paint.setColor(mBackgroundColor);//设置背景颜色
        paint.setAntiAlias(true);// 设置画笔的锯齿效果
        paint.setTextSize(mTextSize);
        RectF oval = new RectF(x, y + paint.ascent(), x + mSize, y + paint.descent() + mPaddingTB);
        // 设置文字背景矩形，
        // x为span其实左上角相对整个TextView的x值，
        // y为span左上角相对整个View的y值。
        // paint.ascent()获得文字上边缘，paint.descent()获得文字下边缘
        canvas.drawRoundRect(oval, mRadius, mRadius, paint);
        paint.setColor(mTextColor);
        //绘制文字
        canvas.drawText(text, start, end, x + mPaddingLR / 2, y + mPaddingTB / 2, paint);
    }

    public int getPaddingLR() {
        return mPaddingLR;
    }

    public void setPaddingLR(int mPaddingLR) {
        this.mPaddingLR = mPaddingLR;
    }

    public int getPaddingTB() {
        return mPaddingTB;
    }

    public void setPaddingTB(int mPaddingTB) {
        this.mPaddingTB = mPaddingTB;
    }
}