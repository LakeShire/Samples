package com.example.samples;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.view.WindowManager;

public class BaseUtil {
    private static int sWidth = -1;
    private static int sHeight = -1;

    public static int getScreenWidth(Context context) {
        if (sWidth < 0) {
            WindowManager windowManager = null;
            if (context instanceof Activity) {
                windowManager = ((Activity) context).getWindowManager();
            }
            if (windowManager == null) {
                windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            }
            if (windowManager == null) {
                return 1;
            }
            Point size = new Point();
            windowManager.getDefaultDisplay().getSize(size);
            sWidth = size.x;
        }
        return sWidth;
    }

    public static int getScreenHeight(Context context) {
        if (sHeight < 0) {
            WindowManager windowManager = null;
            if (context instanceof Activity) {
                windowManager = ((Activity) context).getWindowManager();
            }
            if (windowManager == null) {
                windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            }
            if (windowManager == null) {
                return 1;
            }
            Point size = new Point();
            windowManager.getDefaultDisplay().getSize(size);
            sHeight = size.y;
        }
        return sHeight;
    }

    public static int dp2px(Context context, float dipValue) {
        if (context == null)
            return (int) (dipValue * 1.5);
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int sp2px(Context context, float dipValue) {
        if (context == null)
            return (int) (dipValue * 1.5);
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2dp(Context context, float pxValue) {
        if (context == null)
            return (int) (pxValue * 1.5);
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
