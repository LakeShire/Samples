package com.example.samples.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.fragment.app.DialogFragment;

import com.example.samples.R;

public class FullWidthDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = null;
        if (getActivity() != null) {
            dialog = new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(getLayoutId());
            Window window = dialog.getWindow();
            if (window != null) {
                window.setBackgroundDrawableResource(android.R.color.transparent);
                WindowManager.LayoutParams attributes = window.getAttributes();
                attributes.gravity = getGravity();
                attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
                window.setAttributes(attributes);
            }
        }
        if (dialog == null) {
            dialog = super.onCreateDialog(savedInstanceState);
        }

        if (dialog.getWindow() != null && dialog.getWindow().getDecorView() != null) {
            View view = dialog.getWindow().getDecorView();
            if (view != null) {
                initUi(view);
            }
        }
        return dialog;
    }

    protected void initUi(View view) {

    }

    protected int getLayoutId() {
        return R.layout.dialog_base;
    }

    protected int getGravity() {
        return Gravity.CENTER;
    }
}
