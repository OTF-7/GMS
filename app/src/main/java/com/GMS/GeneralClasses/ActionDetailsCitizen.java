package com.GMS.GeneralClasses;

public class ActionDetailsCitizen {
    private String citizenName ;
    private int qty ;
    private String aqelName;
    private String repName;
    private String status ;

    public String getStatus() {
        return status;
    }

    public ActionDetailsCitizen(String status , String citizenName, int qty, String aqelName, String repName) {
        this.citizenName = citizenName;
        this.qty = qty;
        this.aqelName = aqelName;
        this.repName = repName;
        this.status = status;
    }

    public String getCitizenName() {
        return citizenName;
    }

    public int getQty() {
        return qty;
    }

    public String getAqelName() {
        return aqelName;
    }

    public String getRepName() {
        return repName;
    }
}
