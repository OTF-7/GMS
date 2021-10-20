package com.GMS.firebaseFireStore;

import com.google.firebase.firestore.Exclude;

import java.util.List;
import java.util.Map;

public class StationCollection {
    /*
     ** EACH STATIONS DOCUMENT CONTAINS THESE :
     * {
     * ID ,NAME ,ICON , TELEPHONES[ARRAY] , LOCATION{LATITUDE , LONGITUDE}
     * }
     */
    private  String id ;
    private String name ;
    private List<String> telephones ;
    private Map<String , Object> location ;
    private String longitude ;
    private String latitude ;

    public StationCollection( String name, List<String> telephones ,Map<String , Object> location) {
        this.name = name;
        this.telephones =telephones;
        this.location =location;
        /*
        this.location.put(Collection.Fields.longitude.name(), longitude);
        this.location.put(Collection.Fields.latitude.name(), latitude);
         */
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

    public Map<String, Object> getLocation() {
        return location;
    }
}
