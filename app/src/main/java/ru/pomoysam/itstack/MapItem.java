package ru.pomoysam.itstack;

import android.os.Parcel;
import android.os.Parcelable;

public class MapItem implements Comparable<MapItem>, Parcelable {

    public String address;
    public float distance;
    public int backgroundId;
    public double longitude;
    public double latitude;

    public MapItem(String address, float distance, int backgroundId, double longitude, double latitude) {
        this.address = address;
        this.distance = distance;
        this.backgroundId = backgroundId;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    protected MapItem(Parcel in) {
        address = in.readString();
        distance = in.readFloat();
        backgroundId = in.readInt();
        longitude = in.readDouble();
        latitude = in.readDouble();
    }

    public static final Creator<MapItem> CREATOR = new Creator<MapItem>() {
        @Override
        public MapItem createFromParcel(Parcel in) {
            return new MapItem(in);
        }

        @Override
        public MapItem[] newArray(int size) {
            return new MapItem[size];
        }
    };

    @Override
    public int compareTo(MapItem another) {
        if (distance > another.distance) {
            return 1;
        }
        else if (distance <  another.distance) {
            return -1;
        }
        else {
            return 0;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(address);
        parcel.writeFloat(distance);
        parcel.writeInt(backgroundId);
        parcel.writeDouble(longitude);
        parcel.writeDouble(latitude);
    }
}

