package com.example.samples;

import android.os.Parcel;
import android.os.Parcelable;

public class Track implements Parcelable {
    private long id;
    private String title;

    protected Track(Parcel in) {
        id = in.readLong();
        title = in.readString();
    }

    public static final Creator<Track> CREATOR = new Creator<Track>() {
        @Override
        public Track createFromParcel(Parcel in) {
            return new Track(in);
        }

        @Override
        public Track[] newArray(int size) {
            return new Track[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
    }
}
