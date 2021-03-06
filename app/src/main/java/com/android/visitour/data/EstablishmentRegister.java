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
    String establishment_website;
    String establishment_reserv;


    public  EstablishmentRegister()
    {

    }

    public EstablishmentRegister(String establishment_type, String establishment_Name, String establishment_owner, String establishment_add, String establishment_lat, String establishment_postal, String establishment_email, String establishment_phone, String establishment_id, String establishment_website, String establishment_reserv) {
        this.establishment_type = establishment_type;
        this.establishment_Name = establishment_Name;
        this.establishment_owner = establishment_owner;
        this.establishment_add = establishment_add;
        this.establishment_lat = establishment_lat;
        this.establishment_postal = establishment_postal;
        this.establishment_email = establishment_email;
        this.establishment_phone = establishment_phone;
        this.establishment_id = establishment_id;
        this.establishment_website = establishment_website;
        this.establishment_reserv = establishment_reserv;
    }

    public String getEstablishment_website() {
        return establishment_website;
    }

    public void setEstablishment_website(String establishment_website) {
        this.establishment_website = establishment_website;
    }

    public String getEstablishment_reserv() {
        return establishment_reserv;
    }

    public void setEstablishment_reserv(String establishment_reserv) {
        this.establishment_reserv = establishment_reserv;
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

    public void setEstablishment_type(String establishment_type) {
        this.establishment_type = establishment_type;
    }

    public void setEstablishment_Name(String establishment_Name) {
        this.establishment_Name = establishment_Name;
    }

    public void setEstablishment_owner(String establishment_owner) {
        this.establishment_owner = establishment_owner;
    }

    public void setEstablishment_add(String establishment_add) {
        this.establishment_add = establishment_add;
    }

    public void setEstablishment_lat(String establishment_lat) {
        this.establishment_lat = establishment_lat;
    }

    public void setEstablishment_postal(String establishment_postal) {
        this.establishment_postal = establishment_postal;
    }

    public void setEstablishment_email(String establishment_email) {
        this.establishment_email = establishment_email;
    }

    public void setEstablishment_phone(String establishment_phone) {
        this.establishment_phone = establishment_phone;
    }

    public void setEstablishment_id(String establishment_id) {
        this.establishment_id = establishment_id;
    }
}
