package com.android.visitour.model;

/**
 * Created by Arvin on 9/16/2017.
 */

public class Reservation
{

    public  String user_id;
    public  String date;
    public  String time;
    public  String people;
    public  String name;
    public  String email;
    public  String phone;
    public  String note;
    public  String establishment_id;
    public  String establishment_name;
    public  String establishment_loc;

    public  Reservation()
    {

    }

    public Reservation(String user_id, String date, String time, String people, String name, String email, String phone, String note, String establishment_id, String establishment_name, String establishment_loc) {
        this.user_id = user_id;
        this.date = date;
        this.time = time;
        this.people = people;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.note = note;
        this.establishment_id = establishment_id;
        this.establishment_name = establishment_name;
        this.establishment_loc = establishment_loc;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getEstablishment_id() {
        return establishment_id;
    }

    public void setEstablishment_id(String establishment_id) {
        this.establishment_id = establishment_id;
    }

    public String getEstablishment_name() {
        return establishment_name;
    }

    public void setEstablishment_name(String establishment_name) {
        this.establishment_name = establishment_name;
    }

    public String getEstablishment_loc() {
        return establishment_loc;
    }

    public void setEstablishment_loc(String establishment_loc) {
        this.establishment_loc = establishment_loc;
    }
}
