package com.example.samples.fragment.animation;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.samples.R;
import com.example.samples.fragment.BaseFragment;

public class KeyFrameFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fra_key_frame, container, false);
        final View animateView = view.findViewById(R.id.view);
        view.findViewById(R.id.btn_animate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Keyframe k1 = Keyframe.ofFloat(0, 1);
                Keyframe k2 = Keyframe.ofFloat(0.5F, 0);
                Keyframe k3 = Keyframe.ofFloat(1F, 1);
                PropertyValuesHolder holder = PropertyValuesHolder.ofKeyframe("alpha", k1, k2, k3);
                ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(animateView, holder);
                animator.setDuration(2000);
                animator.start();
            }
        });
        return view;
    }
}
