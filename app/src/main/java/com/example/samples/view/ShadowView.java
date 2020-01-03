package com.example.samples.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.samples.BaseUtil;

import java.util.ArrayList;
import java.util.List;

import static android.graphics.Canvas.ALL_SAVE_FLAG;

public class ShadowView extends View {
    // 聚光灯
    public static final int MODE_FOCUS = 0;
    // 正常
    public static final int MODE_FLAT = 1;
    private List<Focus> mFocusList = new ArrayList<>();
    private Paint mFocusPaint;
    private int mMode;
    private int mBackgroundColor = Color.parseColor("#80000000");

    public interface Callback {
        void onFocusClicked();
        void onOutsideClicked();
    }

    public class Focus {
        public final static int SHAPE_RECT = 0;
        public final static int SHAPE_CIRCLE = 1;
        public final static int SHAPE_ELLIPSE = 2;
        public static final int SHAPE_ROUND_RECT = 3;
        public int height;
        public int width;
        public float x;
        public float y;
        public int type;
        public int marginX;
        public int marginY;
        public int radius;
        public Callback callback;
        public boolean clickable;

        public Focus(int shape, float x, float y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.type = shape;
        }
    }

    public ShadowView(Context context) {
        super(context);
        init();
    }

    public ShadowView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ShadowView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        mFocusPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mFocusPaint.setStyle(Paint.Style.FILL);
        mFocusPaint.setColor(Color.BLACK);
        mFocusPaint.setFilterBitmap(false);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    public void addFocus(final Focus focus) {
        mFocusList.add(focus);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.saveLayer(0, 0, BaseUtil.getScreenWidth(getContext()), getHeight(), null, ALL_SAVE_FLAG);

        // dst
        for (Focus focus : mFocusList) {
            Bitmap circle = makeDstCircle(focus);
            canvas.drawBitmap(circle, 0, 0, mFocusPaint);
        }

        // src
        PorterDuff.Mode mode = PorterDuff.Mode.DST_ATOP;
        if (mMode == MODE_FOCUS) {
            mode = PorterDuff.Mode.DST_ATOP;
        } else if (mMode == MODE_FLAT) {
            mode = PorterDuff.Mode.SRC_OUT;
        }
        mFocusPaint.setXfermode(new PorterDuffXfermode(mode));
        Bitmap background = makeSrcRect();
        canvas.drawBitmap(background, 0, 0, mFocusPaint);
        mFocusPaint.setXfermode(null);

        canvas.restore();
    }

    private Bitmap makeDstCircle(Focus focus) {
        int height = getHeight();
        Bitmap bm = Bitmap.createBitmap(BaseUtil.getScreenWidth(getContext()), height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bm);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        if (mMode == MODE_FOCUS) {
            // 聚焦模式先只支持圆形
            paint.setColor(Color.RED);
            int radius = Math.max(focus.width / 2, focus.height / 2);
            RadialGradient gradient = new RadialGradient(
                    focus.x,
                    focus.y,
                    radius,
                    new int[]{Color.WHITE, mBackgroundColor},
                    new float[]{0.6F, 1F},
                    Shader.TileMode.MIRROR);
            paint.setShader(gradient);
            canvas.drawCircle(focus.x, focus.y, radius, paint);
        } else if (mMode == MODE_FLAT) {
            if (focus.type == Focus.SHAPE_CIRCLE) {
                int radius = Math.max(focus.width / 2, focus.height / 2);
                canvas.drawCircle(focus.x, focus.y, radius + focus.marginX, paint);
            } else if (focus.type == Focus.SHAPE_RECT) {
                RectF rect = new RectF(
                        focus.x - focus.width / 2 + focus.marginX,
                        focus.y - focus.height / 2 + focus.marginY,
                        focus.x + focus.width / 2 - focus.marginX,
                        focus.y + focus.height / 2 - focus.marginY);
                canvas.drawRect(rect, paint);
            } else if (focus.type == Focus.SHAPE_ELLIPSE) {
                RectF rect = new RectF(
                        focus.x - focus.width / 2 + focus.marginX,
                        focus.y - focus.height / 2 + focus.marginY,
                        focus.x + focus.width / 2 - focus.marginX,
                        focus.y + focus.height / 2 - focus.marginY);
                canvas.drawOval(rect, paint);
            } else if (focus.type == Focus.SHAPE_ROUND_RECT) {
                RectF rect = new RectF(
                        focus.x - focus.width / 2 - focus.marginX,
                        focus.y - focus.height / 2 - focus.marginY,
                        focus.x + focus.width / 2 + focus.marginX,
                        focus.y + focus.height / 2 + focus.marginY);
                canvas.drawRoundRect(rect, focus.radius, focus.radius, paint);
            }
        }
        return bm;
    }

    private Bitmap makeSrcRect() {
        int width = BaseUtil.getScreenWidth(getContext());
        int height = getHeight();
        Bitmap bm = Bitmap.createBitmap(BaseUtil.getScreenWidth(getContext()), height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bm);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(mBackgroundColor);
        canvas.drawRect(new Rect(0, 0, width, height), paint);
        return bm;
    }

    public void setMode(int mode) {
        this.mMode = mode;
    }

    public void setBackgroundColor(int color) {
        this.mBackgroundColor = color;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            for (Focus focus : mFocusList) {
                if (x > focus.x - focus.width / 2 &&
                        x < focus.x + focus.width / 2 &&
                        y > focus.y - focus.height / 2 &&
                        y < focus.y + focus.height / 2) {
                    if (focus.callback != null) {
                        focus.callback.onFocusClicked();
                    }
                    return !focus.clickable;
                } else {
                    if (focus.callback != null) {
                        focus.callback.onOutsideClicked();
                    }
                    return true;
                }
            }
        }
        return true;
    }
}
