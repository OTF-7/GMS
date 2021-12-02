package com.GMS.firebaseFireStore;

import com.google.firebase.firestore.Exclude;

import java.util.HashMap;
import java.util.Map;

public class CitizenActionDetails {


    private String documentId;
    private  String idInParent;
    private  String name;
    private  int deliveredQuantity;
    private  long quantityRequired;
    private  double total;
    private Map<String, Object> deliveredState = new HashMap<>();
   // private boolean aqelVerified, repVerified, delivered, received;
    private  String deliveringDate;
    private  String receivingDate;

    public CitizenActionDetails() {
     }


    public CitizenActionDetails(String idInParent, String name, int deliveredQuantity, double total, String deliveringDate, String receivingDate, Map<String, Object> deliveredState ,long quantityRequired) {
        this.idInParent = idInParent;
        this.name = name;
        this.quantityRequired= quantityRequired;
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


    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    @Exclude
    public String getDocumentId() {
        return documentId;
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

    public String getIdInParent() {
        return idInParent;
    }

    public void setDeliveredQuantity(int deliveredQuantity) {
        this.deliveredQuantity = deliveredQuantity;
    }

    public void setDeliveredState(Map<String, Object> deliveredState) {
        this.deliveredState = deliveredState;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public long getQuantityRequired() {
        return quantityRequired;
    }
}

