package com.GMS.firebaseFireStore;

import android.graphics.Bitmap;

import com.google.firebase.firestore.Exclude;

public class CitizenCollection {

    private String id ;
    private String name ;
    private String neighborhood ;
    private int familyMember;
    private int numberOfCylinders;
    private String hireDate ;
    private String aqelAddition ;
    private String repConfirmation ;
    private String dateOfConfirmation ;
    private Bitmap document ;
    private boolean stateOfConfirmation;

    public CitizenCollection(String name, String neighborhood, int numberOfFamily, int countOfCylinder, String hireDate, String aqelAddition, String repConfirmation, String dateOfConfirmation, Bitmap document, boolean stateOfConfirmation) {
        this.name = name;
        this.neighborhood = neighborhood;
        this.familyMember = numberOfFamily;
        this.numberOfCylinders = countOfCylinder;
        this.hireDate = hireDate;
        this.aqelAddition = aqelAddition;
        this.repConfirmation = repConfirmation;
        this.dateOfConfirmation = dateOfConfirmation;
        this.document = document;
        this.stateOfConfirmation = stateOfConfirmation;
    }


    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
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

    public String getHireDate() {
        return hireDate;
    }

    public String getAqelAddition() {
        return aqelAddition;
    }

    public String getRepConfirmation() {
        return repConfirmation;
    }

    public String getDateOfConfirmation() {
        return dateOfConfirmation;
    }

    public Bitmap getDocument() {
        return document;
    }

    public boolean isStateOfConfirmation() {
        return stateOfConfirmation;
    }
}
