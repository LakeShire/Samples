package com.example.samples.fragment;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.samples.R;

public class SystemUiFragment extends BaseFragment {

    private boolean isDim = false;
    private boolean isHide = false;
    private boolean isBehind = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_system_ui, container, false);
        view.findViewById(R.id.btn_dim).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dimSystemUi();
            }
        });
        view.findViewById(R.id.btn_hide_status_bar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSystemUi();
            }
        });
        view.findViewById(R.id.btn_behind_status_bar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setViewBehindStatusBar();
            }
        });
        return view;
    }

    private void dimSystemUi() {
        if (!isDim) {
            View decorView = getActivity().getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_LOW_PROFILE;
            decorView.setSystemUiVisibility(uiOptions);
            isDim = true;
        } else {
            View decorView = getActivity().getWindow().getDecorView();
            decorView.setSystemUiVisibility(0);
            isDim = false;
        }
    }

    private void hideSystemUi() {
        if (!isHide) {
            View decorView = getActivity().getWindow().getDecorView();
            // Hide the status bar.
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
            // Remember that you should never show the action bar if the
            // status bar is hidden, so hide that too if necessary.
//            ActionBar actionBar = getActivity().getActionBar();
//            actionBar.hide();
            isHide = true;
        } else {
            View decorView = getActivity().getWindow().getDecorView();
            // Hide the status bar.
            int uiOptions = decorView.getSystemUiVisibility() & ~View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
            // Remember that you should never show the action bar if the
            // status bar is hidden, so hide that too if necessary.
//            ActionBar actionBar = getActivity().getActionBar();
//            actionBar.show();
            isHide = false;
        }
    }

    private void setViewBehindStatusBar() {
        if (!isBehind) {
            View decorView = getActivity().getWindow().getDecorView();
            // Hide the status bar.
            int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(uiOptions);
            // Remember that you should never show the action bar if the
            // status bar is hidden, so hide that too if necessary.
//            ActionBar actionBar = getActivity().getActionBar();
//            actionBar.hide();
            isBehind = true;
        } else {
            View decorView = getActivity().getWindow().getDecorView();
            // Hide the status bar.
            int uiOptions = decorView.getSystemUiVisibility() & ~View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            uiOptions &= ~View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(uiOptions);
            // Remember that you should never show the action bar if the
            // status bar is hidden, so hide that too if necessary.
//            ActionBar actionBar = getActivity().getActionBar();
//            actionBar.show();
            isBehind = false;
        }
    }
}
