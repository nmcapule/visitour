package com.android.visitour.model;

/**
 * Created by Arvin on 9/16/2017.
 */

public class Reservation {
    public static int RESERVATION_TYPE_HOTEL = 2;
    public static int RESERVATION_TYPE_RESTAURANT = 1;
    public static int RESERVATION_TYPE_ANY = 0;

    public String idGroup;
    public String idCompany;
    public int guests;
    public int type;
    public long arrival;
    public long departure;
    public boolean approved;

    public Reservation(String idGropu, String idCompany) {
        this.idGroup = idGropu;
        this.idCompany = idCompany;
    }
}
