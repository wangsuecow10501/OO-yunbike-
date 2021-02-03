package com.guidance.YunBike.Strategy;


import android.content.Context;

import com.guidance.YunBike.Class.DBManager;
import com.guidance.YunBike.Class.User;

import java.sql.SQLException;

public class RentalSearchController implements Controller {

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

    public String[][] getRecord(Context c, String username,String  userpassword){
        DBManager dbManager =new DBManager(c);
        User u = null;
        String[][] record=new String[3][2];
        try {
            u=dbManager.getuser(c,username,userpassword);
            record= dbManager.getRecord(c,u.getuid());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return record;
    }

}
