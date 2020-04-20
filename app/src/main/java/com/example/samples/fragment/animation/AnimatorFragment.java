package com.example.samples.fragment.animation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.samples.R;
import com.example.samples.fragment.BaseFragment;

public class AnimatorFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fra_animator, container, false);
        view.findViewById(R.id.tv_layout_transition).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFragment(new LayoutTransitionFragment());
            }
        });
        view.findViewById(R.id.tv_key_frame).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFragment(new KeyFrameFragment());
            }
        });
        view.findViewById(R.id.tv_circle_reveal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFragment(new CircleRevealFragment());
            }
        });
        view.findViewById(R.id.tv_scene).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFragment(new SceneAnimationFragment());
            }
        });
        view.findViewById(R.id.tv_fragment_transition).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFragment(new FTransitionFragment());
            }
        });
        view.findViewById(R.id.tv_fragment_in_out).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFragment(new FAnimationFragment());
            }
        });
        view.findViewById(R.id.tv_view_anim).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFragment(new ViewAnimFragment());
            }
        });
        return view;
    }
}
