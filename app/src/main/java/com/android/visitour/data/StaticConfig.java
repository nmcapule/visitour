package com.android.visitour.data;


public class StaticConfig
{
    public static int REQUEST_CODE_REGISTER = 2000;
    public static String STR_EXTRA_ACTION_LOGIN = "login";
    public static String STR_EXTRA_ACTION_RESET = "resetpass";
    public static String STR_EXTRA_ACTION = "action";
    public static String STR_EXTRA_USERTYPE = "usertype";
    public static String STR_EXTRA_USERNAME = "username";
    public static String STR_EXTRA_PASSWORD = "password";
    public static String STR_EXTRA_LASTNAME = "lastname";
    public static String STR_EXTRA_MIDDLENAME = "middlename";
    public static String STR_EXTRA_FIRSTNAME = "firstname";

    public static String STR_DEFAULT_BASE64 = "default";
    public static String STR_FULL_NAME = STR_EXTRA_FIRSTNAME+" "+STR_EXTRA_MIDDLENAME+" "+STR_EXTRA_LASTNAME;

    public static String UID = "";
    //TODO only use this UID for debug mode
//    public static String UID = "6kU0SbJPF5QJKZTfvW1BqKolrx22";
    public static String INTENT_KEY_CHAT_FRIEND = "friendname";
    public static String INTENT_KEY_CHAT_AVATA = "friendavata";
    public static String INTENT_KEY_CHAT_ID = "friendid";
    public static String INTENT_KEY_CHAT_ROOM_ID = "roomid";
    public static String INTENT_KEY_ESTABLISHMENT_ID = "establid";
    public static String INTENT_KEY_ESTABLISHMENT = "establname";
    public static long TIME_TO_REFRESH = 10 * 1000;
    public static long TIME_TO_OFFLINE = 2 * 60 * 1000;

}
