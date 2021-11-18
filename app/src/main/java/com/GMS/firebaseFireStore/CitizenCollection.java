package com.GMS.firebaseFireStore;

import com.google.firebase.firestore.Exclude;

import java.util.HashMap;
import java.util.Map;

public class CitizenCollection {

    private String documentId;
    private String fullName ;
    private Map<String ,Object> additionDetails ;
    private String documentUrl ;
    private String neighborhood ;
    private int familyMember ;
    private int numberOfCylinders;
    private boolean state ;

    public CitizenCollection() {
    }

    public CitizenCollection(String fullName, Map<String, Object> additionDetails, String documentUrl, String neighborhood, int familyMember, int numberOfCylinders, boolean state) {
        this.fullName = fullName;
        this.additionDetails = additionDetails;
        this.documentUrl = documentUrl;
        this.neighborhood = neighborhood;
        this.familyMember = familyMember;
        this.numberOfCylinders = numberOfCylinders;
        this.state = state;
    }

    @Exclude
    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }


    public String getFullName() {
        return fullName;
    }

    public HashMap<String, Object> getAdditionDetails() {
        return (HashMap<String, Object>) additionDetails;
    }

    public String getDocumentUrl() {
        return documentUrl;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public int getFamilyMember() {
        return familyMember;
    }

    public int getNumberOfCylinders() {
        return numberOfCylinders;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public void setAdditionDetails(Map<String, Object> additionDetails) {
        this.additionDetails = additionDetails;
    }
}

