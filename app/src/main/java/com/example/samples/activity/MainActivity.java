package com.example.samples.activity;

import androidx.fragment.app.FragmentManager;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import androidx.fragment.app.Fragment;

import com.example.samples.IPlay;
import com.example.samples.MyApplication;
import com.example.samples.PlayService;
import com.example.samples.PlayStub;
import com.example.samples.R;
import com.example.samples.fragment.BaseFragment;
import com.example.samples.fragment.HomeFragment;
import com.example.samples.fragment.ManageFragment;


public class MainActivity extends BaseActivity {

    private static final String FRAGMENT_MANAGE = "fragment_manage";
    private static final String FRAGMENT_HOME = "fragment_home";
    private IPlay iPlay;

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

        bindService(new Intent(this, PlayService.class), new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                iPlay = PlayStub.asInterface(service);
                iPlay.play();
                iPlay.pause();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Context.BIND_AUTO_CREATE);
    }

    public void startFragment(BaseFragment fragment) {
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
