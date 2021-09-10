package com.GMS.GeneralClasses;

public class HistoryItem {

    String location ;
    String date ;
    String  partnerName ;
    String station;
    int price ;
    int Qty ;

    public HistoryItem(String location, String date, String partnerName, String station, int price, int qty) {
        this.location = location;
        this.date = date;
        this.partnerName = partnerName;
        this.station = station;
        this.price = price;
        Qty = qty;
    }

    public String getLocation() {
        return location;
    }

    public String getDate() {
        return date;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public String getStation() {
        return station;
    }

    public int getPrice() {
        return price;
    }

    public int getQty() {
        return Qty;
    }
}
