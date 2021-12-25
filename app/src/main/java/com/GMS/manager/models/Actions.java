package com.GMS.manager.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Actions implements Parcelable {
    public static final Creator<Actions> CREATOR = new Creator<Actions>() {
        @Override
        public Actions createFromParcel(Parcel in) {
            return new Actions(in);
        }

        @Override
        public Actions[] newArray(int size) {
            return new Actions[size];
        }
    };
    private String neighborhoodName;
    private String representativeName;
    private String agentName;
    private String aqelName;
    private String date;
    private boolean ActionState;

    public Actions() {
    }

    protected Actions(Parcel in) {
        neighborhoodName = in.readString();
        representativeName = in.readString();
        agentName = in.readString();
        date = in.readString();
        ActionState = in.readByte() != 0;
    }

    public String getAqelName() {
        return aqelName;
    }

    public void setAqelName(String aqelName) {
        this.aqelName = aqelName;
    }

    public String getNeighborhoodName() {
        return neighborhoodName;
    }

    public void setNeighborhoodName(String neighborhoodName) {
        this.neighborhoodName = neighborhoodName;
    }

    public String getRepresentativeName() {
        return representativeName;
    }

    public void setRepresentativeName(String representativeName) {
        this.representativeName = representativeName;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isActionState() {
        return ActionState;
    }

    public void setActionState(boolean actionState) {
        ActionState = actionState;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(neighborhoodName);
        dest.writeString(representativeName);
        dest.writeString(agentName);
        dest.writeString(date);
        dest.writeByte((byte) (ActionState ? 1 : 0));
    }
}
