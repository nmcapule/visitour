package com.android.visitour.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Room {
    public ArrayList<String> member;
    public Map<String, String> groupInfo;
    public String id;

    public Room(String id)
    {
        this.id = id;
    }

    public Room()
    {
        member = new ArrayList<>();
        groupInfo = new HashMap<String, String>();
    }
}
