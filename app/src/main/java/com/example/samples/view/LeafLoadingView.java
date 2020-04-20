package com.example.samples.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.example.samples.R;
import com.example.samples.model.Leaf;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

public class LeafLoadingView extends View {
    private int mWidth;
    private int mHeight;
    private Paint mPaint;
    private Paint mPaintStroke;
    private int mProgress;
    private boolean isAnimating = false;
    private CopyOnWriteArrayList<Leaf> mLeafList = new CopyOnWriteArrayList<>();
    private Paint mLeafPaint;
    private int mLeft;
    private int mRight;
    private int mTop;
    private int mBottom;
    private int mRadius;
    private int mLeafR = 24;
    private Paint mPaintBg;
    private Bitmap mBitmap;
    private int mBarWidth;
    private int mBarHeight;

    public LeafLoadingView(Context context) {
        super(context);
        init();
    }

    public LeafLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LeafLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.parseColor("#f86442"));
        mPaint.setStyle(Paint.Style.FILL);

        mPaintBg = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBg.setColor(Color.parseColor("#ffffff"));
        mPaintBg.setStyle(Paint.Style.FILL);

        mPaintStroke = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintStroke.setColor(Color.parseColor("#f86442"));
        mPaintStroke.setStyle(Paint.Style.STROKE);
        mPaintStroke.setStrokeWidth(1);

        mLeafPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLeafPaint.setColor(Color.parseColor("#f86442"));
        mLeafPaint.setStyle(Paint.Style.FILL);

        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.duck);
        mBitmap = Bitmap.createScaledBitmap(bitmap, 48, 48, false);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        mLeft = mWidth / 4;
        mRight = mWidth * 3 / 4;
        mTop = mHeight * 6 / 16;
        mBottom = mHeight * 10 / 16;
        mRadius = mHeight / 4;
        mBarWidth = mWidth / 2;
        mBarHeight = mHeight / 4;
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        // 绘制一个圆角矩形的路径 白色填充
        Path path = new Path();
        path.arcTo(new RectF(mLeft, mTop, mLeft + mRadius, mBottom), 90, 180);
        path.lineTo(mRight - mRadius / 2, mTop);
        path.arcTo(new RectF(mRight - mRadius, mTop, mRight, mBottom), -90, 180);
        path.lineTo(mLeft + mRadius / 2, mBottom);

        canvas.drawPath(path, mPaintStroke);

        canvas.drawPath(path, mPaintBg);

        // SRC_IN模式
        PorterDuff.Mode mode = PorterDuff.Mode.SRC_IN;
        mPaint.setXfermode(new PorterDuffXfermode(mode));

        // 绘制进度（相交部分）
        int barRight = mLeft + mProgress * mWidth / 200;
        canvas.drawRect(mLeft, mTop, barRight, mBottom, mPaint);

        for (Leaf leaf : mLeafList) {
            canvas.save();
            canvas.rotate(leaf.rotation, leaf.x + mLeafR / 2, leaf.y + mLeafR / 2);
            canvas.drawBitmap(mBitmap, leaf.x, leaf.y, mLeafPaint);
            canvas.restore();
        }
    }

    public void setProgress(final int progress) {
        post(new Runnable() {
            @Override
            public void run() {
                if (mProgress != progress) {
                    if (!isAnimating) {
                        ValueAnimator animator = ValueAnimator.ofInt(mProgress, progress);
                        animator.setInterpolator(new AccelerateDecelerateInterpolator());
                        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                int value = (int) animation.getAnimatedValue();
                                mProgress = value;
                                invalidate();
                            }
                        });
                        animator.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationStart(Animator animation) {
                                super.onAnimationStart(animation);
                                isAnimating = true;
                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                isAnimating = false;
                            }
                        });
                        animator.setDuration(Math.abs(mProgress - progress) * 100);
                        animator.start();
                    }
                }
            }
        });
    }

    public void leafStart() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                int count = new Random().nextInt(10);
                if (count > 7 && mLeafList.size() < 6) {
                    Leaf leaf = new Leaf();
                    leaf.x = mWidth * 3 / 4 - mLeafR;
//                    leaf.y = mHeight / 2;
                    leaf.y = mTop;
                    leaf.A = (int) (mBarHeight - mLeafR * 4);
                    leaf.w = 25F + new Random().nextInt(50);
                    mLeafList.add(leaf);
                }
            }
        };
        timer.schedule(task, 0, 200);

        Timer timer2 = new Timer();
        TimerTask task2 = new TimerTask() {
            @Override
            public void run() {
                for (Leaf leaf : mLeafList) {
                    if (leaf.x <= (mWidth / 4 + mBarWidth * mProgress / 100F)) {
                        mLeafList.remove(leaf);
                        continue;
                    }
                    leaf.x -= 10;
//                    leaf.y = (int) (leaf.A * Math.sin(leaf.x / leaf.w)) + mTop + mBarHeight / 2;
                    leaf.y = mTop;
                    leaf.rotation += 10;
                    leaf.rotation %= 360;
                }
                postInvalidate();
            }
        };
        timer2.schedule(task2, 0, 100);
    }
}
