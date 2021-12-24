package com.GMS.firebaseFireStore;

import com.google.firebase.firestore.Exclude;

import java.util.HashMap;
import java.util.Map;

public class ActionCollection {
    /*
     ** EACH DAILY_ACTIONS DOCUMENT CONTAINS THESE :
     * {
     * ID ,AQEL_NAME , REP_NAME , AGENT_NAME ,SELLING_PRICE ,DELIVERED_QUANTITY ,ACTION_DATE
     *  ,NEIGHBORHOOD_DETAILS { NEIGHBORHOOD_NAME ,NUMBER_OF_DELIVERS }
     *  ,STATION_DETAILS { NAME ,PURCHASING_PRICE , PURCHASED_QUANTITY}
     * }
     */

    private  String id;
    private  String aqelName;
    private  String repName;
    private  String agentName;
    private  double sellingPrice;
    private  int deliveredQuantity;
    private  String actionDate;
    private int aqelCount;
    private  Map<String, Object> neighborhoodDetails = new HashMap<>();
    private String neighborhoodName;
    private int numberOfDelivered;
    private String documentId;
    private  Map<String, Object> stationDetails = new HashMap<>();
    private String name;
    private double purchasingPrice;
    private int purchasedQuantity;

    public ActionCollection() {
    }

    public ActionCollection(String aqelName , int aqelCount, String repName, String agentName, double sellingPrice, int deliveredQuantity, String actionDate, Map<String, Object> stationDetails , Map<String, Object> neighborhoodDetails) {
        this.aqelName = aqelName;
        this.aqelCount=aqelCount;
        this.repName = repName;
        this.agentName = agentName;
        this.sellingPrice = sellingPrice;
        this.deliveredQuantity = deliveredQuantity;
        this.actionDate = actionDate;
        this.stationDetails = stationDetails;
        this.neighborhoodDetails= neighborhoodDetails;
    }

    @Exclude
    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public int getAqelCount() {
        return aqelCount;
    }

    public void setAqelCount(int aqelCount) {
        this.aqelCount = aqelCount;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Exclude
    public String getId() {
        return id;
    }

    public String getAqelName() {
        return aqelName;
    }

    public String getRepName() {
        return repName;
    }

    public String getAgentName() {
        return agentName;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public int getDeliveredQuantity() {
        return deliveredQuantity;
    }

    public String getActionDate() {
        return actionDate;
    }

    public Map<String, Object> getNeighborhoodDetails() {
        return neighborhoodDetails;
    }

    public Map<String, Object> getStationDetails() {
        return stationDetails;
    }
}
