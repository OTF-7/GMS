package com.GMS.firebaseFireStore;

import com.google.firebase.firestore.Exclude;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserCollection {
    /*
     *NOTICE THIS NOTE BELOW FOR MORE INFO FOR CONTENT OF EACH DOCUMENT
     ** EACH EMPLOYEE DOCUMENT CONTAINS THESE :
     * {
     * ID , NAME , TELEPHONE[ARRAY], ADDRESS { DISTRICT ,  NEIGHBORHOOD , QUARTER}
     *  , USER_NAME  , PASSWORD ,SOCIAL_ACCOUNTS , TYPE_OF_JOB , HIRE_DATE
     * }
     */
    private String id;
    private String name;
    private List<String> telephones ;
    private Map<String , Object> address = new HashMap<>();
    private String district ;
    private String neighborhood ;
    private String quarter ;
    private String userName ;
    private String password ;
    private List<String> socialAccounts ;
    private String typeOfJob ;
    private String hireDate ;

    public UserCollection(String name, List<String> telephones, String userName, String password,List<String> socialAccounts, String typeOfJob, String hireDate , Map<String , Object> address) {
        this.name = name;
        this.telephones = telephones;
        this.address = address;
        /*
        this.address.put(Collection.Fields.neighborhood.name() , neighborhood);
        this.address.put(Collection.Fields.quarter.name() ,quarter);
        this.address.put(Collection.Fields.district.name() ,district);
         */
        this.userName = userName;
        this.password = password;
        this.socialAccounts = socialAccounts;
        this.typeOfJob = typeOfJob;
        this.hireDate = hireDate;
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

    public List<String> getTelephones() {
        return telephones;
    }

    public Map<String, Object> getAddress() {
        return address;
    }


    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public List<String> getSocialAccounts() {
        return socialAccounts;
    }

    public String getTypeOfJob() {
        return typeOfJob;
    }

    public String getHireDate() {
        return hireDate;
    }
}
