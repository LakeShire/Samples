package com.example.samples;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.example.samples.view.ShadowView;

import java.lang.ref.WeakReference;

public class Tip {

    public final static int SHAPE_RECT = ShadowView.Focus.SHAPE_RECT;
    public final static int SHAPE_CIRCLE = ShadowView.Focus.SHAPE_CIRCLE;
    public final static int SHAPE_ELLIPSE = ShadowView.Focus.SHAPE_ELLIPSE;
    public static final int SHAPE_ROUND_RECT = ShadowView.Focus.SHAPE_ROUND_RECT;

    public static final int ARROW_NONE = -1;
    public static final int ARROW_CENTER = 0;
    public static final int ARROW_LEFT = 1;
    public static final int ARROW_RIGHT = 2;

    public static final int TOP = 0;
    public static final int BOTTOM = 1;

    public static final int STYLE_NORMAL = 0;
    public static final int STYLE_MASK = 1;
    public static final int STYLE_ONLY_MASK = 2;

    public WeakReference<View> anchorWR;
    public int layout = -1;
    public long delay = 1000;
    public PopupWindow.OnDismissListener dismissListener;
    public int arrow = ARROW_NONE;
    public int backgroundColor = Color.BLACK;
    public int backgroundRadius = 4;
    public int dir = BOTTOM;
    // tip等级
    // 1为普通tip
    // 2为蒙层聚焦tip
    int style = 1;
    public String content;
    public long duration = 3000;
    public int offsetX = -1;
    public int offsetY = -1;
    public View view;
    public View.OnClickListener onClickListener;
    // 是否在蒙层下可以直接点击焦点区域
    public boolean focusClickable = true;

    // 蒙层聚焦tip需要以下参数
    // tip的聚焦区形状
    public int shape;

    // tip聚焦区边距
    public int marginX;
    public int marginY;

    // 自定义的宽高
    public int width = -1;
    public int height = -1;

    // 保存tip的暗色遮罩以便之后移除
    public ShadowView background;

    // 圆角矩形
    public int shadowRadius;

    // 自动获得的聚焦区位置有问题时传入
    public int x = -1;
    public int y = -1;

    public int startX;
    public int startY;
    public int arrowX;
    public int arrowY;
    public ImageView arrowView;
    public boolean autoDismiss;
    public int textColor = Color.WHITE;
    public int textSize = 16;

    public Tip(View anchor, int layout) {
        this.anchorWR = new WeakReference<>(anchor);
        this.layout = layout;
    }

    public Tip(View anchor, View view) {
        this.anchorWR = new WeakReference<>(anchor);
        this.view = view;
    }

    public Tip(View anchor, String content) {
        this.anchorWR = new WeakReference<>(anchor);
        this.layout = R.layout.popup_common;
        this.content = content == null ? "" : content;
    }

    public Tip layout(int layout) {
        this.layout = layout;
        return this;
    }

    public Tip onClick(View.OnClickListener listener) {
        onClickListener = listener;
        return this;
    }

    public Tip offsetX(int offset) {
        this.offsetX = offset;
        return this;
    }

    public Tip offsetY(int offset) {
        this.offsetY = offset;
        return this;
    }

    public Tip delay(long delay) {
        this.delay = delay;
        return this;
    }

    public Tip duration(long duration) {
        this.duration = duration;
        return this;
    }

    public Tip onDismiss(PopupWindow.OnDismissListener listener) {
        this.dismissListener = listener;
        return this;
    }

    public Tip arrow(int arrow) {
        this.arrow = arrow;
        return this;
    }

    public Tip backgroundColor(int color) {
        this.backgroundColor = color;
        return this;
    }

    public Tip backgroundRadius(int radius) {
        this.backgroundRadius = radius;
        return this;
    }

    public Tip dir(int dir) {
        this.dir = dir;
        return this;
    }

    public Tip level(int level) {
        this.style = level;
        return this;
    }

    public Tip autoDismiss(boolean auto) {
        this.autoDismiss = auto;
        return this;
    }

    public Tip textColor(int color) {
        this.textColor = color;
        return this;
    }

    public Tip textSize(int size) {
        this.textSize = size;
        return this;
    }

    public Tip shape(int shape) {
        this.shape = shape;
        return this;
    }

    public Tip marginX(int marginX) {
        this.marginX = marginX;
        return this;
    }

    public Tip marginY(int marginY) {
        this.marginY = marginY;
        return this;
    }

    public Tip shadowRadius(int radius) {
        this.shadowRadius = radius;
        return this;
    }

    public Tip focusClickable(boolean clickable) {
        this.focusClickable = clickable;
        return this;
    }
}
