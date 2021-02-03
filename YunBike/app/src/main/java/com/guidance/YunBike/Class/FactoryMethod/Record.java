package com.guidance.YunBike.Class.FactoryMethod;

public class Record {
    private int Record_id,user_id,bike_id;
    private String time;


    public Record(int user_id,int bike_id,String time){
        this.user_id = user_id;
        this.bike_id = bike_id;
        this.time=time;
    }

    public Record(int Record_id,int user_id,int bike_id,String time){
        this.Record_id=Record_id;
        this.user_id = user_id;
        this.bike_id=bike_id;
        this.time=time;
    }

    public int getRecord_id(){ return this.Record_id; }
    public int getuser_id(){ return this.user_id;}
    public int getbike_id(){ return this.bike_id;}
    public String gettime(){return this.time;}


}
