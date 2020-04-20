package com.example.samples.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.samples.util.BaseUtil;
import com.example.samples.view.CustomTipsView;
import com.example.samples.R;
import com.example.samples.view.Tip;

public class TipFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_tip, container, false);
//        final Button btn5 = view.findViewById(R.id.btn5);
//        btn5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CustomTipsView tipsView = new CustomTipsView(getActivity());
//                int[] color = { Color.BLACK, Color.RED, Color.GREEN, Color.BLUE};
//                for (int i = 0; i < 12; i++) {
//                    tipsView.addTip(new Tip(btn5, R.layout.popup_common, 1000, null, i, color[i%4]));
//                }
//                tipsView.showAllTips();
//            }
//        });

        final Button btn1 = view.findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "再次点击", Toast.LENGTH_SHORT).show();
                    }
                });
                Tip tip = new Tip(btn1, "Hello")
                        .dir(Tip.BOTTOM)
                        .arrow(Tip.ARROW_LEFT)
                        .autoDismiss(true)
                        .offsetX(0)
                        .offsetY(BaseUtil.dp2px(getContext(), 8))
                        .onClick(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getContext(), "点击", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .onDismiss(new PopupWindow.OnDismissListener() {
                            @Override
                            public void onDismiss() {
                                Toast.makeText(getContext(), "消失", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .level(Tip.STYLE_MASK)
                        .shape(Tip.SHAPE_ROUND_RECT)
                        .shadowRadius(BaseUtil.dp2px(getContext(), 8))
                        .marginX(BaseUtil.dp2px(getContext(), 16))
                        .focusClickable(false);
                CustomTipsView tipsView = new CustomTipsView(getActivity(), TipFragment.this);
                tipsView.addTip(tip);
                tipsView.showAllTips();
            }
        });

        final Button btn2 = view.findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tip tip = new Tip(btn2, "World").dir(Tip.TOP).arrow(Tip.ARROW_LEFT);
                CustomTipsView tipsView = new CustomTipsView(getActivity(), TipFragment.this);
                tipsView.addTip(tip);
                tipsView.showAllTips();
            }
        });

        final Button btn3 = view.findViewById(R.id.btn3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomTipsView tipsView = new CustomTipsView(getActivity(), TipFragment.this);
//                Tip tip = new Tip(btn3, "Hello").dir(Tip.BOTTOM).arrow(Tip.ARROW_LEFT);
                Tip tip = new Tip(btn3, R.layout.popup_common_pic).dir(Tip.BOTTOM).arrow(Tip.ARROW_LEFT).backgroundColor(Color.parseColor("#f86442"));
                tipsView.addTip(tip);
                tipsView.showAllTips();
            }
        });

        final Button btn4 = view.findViewById(R.id.btn4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tip tip = new Tip(btn4, "World").dir(Tip.BOTTOM);
                CustomTipsView tipsView = new CustomTipsView(getActivity(), TipFragment.this);
                tipsView.addTip(tip);
                tipsView.showAllTips();
            }
        });
        return view;
    }
}
