package com.example.samples.fragment.animation;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.samples.util.BaseUtil;
import com.example.samples.R;
import com.example.samples.fragment.BaseFragment;

public class LayoutTransitionFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fra_layout_transition, container, false);
        final View view1 = view.findViewById(R.id.view1);
        view.findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view3 = new View(getContext());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, BaseUtil.dp2px(getContext(), 48));
                view3.setLayoutParams(lp);
                view3.setBackgroundColor(Color.BLUE);
                view.addView(view3, 0);
            }
        });
        view.findViewById(R.id.btn_remove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.removeView(view1);
            }
        });
        return view;
    }
}
