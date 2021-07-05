package com.GMS.representative.helperClass;

public class CitizenAdditionRequest {

    private String citizenName;
    private String citizenAddress;
    private String citizenHireDate;


    public CitizenAdditionRequest(String citizenName, String citizenAddress, String citizenHireate) {
        this.citizenName = citizenName;
        this.citizenAddress = citizenAddress;
        this.citizenHireDate = citizenHireate;
    }

    public String getCitizenName() {
        return citizenName;
    }

    public String getCitizenAddress() {
        return citizenAddress;
    }

    public String getCitizenHireDate() {
        return citizenHireDate;
    }
}
