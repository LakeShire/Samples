package com.example.samples.fragment.animation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.example.samples.R;
import com.example.samples.fragment.BaseFragment;

public class FAnimationFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fra_f_animation, container, false);
        view.findViewById(R.id.tv_f_animation_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getChildFragmentManager();
                FAnimationFragment fragment = new FAnimationFragment();
                fm.beginTransaction().setCustomAnimations(R.anim.anim_slide_in_bottom, R.anim.anim_slide_out_bottom, 0, 0).add(R.id.container, fragment).commit();
            }
        });
        view.findViewById(R.id.tv_f_animation_out).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getChildFragmentManager();
                FAnimationFragment fragment = (FAnimationFragment) fm.findFragmentById(R.id.container);
                if (fragment != null) {
                    fm.beginTransaction().setCustomAnimations(R.anim.anim_slide_in_bottom, R.anim.anim_slide_out_bottom, 0, 0).remove(fragment).commit();
                }
            }
        });
        return view;
    }
}
