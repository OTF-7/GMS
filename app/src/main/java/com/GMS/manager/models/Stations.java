package com.GMS.manager.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Stations implements Parcelable {
    private String stationName;
    public static final Creator<Stations> CREATOR = new Creator<Stations>() {
        @Override
        public Stations createFromParcel(Parcel in) {
            return new Stations(in);
        }

        @Override
        public Stations[] newArray(int size) {
            return new Stations[size];
        }
    };
    private int stationImage;
    private String stationManagerName;
    private String stationNeighborhoodName;
    private String stationManagerPhone;

    public Stations() {
    }

    protected Stations(Parcel in) {
        stationName = in.readString();
        stationImage = in.readInt();
        stationManagerName = in.readString();
        stationNeighborhoodName = in.readString();
        stationManagerPhone = in.readString();
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public int getStationImage() {
        return stationImage;
    }

    public void setStationImage(int stationImage) {
        this.stationImage = stationImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(stationName);
        dest.writeInt(stationImage);
        dest.writeString(stationManagerName);
        dest.writeString(stationNeighborhoodName);
        dest.writeString(stationManagerPhone);
    }


    public String getStationManagerName() {
        return stationManagerName;
    }

    public void setStationManagerName(String stationManagerName) {
        this.stationManagerName = stationManagerName;
    }

    public String getStationNeighborhoodName() {
        return stationNeighborhoodName;
    }

    public void setStationNeighborhoodName(String stationNeighborhoodName) {
        this.stationNeighborhoodName = stationNeighborhoodName;
    }

    public String getStationManagerPhone() {
        return stationManagerPhone;
    }

    public void setStationManagerPhone(String stationManagerPhone) {
        this.stationManagerPhone = stationManagerPhone;
    }
}
