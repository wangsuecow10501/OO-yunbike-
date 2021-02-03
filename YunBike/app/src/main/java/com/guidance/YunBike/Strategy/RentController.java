package com.guidance.YunBike.Strategy;

import android.content.Context;
import android.util.Log;

import com.guidance.YunBike.Class.FactoryMethod.RecordCreator;
import com.guidance.YunBike.Class.State_Singleton.Available;
import com.guidance.YunBike.Class.Bike;
import com.guidance.YunBike.Class.DBManager;
import com.guidance.YunBike.Class.FactoryMethod.Record;
import com.guidance.YunBike.Class.State_Singleton.Damaged;
import com.guidance.YunBike.Class.State_Singleton.Renting;
import com.guidance.YunBike.Class.User;


import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class RentController implements Controller {

    public User getUser(Context c, String username, String userpassword){
        DBManager dbManager =new DBManager(c);
        User u = null;
        try {
            u= dbManager.getuser(c,username,userpassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return u;
    }

    public Bike getBike(Context c){
        DBManager dbManager =new DBManager(c);
        Bike b = null;
        try {
            b= dbManager.getbike(c);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return b;
    }

    public void rentBike(Context c){
        DBManager dbManager =new DBManager(c);
        try {
            Bike b = dbManager.getbike(c);
            String status= b.getStatus();
            switch (status){
                case "available":
                    b.setState(Available.getInstance());
                    break;
                case "renting":
                    b.setState(Renting.getInstance());
                    break;
                case "damaged":
                    b.setState(Damaged.getInstance());
                    break;
            }
            b.renting();
            dbManager.rentbike(c,b);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void returnBike(Context c,int bike_id){
        DBManager dbManager =new DBManager(c);
        try {
            dbManager.setbiketoavailable(c,bike_id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    String formatDateInGMT(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // HH是24小時制，hh是12小時制
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8") );
        return sdf.format(date);
    }

    public void createRecord(Context c,int user_id,int Bike_id){
        Date d = new Date();
        String date = formatDateInGMT(d);

        RecordCreator rc = new RecordCreator();

        Record r = rc.FactoryMethod(user_id,Bike_id,date);

        DBManager dbManager = new DBManager(c);
        dbManager.createRecord(c,r);
        Log.e("","already done");
    }



}
