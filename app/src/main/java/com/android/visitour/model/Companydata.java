package com.android.visitour.model;



public class Companydata
{
    public  String companyname;
    public String companyowner;
    public String name;
    public String email;
    public String avata;
    public  String usertype;
    public Status status;
    public Message message;


    public Companydata(){
        status = new Status();
        message = new Message();
        status.isOnline = false;
        status.timestamp = 0;
        message.idReceiver = "0";
        message.idSender = "0";
        message.text = "";
        message.timestamp = 0;
    }
}
