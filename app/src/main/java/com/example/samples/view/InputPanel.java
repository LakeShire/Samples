package com.example.samples.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.example.samples.R;
import com.example.samples.util.BaseUtil;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class InputPanel extends FrameLayout {
    private boolean isKeyboardShow;

    public interface KeyboardListener {
        void onKeyboardShow();
        void onKeyboardHide(boolean allHide);
    }

    private View mView;
    private InputMethodManager mImm;
    private EditText mEditText;
    private View mTvSend;
    private View mVInput;
    private int rootViewVisibleHeight;
    private int lastRootViewHeight;
    private View mVEmotion;
    private View mIvEmotion;
    private ViewTreeObserver.OnGlobalLayoutListener mListener;
    private Activity mActivity;
    private int mKeyboardHeight;
    private KeyboardListener mKeyboardListener;

    public InputPanel(Context context) {
        super(context);
        init(context);
    }

    public InputPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public InputPanel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        if (context instanceof Activity) {
            mActivity = (Activity) context;
        } else {
            throw new RuntimeException("context must be a activity");
        }

        mImm = (InputMethodManager) mActivity.getSystemService(INPUT_METHOD_SERVICE);
        mView = LayoutInflater.from(context).inflate(R.layout.panel_input, null, false);
        addView(mView);

        mVInput = mView.findViewById(R.id.rl_input);
        mEditText = mView.findViewById(R.id.edit_text);
        mEditText.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    isKeyboardShow = true;
                }
            }
        });
        mEditText.requestFocus();
        mTvSend = mView.findViewById(R.id.tv_send);
        mVEmotion = mView.findViewById(R.id.v_emotion);
        mIvEmotion = mView.findViewById(R.id.iv_emotion);
        mIvEmotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mVEmotion.getVisibility() == View.VISIBLE) {
                    mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                    mImm.showSoftInput(mEditText, 0);
                } else {
                    mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
                    mImm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
                    mVEmotion.setVisibility(View.VISIBLE);
                }
            }
        });

        mListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                FrameLayout content = mActivity.findViewById(android.R.id.content);
                if (content == null) {
                    return;
                }

                final View rootView = content.getChildAt(0);
                if (rootView == null) {
                    return;
                }

                Rect r = new Rect();
                rootView.getWindowVisibleDisplayFrame(r);
                int visibleHeight = r.height();

                if (rootViewVisibleHeight == 0) {
                    rootViewVisibleHeight = visibleHeight;
                    return;
                }

                if (rootViewVisibleHeight == visibleHeight) {
                    return;
                }

                if (Math.abs(rootViewVisibleHeight - visibleHeight) < BaseUtil.dp2px(mActivity, 60)
                        && Math.abs(rootViewVisibleHeight - visibleHeight) == Math.abs(rootView.getHeight() -
                        lastRootViewHeight)) {
                    return;
                }

                lastRootViewHeight = rootView.getHeight();

                int height;
                if (rootViewVisibleHeight - visibleHeight > BaseUtil.dp2px(mActivity, 200)) {
                    height = rootViewVisibleHeight - visibleHeight;
                    onKeyboardShow(height);
                } else if (visibleHeight - rootViewVisibleHeight > BaseUtil.dp2px(mActivity, 200)) {
                    onKeyboardHide();
                }

                rootViewVisibleHeight = visibleHeight;
            }
        };
        watchKeyboard();
    }

    private void watchKeyboard() {
        if (mActivity == null) {
            return;
        }
        FrameLayout content = mActivity.findViewById(android.R.id.content);
        if (content == null) {
            return;
        }

        final View rootView = content.getChildAt(0);
        if (rootView == null) {
            return;
        }

        if (rootView.getViewTreeObserver() == null) {
            return;
        }
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(mListener);
    }

    public void toggleInput() {
        if (mVInput.getVisibility() == VISIBLE) {
            hideInput();
        } else {
            showInput();
        }
    }

    private void showInput() {
        // 需要先获取焦点 否则第一次点击是获取焦点 第二次才弹出输入法
        mEditText.requestFocus();
        // 然后延迟一下再弹
        mEditText.postDelayed(new Runnable() {
            @Override
            public void run() {
                mVEmotion.setVisibility(View.GONE);
                mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                mImm.showSoftInput(mEditText, 0);
            }
        }, 100);
    }

    private void hideInput() {
        mImm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
        mVInput.setVisibility(GONE);
    }

    private void onKeyboardHide() {
        if (mVEmotion.getVisibility() == View.VISIBLE) {
            mKeyboardListener.onKeyboardHide(false);
        } else {
            mVInput.setVisibility(View.GONE);
            if (mKeyboardListener != null) {
                mKeyboardListener.onKeyboardHide(true);
            }
        }
    }

    private void onKeyboardShow(int height) {
        if (mKeyboardHeight != height) {
            ViewGroup.LayoutParams lpEmotion = mVEmotion.getLayoutParams();
            lpEmotion.height = height;
            mVEmotion.setLayoutParams(lpEmotion);
            mKeyboardHeight = height;
        }
        mVEmotion.setVisibility(View.GONE);
        mVInput.setVisibility(View.VISIBLE);
        if (mKeyboardListener != null) {
            mKeyboardListener.onKeyboardShow();
        }
    }

    public boolean onBackPress() {
        if (mVInput.getVisibility() == VISIBLE) {
            mVInput.setVisibility(GONE);
            return true;
        }
        return false;
    }

    public void setKeyboardListener(KeyboardListener listener) {
        mKeyboardListener = listener;
    }
}