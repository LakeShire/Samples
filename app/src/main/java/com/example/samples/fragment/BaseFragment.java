package com.example.samples.fragment;

import androidx.fragment.app.Fragment;

import com.example.samples.activity.MainActivity;

public class BaseFragment extends Fragment {

    public void startFragment(Class<? extends BaseFragment> clazz) {
        if (clazz == null) {
            return;
        }
        if (getActivity() instanceof MainActivity) {
            try {
                BaseFragment f = clazz.newInstance();
                ((MainActivity) getActivity()).startFragment(f);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    public void startFragment(BaseFragment fragment) {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).startFragment(fragment);
        }
    }

    public void stopFragment() {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).stopFragment();
        }
    }

    public boolean onBackPress() {
        return false;
    }
}
