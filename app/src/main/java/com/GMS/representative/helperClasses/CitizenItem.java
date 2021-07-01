package com.GMS.representative.helperClasses;

public class CitizenItem {

    private String  citizenName ;
    private String citizenId ;
    private int countCylinder ;
    private int ivStateResource ;

    public CitizenItem(String citizenName, String citizenId, int countCylinder, int ivStateResource)
    {
        this.citizenName = citizenName;
        this.citizenId = citizenId;
        this.countCylinder = countCylinder;
        this.ivStateResource = ivStateResource;
    }

    public CitizenItem(String citizenName, String citizenId, int countCylinder)
    {
        this.citizenName = citizenName;
        this.citizenId = citizenId;
        this.countCylinder = countCylinder;
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
}
