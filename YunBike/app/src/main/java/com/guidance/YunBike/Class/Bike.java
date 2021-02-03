package com.guidance.YunBike.Class;

import com.guidance.YunBike.Class.State_Singleton.State;

public class Bike {
    private int bike_id;
    private  String status,station;
    private State state;


    public Bike(String status, String station){
        this.status = status;
        this.station=station;
    }

    public Bike(int bike_id, String status,String station){
        this.bike_id=bike_id;
        this.status=status;
        this.station=station;
    }

    public void setState(State state){
        this.state = state;
    }

    public void renting(){
        state.renting(this);
    }

    public State getState(){
        return this.state;
    }


    public int getBike_id(){ return this.bike_id; }
    public  String getStatus(){ return this.status;}
    public String getStation(){ return this.station;}
}
