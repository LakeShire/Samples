package com.example.samples;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class PlayService extends Service {
    PlayStub mBinder = new PlayStub();

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
