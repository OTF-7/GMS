package com.GMS.firebaseFireStore;

import com.google.firebase.firestore.Exclude;

import java.util.HashMap;
import java.util.Map;

public class CitizenActionDetails {
    /*
     ** EACH CITIZEN_ACTION_DETAILS DOCUMENT CONTAINS THESE :
     *  {
     *  ID , NAME , DELIVERED_QUANTITY ,TOTAL ,
     *  DELIVERED_STATE { AQEL_VERIFIED , REP_VERIFIED , DELIVERED , RECEIVED }
     * , DELIVERING_DATE , RECEIVING_DATE
     * }
     *
     */

    private String id ;
    private String name ;
    private int deliveredQuantity ;
    private double total ;
    private Map<String  ,Object> deliveredState = new HashMap<>();
    private boolean aqelVerified , repVerified , delivered , received ;
    private String deliveringDate ;
    private String receivingDate ;

    public CitizenActionDetails(String name, int deliveredQuantity, double total, String deliveringDate, String receivingDate , Map<String  ,Object> deliveredState) {
        this.name = name;
        this.deliveredQuantity = deliveredQuantity;
        this.total = total;
        this.deliveredState = deliveredState;
        /*
        this.deliveredState.put(Collection.Fields.aqelVerified.name(), aqelVerified);
        this.deliveredState.put(Collection.Fields.repVerified.name(), repVerified);
        this.deliveredState.put(Collection.Fields.delivered.name(), delivered);
        this.deliveredState.put(Collection.Fields.received.name(), received);
         */
        this.deliveringDate = deliveringDate;
        this.receivingDate = receivingDate;
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

    public int getDeliveredQuantity() {
        return deliveredQuantity;
    }

    public double getTotal() {
        return total;
    }

    public Map<String, Object> getDeliveredState() {
        return deliveredState;
    }

    public String getDeliveringDate() {
        return deliveringDate;
    }

    public String getReceivingDate() {
        return receivingDate;
    }
}

