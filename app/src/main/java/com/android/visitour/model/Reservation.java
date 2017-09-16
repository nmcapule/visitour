package com.android.visitour.model;

/**
 * Created by Arvin on 9/16/2017.
 */

public class Reservation {
    public String idGroup;
    public String idCompany;
    public int guests;
    public long timestamp;
    public boolean approved;

    public Reservation(String idGropu, String idCompany, int guests, long timestamp, boolean approved) {
        this.idGroup = idGropu;
        this.idCompany = idCompany;
        this.guests = guests;
        this.timestamp = timestamp;
        this.approved = approved;
    }
}
