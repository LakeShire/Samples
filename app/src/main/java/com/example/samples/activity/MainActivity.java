package com.example.samples.activity;

import androidx.fragment.app.FragmentManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.example.samples.R;
import com.example.samples.fragment.HomeFragment;
import com.example.samples.fragment.ManageFragment;


public class MainActivity extends BaseActivity {

    private static final String FRAGMENT_MANAGE = "fragment_manage";
    private static final String FRAGMENT_HOME = "fragment_home";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentByTag(FRAGMENT_MANAGE);
        if (fragment == null) {
            fragment = new ManageFragment();
            fm.beginTransaction().add(R.id.fl_container, fragment, FRAGMENT_MANAGE).commit();
        }
        Fragment homeFragment = fm.findFragmentByTag(FRAGMENT_HOME);
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
            fm.beginTransaction().add(R.id.fl_home, homeFragment, FRAGMENT_HOME).commit();
        }
    }

    public void startFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        Fragment f = fm.findFragmentByTag(FRAGMENT_MANAGE);
        if (f instanceof ManageFragment) {
            ((ManageFragment) f).startFragment(fragment);
        }
    }

    public void stopFragment() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment f = fm.findFragmentByTag(FRAGMENT_MANAGE);
        if (f instanceof ManageFragment) {
            ((ManageFragment) f).stopFragment();
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment f = fm.findFragmentByTag(FRAGMENT_MANAGE);
        if (f instanceof ManageFragment) {
            if (((ManageFragment) f).stopFragment()) {
                return;
            }
        }
        super.onBackPressed();
    }
}
