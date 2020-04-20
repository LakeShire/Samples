package com.example.samples;

import android.os.IBinder;
import android.os.IInterface;

public interface IPlay extends IInterface {
    String DESCRIPTOR = "com.example.samples.IPlay";
    int INTERFACE_PLAY = 0;
    int INTERFACE_PAUSE = 1;
    int INTERFACE_GET_TRACK = 2;

    void play();
    void pause();
    Track getTrack();
}
