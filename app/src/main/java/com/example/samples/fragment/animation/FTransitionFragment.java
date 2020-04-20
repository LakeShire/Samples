package com.example.samples.fragment.animation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import com.example.samples.R;
import com.example.samples.fragment.BaseFragment;

public class FTransitionFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fra_f_transition, container, false);
        final ImageView iv = view.findViewById(R.id.iv_fish);
        final TextView tv = view.findViewById(R.id.tv_title);
        view.findViewById(R.id.rl_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment f = getFragmentManager().findFragmentByTag("transition_dst");
                if (f == null) {
                    f = new FTransitionDstFragment();
                }
                getFragmentManager()
                        .beginTransaction()
                        .addSharedElement(iv, ViewCompat.getTransitionName(iv))
                        .addSharedElement(tv, ViewCompat.getTransitionName(tv))
                        .addToBackStack("transition_dst")
                        .replace(R.id.fl_manage_container, f)
                        .commit();
            }
        });

        return view;
    }
}
