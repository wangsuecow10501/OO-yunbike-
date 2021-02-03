package com.guidance.YunBike.Class.State_Singleton;

import com.guidance.YunBike.Class.Bike;

public class Renting implements State {//Using Singleton to prevent one bike has two or more states.

    public static Renting instance;
    private Renting(){}

    public static synchronized Renting getInstance(){
        if(instance==null)
        {
            instance = new Renting();
        }
        return instance;
    }

    public void renting(Bike b)
    {
        String s = b.getStatus();
        if(s.equals("available")) {
            b.setState(Available.getInstance());
            b.renting();
        }
        else if(s.equals("damaged"))
        {
            b.setState(Damaged.getInstance());
            b.renting();
        }
        else
        {
            System.out.println("This bike has been rented.");
        }
    }
}
