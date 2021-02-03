package com.guidance.YunBike.Strategy;

import android.content.Context;

import com.guidance.YunBike.Class.Bike;
import com.guidance.YunBike.Class.User;
import com.guidance.YunBike.GUI.Rentpage;

public interface Controller {
    public User getUser(Context c, String account, String userpassword);
}
