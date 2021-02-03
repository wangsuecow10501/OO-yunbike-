package com.guidance.YunBike.Class.State_Singleton;

import com.guidance.YunBike.Class.Bike;

public class Damaged implements State {//Using Singleton to prevent one bike has two or more states.

    public static Damaged instance;
    private Damaged(){}

    public static synchronized Damaged getInstance(){
        if(instance==null)
        {
            instance = new Damaged();
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
        else if(s.equals("renting"))
        {
            b.setState(Renting.getInstance());
            b.renting();
        }
        else
        {
            System.out.println("This bike is damaged.");
        }

    }
}
