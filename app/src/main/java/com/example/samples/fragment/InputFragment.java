package com.example.samples.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.samples.R;
import com.example.samples.view.InputPanel;

public class InputFragment extends BaseFragment {

    private View mBtnComment;
    private InputPanel mInputPanel;
    private View mMask;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_input, container, false);
        mBtnComment = view.findViewById(R.id.btn_comment);
        mBtnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInputPanel.toggleInput();
            }
        });

        mMask = view.findViewById(R.id.fl_mask);
        mMask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInputPanel.toggleInput();
            }
        });

        mInputPanel = view.findViewById(R.id.input_panel);
        mInputPanel.setKeyboardListener(new InputPanel.KeyboardListener() {
            @Override
            public void onKeyboardShow() {
                mMask.setVisibility(View.VISIBLE);
            }

            @Override
            public void onKeyboardHide(boolean allHide) {
                if (allHide) {
                    mMask.setVisibility(View.GONE);
                }
            }
        });
        return view;
    }

    @Override
    public boolean onBackPress() {
        return mInputPanel.onBackPress();
    }
}
