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

public class ManageFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fra_manage, container, false);
    }

    public void startFragment(Fragment fragment) {
        FragmentManager fm = getChildFragmentManager();
        fm.beginTransaction().add(R.id.fl_manage_container, fragment).addToBackStack(fragment.getClass().getSimpleName()).commitAllowingStateLoss();
    }

    public boolean stopFragment() {
        FragmentManager fm = getChildFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
            return true;
        }
        return false;
    }
}
