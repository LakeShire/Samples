package com.example.samples.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.samples.util.BaseUtil;
import com.example.samples.R;
import com.example.samples.view.FlowLayout;

public class FlowLayoutFragment extends BaseFragment {
    private String[] mTags = {
            "啊",
            "啊啊",
            "啊啊啊",
            "啊啊啊啊",
            "啊啊啊啊啊",
            "啊啊啊啊啊啊",
            "啊啊啊啊啊啊啊",
            "啊啊啊啊啊啊啊啊",
            "啊啊啊啊啊啊啊啊啊",
            "啊啊啊啊啊啊啊啊啊啊",
    };
    private FlowLayout mLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fra_flow, container, false);
        view.findViewById(R.id.btn_unfold).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.findViewById(R.id.btn_unfold).setVisibility(View.GONE);
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mLayout.getLayoutParams();
                lp.bottomMargin = 0;
                mLayout.setLayoutParams(lp);
//                if (mLayout.getVisibility() == VISIBLE) {
//                    TranslateAnimation show = new TranslateAnimation(
//                            Animation.RELATIVE_TO_SELF,
//                            0.0f,
//                            Animation.RELATIVE_TO_SELF,
//                            0.0f,
//                            Animation.RELATIVE_TO_SELF,
//                            0.0f,
//                            Animation.RELATIVE_TO_SELF,
//                            -1.0f);
//                    show.setDuration(500);
//                    mLayout.startAnimation(show);
//                    mLayout.setVisibility(View.GONE);
//                } else {
//                    TranslateAnimation hide = new TranslateAnimation(
//                            Animation.RELATIVE_TO_SELF,
//                            0.0f,
//                            Animation.RELATIVE_TO_SELF,
//                            0.0f,
//                            Animation.RELATIVE_TO_SELF,
//                            -1.0f,
//                            Animation.RELATIVE_TO_SELF,
//                            0.0f);
//                    hide.setDuration(500);
//                    mLayout.startAnimation(hide);
//                    mLayout.setVisibility(VISIBLE);
//                }
            }
        });
        mLayout = view.findViewById(R.id.flow_layout);
        for (int i = 0; i < 9; i++) {
            final TextView tv = new TextView(getContext());
            tv.setBackgroundColor(Color.GRAY);
            tv.setTextSize(20);
            tv.setText(mTags[i]);
            tv.setHeight(BaseUtil.dp2px(getContext(), 28));
            tv.setGravity(Gravity.CENTER);
            FlowLayout.LayoutParams lp = new FlowLayout.LayoutParams(
                    FlowLayout.LayoutParams.WRAP_CONTENT,
                    FlowLayout.LayoutParams.WRAP_CONTENT);
            int margin = BaseUtil.dp2px(getContext(), 8);
            lp.setMargins(0, margin, margin, margin);
            mLayout.addView(tv, lp);
        }
        return view;
    }
}
