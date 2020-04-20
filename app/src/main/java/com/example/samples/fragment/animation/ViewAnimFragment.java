package com.example.samples.fragment.animation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.samples.R;
import com.example.samples.fragment.BaseFragment;
import com.example.samples.util.AnimationUtil;

public class ViewAnimFragment extends BaseFragment {

    private View mVAlpha;
    private View mVScale;
    private View mVTrans;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fra_view_anim, container, false);
        mVAlpha = view.findViewById(R.id.tv_alpha_target);
        mVTrans = view.findViewById(R.id.tv_trans_target);
        mVScale = view.findViewById(R.id.tv_scale_target);

        view.findViewById(R.id.tv_alpha).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mVAlpha.getVisibility() == View.VISIBLE) {
                    AnimationUtil.alphaHide(mVAlpha);
                } else {
                    AnimationUtil.alphaShow(mVAlpha);
                }
            }
        });
        view.findViewById(R.id.tv_trans).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mVTrans.getVisibility() == View.VISIBLE) {
                    AnimationUtil.transHide(mVTrans);
                } else {
                    AnimationUtil.transShow(mVTrans);
                }
            }
        });
        view.findViewById(R.id.tv_scale).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mVScale.getVisibility() == View.VISIBLE) {
                    AnimationUtil.scaleHide(mVScale);
                } else {
                    AnimationUtil.scaleShow(mVScale);
                }
            }
        });
        return view;
    }
}
