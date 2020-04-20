package com.example.samples.fragment.canvas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.samples.R;
import com.example.samples.fragment.BaseFragment;
import com.example.samples.view.LeafLoadingView;

import java.util.Timer;
import java.util.TimerTask;

public class LeafLoadingFragment extends BaseFragment {

    private LeafLoadingView mProgress;
    private int progress = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fra_leaf_loading, container, false);
        mProgress = view.findViewById(R.id.progress);
        final Timer timer = new Timer();
        final TimerTask task = new TimerTask() {
            @Override
            public void run() {
                progress += 20;
                mProgress.setProgress(progress);
                if (progress == 100) {
                    timer.cancel();
                }
            }
        };
        timer.schedule(task, 0, 3000);
        mProgress.leafStart();
        return view;
    }
}
