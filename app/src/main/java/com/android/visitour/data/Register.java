package com.android.visitour.data;

/**
 * Created by Miguel Aicrag on 8/9/2017.
 */

public class Register
{

    String userID;
    String email;
    String user_type;

    public Register()
    {

    }

    public Register(String UserID, String Email, String User_type) {
        userID = UserID;
        email = Email;
        user_type = User_type;
    }

    public String getUser_type() {
        return user_type;
    }

    public String getUserID() {
        return userID;
    }

    public String getEmail() {
        return email;
    }
}

