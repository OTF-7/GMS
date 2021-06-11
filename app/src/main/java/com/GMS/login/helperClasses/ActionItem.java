package com.GMS.login.helperClasses;

public class ActionItem {
    private String agent ;
    private String representative ;
    private String aqel ;
    private int qty ;
    private double price ;
    private int seller ;
    private String neighborhood ;

    public ActionItem(String agent, String representative, String aqel, int qty, double price, int seller, String neighborhood) {
        this.agent = agent;
        this.representative = representative;
        this.aqel = aqel;
        this.qty = qty;
        this.price = price;
        this.seller = seller;
        this.neighborhood = neighborhood;
    }

    public String getAgent() {
        return agent;
    }

    public String getRepresentative() {
        return representative;
    }

    public String getAqel() {
        return aqel;
    }

    public int getQty() {
        return qty;
    }

    public double getPrice() {
        return price;
    }

    public int getSeller() {
        return seller;
    }

    public String getNeighborhood() {
        return neighborhood;
    }
}
