package com.GMS.agent.helperClasses;

public class CitizenItem {

    private final String citizenName;
    private final String citizenId;
    private final int countCylinder;
    private int ivStateResource;
    private int ivPersonalImage;
    private double price;
    private boolean acceptedState;

    public CitizenItem(String citizenName, String citizenId, int countCylinder, int ivStateResource) {
        this.citizenName = citizenName;
        this.citizenId = citizenId;
        this.countCylinder = countCylinder;
        this.ivStateResource = ivStateResource;
    }

    public CitizenItem(String citizenName, String citizenId, int countCylinder, int ivStateResource, double price, boolean acceptedState) {
        this.citizenName = citizenName;
        this.citizenId = citizenId;
        this.countCylinder = countCylinder;
        this.ivStateResource = ivStateResource;
        this.price = price;
        this.acceptedState = acceptedState;
    }

    public CitizenItem(String citizenName, String citizenId, int countCylinder) {
        this.citizenName = citizenName;
        this.citizenId = citizenId;
        this.countCylinder = countCylinder;
    }

    public CitizenItem(String citizenName, String citizenId, int countCylinder, int ivStateResource, double price) {
        this.citizenName = citizenName;
        this.citizenId = citizenId;
        this.countCylinder = countCylinder;
        this.ivStateResource = ivStateResource;
        this.price = price;
    }

    public CitizenItem(String citizenName, String citizenId, int countCylinder, int ivStateResource, int ivPersonalImage) {
        this.citizenName = citizenName;
        this.citizenId = citizenId;
        this.countCylinder = countCylinder;
        this.ivStateResource = ivStateResource;
        this.ivPersonalImage = ivPersonalImage;
    }

    public boolean isAcceptedState() {
        return acceptedState;
    }

    public void setAcceptedState(boolean acceptedState) {
        this.acceptedState = acceptedState;
    }

    public double getPrice() {
        return price;
    }

    public String getCitizenName() {
        return citizenName;
    }

    public String getCitizenId() {
        return citizenId;
    }

    public int getCountCylinder() {
        return countCylinder;
    }

    public int getIvStateResource() {
        return ivStateResource;
    }

    public int getIvPersonalImage() {
        return ivPersonalImage;
    }
}
