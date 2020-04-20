package com.example.samples;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

public class PlayManager implements IPlay {

    private final IBinder mRemote;

    public PlayManager(IBinder remote) {
        mRemote = remote;
    }

    @Override
    public void play() {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        try {
            data.writeInterfaceToken(DESCRIPTOR);
            mRemote.transact(INTERFACE_PLAY, data, reply, 0);
            reply.readException();
        } catch (RemoteException e) {
            e.printStackTrace();
        } finally {
            data.recycle();
            reply.recycle();
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public Track getTrack() {
        return null;
    }

    @Override
    public IBinder asBinder() {
        return mRemote;
    }
}
