package com.example.samples.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

import com.example.samples.util.BaseUtil;
import com.example.samples.R;

import java.util.LinkedList;
import java.util.Queue;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static com.example.samples.view.Tip.ARROW_CENTER;
import static com.example.samples.view.Tip.ARROW_LEFT;
import static com.example.samples.view.Tip.ARROW_RIGHT;
import static com.example.samples.view.Tip.BOTTOM;
import static com.example.samples.view.Tip.STYLE_MASK;
import static com.example.samples.view.Tip.STYLE_NORMAL;
import static com.example.samples.view.Tip.STYLE_ONLY_MASK;
import static com.example.samples.view.Tip.TOP;
import static com.example.samples.view.ShadowView.MODE_FLAT;

public class CustomTipsView {

    private static final String TAG = CustomTipsView.class.getSimpleName();
    private Fragment mFragment;
    private LifecycleEventObserver mObserver = new LifecycleEventObserver() {
        @Override
        public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
            if (event == Lifecycle.Event.ON_PAUSE) {
                Log.i(TAG, "移除窗口以及剩余任务");
                isQuit = true;
                if (mWindow != null) {
                    mWindow.dismiss();
                }
                mTips.clear();
                if (mFragment != null) {
                    mFragment.getLifecycle().removeObserver(mObserver);
                    mFragment = null;
                }
            }
        }
    };
    private final Context mContext;
    private final Handler mHandler;
    private final View mContainerView;
    private final ViewGroup mContainer;
    private Queue<Tip> mTips = new LinkedList<>();
    private boolean isShowing;
    private long mDelay = 1000;
    private int[][] mArrowIds = {
            {R.id.iv_arrow_bottom_center, R.id.iv_arrow_left_bottom_bottom, R.id.iv_arrow_right_bottom_bottom},
            {R.id.iv_arrow_top_center, R.id.iv_arrow_left_top_top, R.id.iv_arrow_right_top_top,}
    };
    private PopupWindow mWindow;
    private boolean isQuit = false;

    public CustomTipsView(Context context, final Fragment fragment) {
        mContext = context;
        mFragment = fragment;
        mContainerView = LayoutInflater.from(context).inflate(R.layout.popup_container, null);
        mContainer = mContainerView.findViewById(R.id.popup_container);
        mHandler = new Handler(Looper.getMainLooper());
        fragment.getLifecycle().addObserver(mObserver);
    }

    public void addTip(Tip tip) {
        if (tip != null) {
            mTips.offer(tip);
        }
    }

    public void showAllTips() {
        if (isShowing) {
            return;
        }
        if (mTips == null || mTips.isEmpty()) {
            return;
        }
        Tip tip = mTips.poll();
        if (tip != null) {
            showSingleTip(tip);
        }
    }

    private void showSingleTip(final Tip tip) {
        if (tip == null || tip.anchorWR == null || tip.anchorWR.get() == null || tip.anchorWR.get().getVisibility() != View.VISIBLE) {
            next();
            return;
        }

        isShowing = true;
        if (tip.style == STYLE_NORMAL || tip.style == STYLE_MASK) {
            View anchor = tip.anchorWR.get();
            int[] location = new int[2];
            anchor.getLocationOnScreen(location);
            View view;
            if (tip.layout < 0) {
                view = LayoutInflater.from(mContext).inflate(R.layout.popup_common, null);
                TextView tvContent = view.findViewById(R.id.tv_content);
                tvContent.setText(tip.content);
                tvContent.setTextColor(tip.textColor);
                tvContent.setTextSize(BaseUtil.dp2px(mContext, tip.textSize));
            } else {
                if (tip.view == null) {
                    view = LayoutInflater.from(mContext).inflate(tip.layout, null);
                } else {
                    view = tip.view;
                }
            }
            view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            view.setOnClickListener(tip.onClickListener);
            mContainer.removeAllViews();
            mContainer.addView(view);

            int anchorHeight = anchor.getHeight();
            int anchorWidth = anchor.getWidth();
            int viewWidth = view.getMeasuredWidth();
            int viewHeight = view.getMeasuredHeight();
            int screenWidth = BaseUtil.getScreenWidth(mContext);
            int screenHeight = BaseUtil.getScreenHeight(mContext);

            adjustDirection(tip, anchorHeight, anchorWidth, location[0], location[1], viewWidth, viewHeight, screenWidth, screenHeight);
            adjustArrow(tip, anchorHeight, anchorWidth, location[0], location[1], viewWidth, viewHeight, screenWidth, screenHeight);
            adjustBackground(tip, view);
            showArrow(tip);

            mWindow = new PopupWindow(mContainerView, WRAP_CONTENT, WRAP_CONTENT);
            mWindow.setOutsideTouchable(false);
            mWindow.showAtLocation(anchor, Gravity.NO_GRAVITY, tip.startX, tip.startY);
            mWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    if (tip.dismissListener != null) {
                        tip.dismissListener.onDismiss();
                    }
                    if (tip.arrowView != null) {
                        tip.arrowView.setVisibility(View.INVISIBLE);
                    }
                    dismissShadow(tip);
                    if (tip.delay >= 0 && !isQuit) {
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                next();
                            }
                        }, tip.delay);
                    }
                }
            });
        }

        if (tip.style == Tip.STYLE_MASK || tip.style == STYLE_ONLY_MASK) {
            showMask(tip);
        }

        if (tip.autoDismiss && tip.duration > 0) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mWindow != null) {
                        mWindow.dismiss();
                    }
                }
            }, tip.duration);
        }
    }

    private void dismissShadow(Tip tip) {
        if (tip.background != null) {
            if (mContext instanceof Activity && ((Activity) mContext).getWindow() != null) {
                View view = ((Activity) mContext).getWindow().getDecorView();
                if (view instanceof ViewGroup) {
                    ((ViewGroup) view).removeView(tip.background);
                    tip.background = null;
                }
            }
        }
    }

    private void adjustArrow(Tip tip, int anchorH, int anchorW, int anchorX, int anchorY, int viewW, int viewH, int screenW, int screenH) {
        int startX;
        int startY = 0;
        if (tip.arrow == ARROW_LEFT) {
            startX = anchorX + anchorW / 2 + tip.offsetX;
        } else if (tip.arrow == ARROW_RIGHT) {
            startX = anchorX - viewW + anchorW / 2 + tip.offsetX;
        } else {
            startX = anchorX + anchorW / 2 - viewW / 2 + tip.offsetX;
        }
        if (startX + viewW > screenW) {
            startX = screenW - viewW - BaseUtil.dp2px(mContext, 2);
        }
        if (startX < 0) {
            startX = BaseUtil.dp2px(mContext, 2);
        }
        if (tip.dir == TOP || tip.dir == BOTTOM) {
            if (tip.arrow == ARROW_CENTER) {
                tip.arrowX = anchorX + anchorW / 2 - startX;
            }
        }

        if (tip.offsetY < 0) {
            tip.offsetY = BaseUtil.dp2px(mContext, 16);
        }

        if (tip.dir == TOP) {
            startY = anchorY - viewH - tip.offsetY;
        } else if (tip.dir == BOTTOM) {
            startY = anchorY + anchorH + tip.offsetY;
        }
        tip.startX = startX;
        tip.startY = startY;
    }

    private void adjustBackground(Tip tip, View view) {
        if (tip.backgroundRadius != 0) {
            float r = BaseUtil.dp2px(mContext, tip.backgroundRadius);
            float[] radius = new float[8];
            for (int i = 0; i < 8; i++) {
                radius[i] = r;
            }
            GradientDrawable drawable = new GradientDrawable();
            if (tip.arrow == ARROW_LEFT && tip.dir == BOTTOM) {
                radius[0] = 0;
                radius[1] = 0;
            } else if (tip.arrow == ARROW_RIGHT && tip.dir == BOTTOM) {
                radius[2] = 0;
                radius[3] = 0;
            } else if (tip.arrow == ARROW_RIGHT && tip.dir == TOP) {
                radius[4] = 0;
                radius[5] = 0;
            } else if (tip.arrow == ARROW_LEFT && tip.dir == TOP) {
                radius[6] = 0;
                radius[7] = 0;
            }
            drawable.setCornerRadii(radius);
            drawable.setColor(tip.backgroundColor);
            view.setBackground(drawable);
        } else {
            view.setBackgroundColor(tip.backgroundColor);
        }
    }

    private void adjustDirection(Tip tip, int anchorH, int anchorW, int anchorX, int anchorY, int viewW, int viewH, int screenW, int screenH) {
        if (tip.dir == TOP) {
            if (anchorY - viewH <= 0) {
                // 上方空间不足
                tip.dir = BOTTOM;
            }
        } else if (tip.dir == BOTTOM) {
            if (screenH - anchorY - anchorH - viewH <= 0) {
                // 下方空间不足
                tip.dir = TOP;
            }
        }
    }

    private void showArrow(Tip tip) {
        if (tip.arrow == Tip.ARROW_NONE) {
            return;
        }
        tip.arrowView = mContainerView.findViewById(mArrowIds[tip.dir][tip.arrow]);
        tip.arrowView.setVisibility(View.VISIBLE);
        tip.arrowView.setColorFilter(tip.backgroundColor);
        if (tip.arrow == ARROW_CENTER) {
            tip.arrowView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) tip.arrowView.getLayoutParams();
            lp.leftMargin = tip.arrowX - tip.arrowView.getMeasuredWidth() / 2;
            tip.arrowView.setLayoutParams(lp);
        }
    }

    private void showMask(final Tip tip) {
        if (!(mContext instanceof Activity)) {
            return;
        }
        Activity activity = (Activity) mContext;
        if (activity.getWindow() == null) {
            return;
        }
        View view = activity.getWindow().getDecorView();
        if (view == null) {
            return;
        }
        if (tip == null || tip.anchorWR == null || tip.anchorWR.get() == null) {
            return;
        }
        if (view instanceof ViewGroup) {
            View anchor = tip.anchorWR.get();
            ShadowView sv = new ShadowView(activity);
            int[] locations = new int[2];
            anchor.getLocationInWindow(locations);
            sv.setMode(MODE_FLAT);
            int width = tip.width == -1 ? anchor.getWidth() : tip.width;
            int height = tip.height == -1 ? anchor.getHeight() : tip.height;
            int x = tip.x == -1 ? locations[0] + anchor.getWidth() / 2 : tip.x;
            int y = tip.y == -1 ? locations[1] + anchor.getHeight() / 2 : tip.y;
            ShadowView.Focus focus = sv.new Focus(tip.shape, x, y, width, height);
            focus.marginX = tip.marginX;
            focus.marginY = tip.marginY;
            if (tip.shape == ShadowView.Focus.SHAPE_ROUND_RECT) {
                focus.radius = tip.shadowRadius;
            }
            focus.clickable = tip.focusClickable;
            focus.callback = new ShadowView.Callback() {
                @Override
                public void onFocusClicked() {
                    if (tip.style == STYLE_ONLY_MASK) {
                        dismissShadow(tip);
                    } else {
                        if (mWindow != null) {
                            mWindow.dismiss();
                        }
                    }
                }

                @Override
                public void onOutsideClicked() {
                    if (tip.style == STYLE_ONLY_MASK) {
                        dismissShadow(tip);
                    } else {
                        if (mWindow != null) {
                            mWindow.dismiss();
                        }
                    }
                }
            };
            sv.addFocus(focus);
            ((ViewGroup) view).addView(sv);
            tip.background = sv;
        }
    }

    private void next() {
        if (mTips == null || mTips.isEmpty()) {
            isShowing = false;
            return;
        }
        final Tip next = mTips.poll();
        if (next != null) {
            showSingleTip(next);
        }
    }
}
