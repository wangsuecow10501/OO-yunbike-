package com.guidance.YunBike.Strategy;

import android.content.Context;

import com.guidance.YunBike.Class.DBManager;
import com.guidance.YunBike.Class.User;

import java.sql.SQLException;

public class SignUpController implements Controller {

    public User getUser(Context c, String account, String userpassword){
        DBManager dbManager =new DBManager(c);
        User u = null;
        try {
            u= dbManager.getuser_signup(c,account);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return u;
    }

    /*public void createUser(Context c,User u){
        DBManager dbManager = new DBManager(c);
        dbManager.createUser(c, u);
    }*/


}
