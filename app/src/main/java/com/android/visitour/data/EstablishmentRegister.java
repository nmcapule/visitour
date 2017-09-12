package com.android.visitour.data;

/**
 * Created by Miguel Aicrag on 8/24/2017.
 */


public class EstablishmentRegister
{

    String establishment_type;
    String establishment_Name;
    String establishment_owner;
    String establishment_add;
    String establishment_lat;
    String establishment_postal;
    String establishment_email;
    String establishment_phone;
    String establishment_id;


    public  EstablishmentRegister()
    {

    }

    public EstablishmentRegister(String establishment_type, String establishment_Name, String establishment_owner, String establishment_add, String establishment_lat, String establishment_postal, String establishment_email, String establishment_phone, String establishment_id) {
        this.establishment_type = establishment_type;
        this.establishment_Name = establishment_Name;
        this.establishment_owner = establishment_owner;
        this.establishment_add = establishment_add;
        this.establishment_lat = establishment_lat;
        this.establishment_postal = establishment_postal;
        this.establishment_email = establishment_email;
        this.establishment_phone = establishment_phone;
        this.establishment_id = establishment_id;
    }

    public String getEstablishment_type() {
        return establishment_type;
    }

    public String getEstablishment_Name() {
        return establishment_Name;
    }

    public String getEstablishment_owner() {
        return establishment_owner;
    }

    public String getEstablishment_add() {
        return establishment_add;
    }

    public String getEstablishment_lat() {
        return establishment_lat;
    }

    public String getEstablishment_postal() {
        return establishment_postal;
    }

    public String getEstablishment_email() {
        return establishment_email;
    }

    public String getEstablishment_phone() {
        return establishment_phone;
    }

    public String getEstablishment_id() {
        return establishment_id;
    }
}
