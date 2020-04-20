package com.example.samples.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import static android.view.View.VISIBLE;

public class AnimationUtil {

    public static void alphaShow(View view) {
        if (view != null) {
            view.setAlpha(0);
            view.setVisibility(VISIBLE);
            view.animate().alpha(1).setDuration(1000);
        }
    }

    public static void alphaHide(final View view) {
        if (view != null) {
            view.animate().alpha(0).setDuration(1000).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    view.setVisibility(View.GONE);
                    view.animate().setListener(null);
                }
            });
        }
    }

    public static void scaleShow(View view) {
        if (view != null) {
            view.setScaleX(0);
            view.setScaleY(0);
            view.setVisibility(VISIBLE);
            view.animate().scaleX(1).scaleY(1).setDuration(1000);
        }
    }

    public static void scaleHide(final View view) {
        if (view != null) {
            view.animate().scaleX(0).scaleY(0).setDuration(1000).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    view.setVisibility(View.GONE);
                    view.animate().setListener(null);
                }
            });
        }
    }

    public static void transShow(final View view) {
        if (view != null) {
            TranslateAnimation show = new TranslateAnimation(
                    Animation.RELATIVE_TO_SELF,
                    0.0f,
                    Animation.RELATIVE_TO_SELF,
                    0.0f,
                    Animation.RELATIVE_TO_SELF,
                    -1.0f,
                    Animation.RELATIVE_TO_SELF,
                    0.0f);
            show.setDuration(1000);
            view.startAnimation(show);
            view.setVisibility(VISIBLE);
        }
    }

    public static void transHide(final View view) {
        if (view != null) {
            TranslateAnimation hide = new TranslateAnimation(
                    Animation.RELATIVE_TO_SELF,
                    0.0f,
                    Animation.RELATIVE_TO_SELF,
                    0.0f,
                    Animation.RELATIVE_TO_SELF,
                    0.0f,
                    Animation.RELATIVE_TO_SELF,
                    -1.0f);
            hide.setDuration(1000);
            view.startAnimation(hide);
            view.setVisibility(View.GONE);
        }
    }
}
