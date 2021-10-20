package com.GMS.firebaseFireStore;

import com.google.firebase.firestore.Exclude;

public class StationPurchasingDetails {
    /*
     ** ACH STATION_PURCHASING_DETAILS DOCUMENT CONTAINS THESE :
     *  {
     *  ID , PURCHASING_PRICE , PURCHASED_QUANTITY ,AGENT_NAME ,PURCHASING_DATE
     * }
     */
    private String id;
    private double purchasingPrice ;
    private int purchasedQuantity ;
    private String agentName ;
    private String purchasingDate ;

    public StationPurchasingDetails(double purchasingPrice, int purchasedQuantity, String agentName, String purchasingDate) {
        this.purchasingPrice = purchasingPrice;
        this.purchasedQuantity = purchasedQuantity;
        this.agentName = agentName;
        this.purchasingDate = purchasingDate;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Exclude
    public String getId() {
        return id;
    }

    public double getPurchasingPrice() {
        return purchasingPrice;
    }

    public int getPurchasedQuantity() {
        return purchasedQuantity;
    }

    public String getAgentName() {
        return agentName;
    }

    public String getPurchasingDate() {
        return purchasingDate;
    }
}
