package com.example.samples.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.samples.R;

import java.util.Stack;

public class ManageFragment extends Fragment {

    private Stack<BaseFragment> mStack = new Stack<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fra_manage, container, false);
    }

    public void startFragment(BaseFragment fragment) {
        FragmentManager fm = getChildFragmentManager();
        fm.beginTransaction()
                .add(R.id.fl_manage_container, fragment)
//                .addToBackStack(fragment.getClass().getSimpleName())
                .commitAllowingStateLoss();
        mStack.add(fragment);
    }

    public boolean stopFragment() {
        FragmentManager fm = getChildFragmentManager();
//        if (fm.getBackStackEntryCount() > 0) {
//            fm.popBackStack();
        if (!mStack.isEmpty()) {
            BaseFragment fragment = mStack.peek();
            boolean result = fragment.onBackPress();
            if (result) {
                return true;
            } else {
                fm.beginTransaction()
                        .remove(fragment)
                        .commitAllowingStateLoss();
                mStack.pop();
                return true;
            }
        }
        return false;
    }
}
