package com.GMS.representative.helperClass;

public class CitizenItemOfRep {

    private String  citizenName ;
    private String citizenId ;
    private int countCylinder ;
    private int ivStateResource ;
    private int ivPersonalImage ;

    public CitizenItemOfRep(String citizenName, String citizenId, int countCylinder, int ivStateResource)
    {
        this.citizenName = citizenName;
        this.citizenId = citizenId;
        this.countCylinder = countCylinder;
        this.ivStateResource = ivStateResource;
    }

    public CitizenItemOfRep(String citizenName, String citizenId, int countCylinder)
    {
        this.citizenName = citizenName;
        this.citizenId = citizenId;
        this.countCylinder = countCylinder;
    }

    public CitizenItemOfRep(String citizenName, String citizenId, int countCylinder, int ivStateResource, int ivPersonalImage) {
        this.citizenName = citizenName;
        this.citizenId = citizenId;
        this.countCylinder = countCylinder;
        this.ivStateResource = ivStateResource;
        this.ivPersonalImage = ivPersonalImage;
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
