package com.guidance.YunBike.GUI;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.guidance.YunBike.Class.Bike;
import com.guidance.YunBike.Class.Command.Command;
import com.guidance.YunBike.Class.Command.Reciever;
import com.guidance.YunBike.Class.Command.openlock;
import com.guidance.YunBike.Strategy.Controller;
import com.guidance.YunBike.Strategy.RentController;
import com.guidance.YunBike.R;
import com.guidance.YunBike.Class.User;
import com.guidance.YunBike.GUI.Mainpage;

import java.util.ArrayList;

public class Rentpage extends AppCompatActivity {
    int userid;
    String uname,upwd;
    Spinner spinner;
    TextView timer;
    Button rent;
    User u;
    Bike b;
    Reciever rc= new Reciever(1);

    ArrayList<String> station = new ArrayList<String>();
    Controller controller;
    RentController r = new RentController();
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rentpage);
        spinner = findViewById(R.id.spinner);
        rent = findViewById(R.id.rent);

        station.add("MA station");

        userid = getSharedPreferences("UserInfo", MODE_PRIVATE)
                .getInt("Uid", 0);
        uname = getSharedPreferences("UserInfo", MODE_PRIVATE)
                .getString("User", "");
        upwd = getSharedPreferences("UserInfo", MODE_PRIVATE)
                .getString("Password", "");

        this.chooseController(new RentController());

        u = controller.getUser(this, uname, upwd);
        b = r.getBike(this);

        timer=findViewById(R.id.timer);

        ArrayAdapter<String> stationlist = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, station);
        spinner.setAdapter(stationlist);
    }

    public void chooseController(Controller c)
    {
        this.controller=c;
    }

    public void rent(View view){

        Toast.makeText(getApplicationContext(),"Rent Successed!", Toast.LENGTH_SHORT).show();

        int choose = 1;
        Intent i = new Intent(this, Mainpage.class);
        i.putExtra("choose",choose);
        i.putExtra("bike",b.getBike_id());
        Log.e("bike id",Integer.toString(b.getBike_id()));
        startActivity(i);
    }

    public void gotomain(View view){
        timer.setVisibility(View.VISIBLE);
        Intent i = new Intent(this, Mainpage.class);
        startActivity(i);
    }



}
