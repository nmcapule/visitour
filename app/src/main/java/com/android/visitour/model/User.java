package com.android.visitour.model;




public class User
{
    public  String lastname;
    public String middlename;
    public String firstname;
    public String name;
    public String email;
    public String avata;
    public  String usertype;
    public Status status;
    public Message message;


    public User(){
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