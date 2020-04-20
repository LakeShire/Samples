package com.example.samples.fragment.animation;

import android.animation.Animator;
import android.os.Build;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.Fade;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.samples.R;
import com.example.samples.fragment.BaseFragment;

public class SceneAnimationFragment extends BaseFragment {

    private Scene sceneA;
    private Scene sceneB;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fra_scene_animation, container, false);
        ViewGroup root = view.findViewById(R.id.scene_root);
        sceneA = Scene.getSceneForLayout(root, R.layout.scene_a, getContext());
        sceneB = Scene.getSceneForLayout(root, R.layout.scene_b, getContext());
        view.findViewById(R.id.btn_animate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeBounds anim = new ChangeBounds();
                TransitionManager.go(sceneB, anim);
            }
        });
        return view;
    }
}
