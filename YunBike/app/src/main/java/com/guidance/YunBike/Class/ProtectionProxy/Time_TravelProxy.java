package com.guidance.YunBike.Class.ProtectionProxy;

import android.content.Context;

public class Time_TravelProxy implements Magic {
    public String password;
    Magic b;

    public Time_TravelProxy(String password){
        this.b = new Time_Travel();
        this.password = password;
    }

    public void TimeTravel(Context c)
    {
        if(password.equals("0000"))
        {
            b.TimeTravel(c);
        }
        else
        {
            System.out.println("Don't even try!");
        }
    }
}
