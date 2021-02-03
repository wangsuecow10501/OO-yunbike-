package com.guidance.YunBike.Strategy;

import android.content.Context;

import com.guidance.YunBike.Class.DBManager;
import com.guidance.YunBike.Class.DamagedReport;
import com.guidance.YunBike.Class.User;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class ReportController implements Controller {

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

    String formatDateInGMT(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // HH是24小時制，hh是12小時制
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8") );
        return sdf.format(date);
    }

    public void SendReport(Context c,int bike_id,String reason){
        Date da = new Date();
        String date = formatDateInGMT(da);

        DBManager dbManager =new DBManager(c);
        DamagedReport dr = new DamagedReport(bike_id,date,reason);
        try {
            dbManager.setbiketodamaged(c,bike_id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dbManager.createDamagedReport(c,dr);
    }

}
