package com.GMS.firebaseFireStore;

import com.google.firebase.firestore.Exclude;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NeighborhoodCollection {
    /*
     ** EACH NEIGHBORHOOD DOCUMENT CONTAINS THESE :
     * {
     * ID , NAME ,TELEPHONES[ARRAY] , AQEL_DETAILS { NAME , PICTURE ,USER_NAME , PASSWORD ,TELEPHONES[ARRAY] , SOCIAL_ACCOUNTS[ARRAY] , HIRE_DATE}
     * NUMBER_OF_FAMILIES ,LOCATION {LATITUDE , LONGITUDE}
     * }
     */

    private  String id;
    private  String name;
    private  List<String> telephones;
    private  Map<String, Object> aqelDetails = new HashMap<>();
    private String aqelName;
    private Map<String, Object> location;
    private  int numberOfFamilies;
    private String userName;
    private String password;
    private String hireDate;
    private List<String> socialAccounts;

    public NeighborhoodCollection(String name, List<String> telephones,  int numberOfFamilies,Map<String, Object> aqelDetails , Map<String, Object> location) {
        this.name = name;
        this.telephones = telephones;
        this.aqelDetails = aqelDetails;
        this.location = location;
        this.numberOfFamilies = numberOfFamilies;

    }

    public void setId(String id) {
        this.id = id;
    }

    @Exclude
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getTelephones() {
        return telephones;
    }

    public Map<String, Object> getAqelDetails() {
        return aqelDetails;
    }

    public Map<String, Object> getLocation() {
        return location;
    }

    public int getNumberOfFamilies() {
        return numberOfFamilies;
    }
}
