package com.example.samples;

import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

public class PlayStub extends Binder implements IPlay {

    public PlayStub() {
        this.attachInterface(this, DESCRIPTOR);
    }

    @Override
    public void play() {
        Log.i("liuhan", "play service play");
    }

    @Override
    public void pause() {
        Log.i("liuhan", "play service pause");
    }

    @Override
    public Track getTrack() {
        return null;
    }

    @Override
    public IBinder asBinder() {
        return this;
    }

    @Override
    protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
        switch (code) {
            case INTERFACE_PLAY:
                play();
                return true;
            case INTERFACE_PAUSE:
                pause();
                return true;
            case INTERFACE_GET_TRACK:
                break;
        }
        return super.onTransact(code, data, reply, flags);
    }

    public static IPlay asInterface(IBinder obj) {
        if (obj == null) {
            return null;
        }
        IPlay iPlay = (IPlay) obj.queryLocalInterface(IPlay.DESCRIPTOR);
        if (iPlay != null) {
            return iPlay;
        }
        return new PlayManager(obj);
    }
}
