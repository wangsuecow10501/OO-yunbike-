package com.guidance.YunBike.Class.ProtectionProxy;

import android.content.Context;

import com.guidance.YunBike.Class.DBManager;

import java.sql.SQLException;

public class Time_Travel implements Magic {
    public String password;

    public void TimeTravel(Context c){
        DBManager dbManager = new DBManager(c);
        try {
            dbManager.bikemagic(c);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
