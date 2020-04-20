package com.example.samples.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.example.samples.R;

public class MyView extends View {
    private Bitmap mBitmap;
    private Paint mPaint;
    private int mWidth;
    private int mHeight;
    private int mBW;
    private int mBH;

    public MyView(Context context) {
        super(context);
        init();
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getWidth();
        mHeight = getHeight();
    }

    public void init() {
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.duck);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.parseColor("#f86442"));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(2);
        mBW = mBitmap.getWidth();
        mBH = mBitmap.getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.rotate(90, mWidth/2, mHeight/2);
        canvas.drawBitmap(mBitmap, mWidth/2 - mBW/2, mHeight/2 - mBH/2, mPaint);
        canvas.restore();

        canvas.save();
        canvas.rotate(90, mWidth/2 + 200, mHeight/2);
        canvas.drawBitmap(mBitmap, mWidth/2 - mBW/2 + 200, mHeight/2 - mBH/2, mPaint);
        canvas.restore();

        canvas.save();
        canvas.rotate(90, mBW/2, mBH/2);
        canvas.drawBitmap(mBitmap, 0, 0, mPaint);
        canvas.restore();

        canvas.drawLine(0, mHeight/2, 1080, mHeight/2, mPaint);
        canvas.drawLine(mWidth/2, 0, mWidth/2, 1920, mPaint);
    }
}
