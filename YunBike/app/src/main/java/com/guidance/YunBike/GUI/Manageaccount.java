package com.guidance.YunBike.GUI;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.guidance.YunBike.Class.User;
import com.guidance.YunBike.Strategy.Controller;
import com.guidance.YunBike.Strategy.LoginController;
import com.guidance.YunBike.R;


public class Manageaccount extends AppCompatActivity {

    TextView name,balance;
    String username,userpwd;
    int userid;
    Controller controller;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manageaccount);

        userid = getSharedPreferences("UserInfo", MODE_PRIVATE)
                .getInt("Uid", 0);
        username = getSharedPreferences("UserInfo", MODE_PRIVATE)
                .getString("User", "");
        userpwd = getSharedPreferences("UserInfo", MODE_PRIVATE)
                .getString("Password", "");

        this.chooseController(new LoginController());
        User u = controller.getUser(this, username, userpwd);
    }

    public void chooseController(Controller c)
    {
        this.controller=c;
    }

    public void gotorepair(View view){
        Intent i = new Intent(this, Report.class);
        startActivity(i);
    }

    public void gotorecord(View view) {
        Intent i = new Intent(this, Rentalrecord.class);
        startActivity(i);
    }
}
