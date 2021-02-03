package com.guidance.YunBike.Strategy;

import android.content.Context;

import com.guidance.YunBike.Class.DBManager;
import com.guidance.YunBike.Class.User;
import java.sql.SQLException;

public class LoginController implements Controller {//Concrete Strategy

    public User getUser(Context c, String account, String userpassword){
        DBManager dbManager =new DBManager(c);
        User u = null;
        try {
            u= dbManager.getuser_login(c,account,userpassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return u;
    }

}
