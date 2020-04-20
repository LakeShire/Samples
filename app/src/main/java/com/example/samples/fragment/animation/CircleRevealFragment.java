package com.example.samples.fragment.animation;

import android.animation.Animator;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.samples.R;
import com.example.samples.fragment.BaseFragment;

public class CircleRevealFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fra_key_frame, container, false);
        final View animateView = view.findViewById(R.id.view);
        animateView.setVisibility(View.INVISIBLE);
        view.findViewById(R.id.btn_animate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    // get the center for the clipping circle
                    int cx = animateView.getWidth() / 2;
                    int cy = animateView.getHeight() / 2;

                    // get the final radius for the clipping circle
                    float finalRadius = (float) Math.max(cx, cy);

                    // create the animator for this view (the start radius is zero)
                    Animator anim = ViewAnimationUtils.createCircularReveal(animateView, cx, cy, 0f, finalRadius * 2);

                    // make the view visible and start the animation
                    animateView.setVisibility(View.VISIBLE);
                    anim.setDuration(2000);
                    anim.start();
                } else {
                    // set the view to invisible without a circular reveal animation below Lollipop
                    animateView.setVisibility(View.VISIBLE);
                }
            }
        });
        return view;
    }
}
