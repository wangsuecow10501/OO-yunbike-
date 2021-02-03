package com.guidance.YunBike.Class;

public class DamagedReport {

    private int report_id,bike_id;
    private  String date,description;

    public DamagedReport(int bike_id, String date,String description){
        this.bike_id = bike_id;
        this.date=date;
        this.description=description;
    }

    public DamagedReport(int report_id,int bike_id, String date,String description){
        this.report_id=report_id;
        this.bike_id = bike_id;
        this.date=date;
        this.description=description;
    }

    public int getReport_id(){ return this.report_id; }
    public  int getbike_id(){ return this.bike_id;}
    public String getdate(){ return this.date;}
    public String getdescription(){ return this.description;}
}
