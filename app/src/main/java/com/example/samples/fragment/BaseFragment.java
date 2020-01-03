package com.example.samples.fragment;

import androidx.fragment.app.Fragment;

import com.example.samples.activity.MainActivity;

public class BaseFragment extends Fragment {

    public void startFragment(Class<? extends Fragment> clazz) {
        if (clazz == null) {
            return;
        }
        if (getActivity() instanceof MainActivity) {
            try {
                Fragment f = clazz.newInstance();
                ((MainActivity) getActivity()).startFragment(f);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    public void startFragment(Fragment fragment) {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).startFragment(fragment);
        }
    }

    public void stopFragment() {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).stopFragment();
        }
    }
}
