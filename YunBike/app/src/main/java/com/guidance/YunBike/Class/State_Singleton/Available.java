package com.guidance.YunBike.Class.State_Singleton;

import com.guidance.YunBike.Class.Bike;

public class Available implements State {//Concrete State. Using Singleton to prevent one bike has two or more states.
    public static Available instance;
    private Available(){}

    public static synchronized Available getInstance(){
        if(instance==null)
        {
            instance = new Available();
        }
        return instance;
    }

    public void renting(Bike b)
    {
        String s = b.getStatus();
        if(s.equals("renting")) {
            b.setState(Renting.getInstance());
            b.renting();
        }
        else if(s.equals("damaged"))
        {
            b.setState(Damaged.getInstance());
            b.renting();
        }
        else
        {
            System.out.println("This bike is ok.");
        }
    }


}
