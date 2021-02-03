package com.guidance.YunBike.GUI;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.guidance.YunBike.Class.User;
import com.guidance.YunBike.R;
import com.guidance.YunBike.Strategy.Controller;
import com.guidance.YunBike.Strategy.ReportController;

import java.util.ArrayList;

public class Report extends AppCompatActivity {

    Spinner spinner,spinner2;
    String username,userpwd;
    Controller controller;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair);

        username = getSharedPreferences("UserInfo", MODE_PRIVATE)
                .getString("User", "");
        userpwd = getSharedPreferences("UserInfo", MODE_PRIVATE)
                .getString("Password", "");

        controller = new ReportController();
        User u = controller.getUser(this,username,userpwd);

        ArrayList<String> bike = new ArrayList<String>();
        ArrayList<String> reason = new ArrayList<String>();
        spinner = findViewById(R.id.bike_damaged);
        spinner2 = findViewById(R.id.repair);

        bike.add("bike 1");
        bike.add("bike 2");
        bike.add("bike 3");

        reason.add("tire");
        reason.add("body");
        reason.add("handle");
        reason.add("brake");
        reason.add("plenty of them");

        ArrayAdapter<String> bikelist = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, bike);
        spinner.setAdapter(bikelist);

        ArrayAdapter<String> reasonlist = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, reason);
        spinner2.setAdapter(reasonlist);
    }

    public void choosecontroller(Controller c){
        this.controller = c;
    }


    protected void onResume(){
        super.onResume();

    }

    public void report(View view)
    {
        ReportController r = new ReportController();
        r.SendReport(this,spinner.getSelectedItemPosition()+1,spinner2.getSelectedItem().toString());
        Toast.makeText(getApplicationContext(),"Report Successed!", Toast.LENGTH_SHORT).show();

        finish();
    }

}
