package com.guidance.YunBike.GUI;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.guidance.YunBike.R;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.guidance.YunBike.Class.Command.Command;
import com.guidance.YunBike.Class.Command.Reciever;
import com.guidance.YunBike.Class.Command.locklock;
import com.guidance.YunBike.Class.Command.openlock;
import com.guidance.YunBike.Strategy.Controller;
import com.guidance.YunBike.Strategy.LoginController;
import com.guidance.YunBike.R;
import com.guidance.YunBike.Class.User;
import com.guidance.YunBike.Strategy.RentController;
import com.guidance.YunBike.Class.ProtectionProxy.Time_TravelProxy;
import com.guidance.YunBike.Class.ProtectionProxy.Magic;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class Mainpage extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    TextView name,time;
    String username, userpwd,account;
    EditText proxy_input;
    Button return_b,timetravel;
    int userid;
    Handler handler;
    int count = 3600;
    int rent=0;
    int choose,bike_id;
    boolean b;
    final Context c = this;
    Controller controller;
    String proxy_password;
    Reciever openorlock;
    private final static int REQUEST_ENABLE_BT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        openorlock= new Reciever(1);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        userid = getSharedPreferences("UserInfo", MODE_PRIVATE)
                .getInt("Uid", 0);
        username = getSharedPreferences("UserInfo", MODE_PRIVATE)
                .getString("User", "");
        userpwd = getSharedPreferences("UserInfo", MODE_PRIVATE)
                .getString("Password", "");
        account = getSharedPreferences("UserInfo", MODE_PRIVATE)
                .getString("Account", "");

        this.chooseController(new LoginController());//select the Strategy client wants
        User u = controller.getUser(this, username, userpwd);

        time = (TextView) findViewById(R.id.timer);
        return_b = findViewById(R.id.return_button);
        timetravel = findViewById(R.id.admin_btn);
        timetravel.setVisibility(View.INVISIBLE);

        if(username.equals("admin"))
        {
            timetravel.setVisibility(View.VISIBLE);
        }
        Intent intent = getIntent();
        choose = intent.getIntExtra("choose",rent);
        bike_id = intent.getIntExtra("bike",0);

        Log.e("",Integer.toString(choose));



    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(23.695850, 120.534012);
        mMap.addMarker(new MarkerOptions().position(sydney).title("YunBike").snippet("YunBike Station"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,16));
    }

    public void chooseController(Controller c)
    {
        this.controller=c;
    }

    final Runnable runnable = new Runnable() {
        public void run() {
            // TODO Auto-generated method stub
            if (count >= 3600) {
                time.setText("1:00:00");
                count--;
                handler.postDelayed(runnable, 1000);
            }
            else if(count<3600 && count>0)
            {
                time.setText(Integer.toString(count/60)+":"+Integer.toString(count%60));
                count--;
                handler.postDelayed(runnable, 1000);
            }
            else {
                time.setText("Time's up!");
            }
        }

    };

    protected void onResume() {
        super.onResume();

        User u = controller.getUser(this, account, userpwd);

        userid=u.getuid();

        if(choose==1)
        {
            handler = new Handler();
            handler.post(runnable);
            time.setVisibility(View.VISIBLE);
            return_b.setVisibility(View.VISIBLE);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("The bike you rent is：bike"+bike_id)
                    .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });

            AlertDialog dialog = builder.create();
            dialog.show();

            choose=0;//set your choose to default
        }
    }

    public void return_btn(View view){
        final RentController r = new RentController();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Return your bike?")
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        closelock();
                        time.setVisibility(View.INVISIBLE);
                        return_b.setVisibility(View.INVISIBLE);
                        r.returnBike(c,bike_id );
                        Toast.makeText(getApplicationContext(),"Return Successed!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();

        r.createRecord(this, userid , bike_id);

    }

    public void openlock(){
        Command open= new openlock(openorlock,bike_id);
        open.execute();
    }

    public void closelock(){
        Command close = new locklock(openorlock,bike_id);
        close.execute();
    }

    public void timetravel(View view){
        proxy_input = new EditText(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Authentication")
                .setView(proxy_input)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String proxy_password = proxy_input.getText().toString();
                        Magic proxy = new Time_TravelProxy(proxy_password);
                        proxy.TimeTravel(c);
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void gotorent(View view) {
        Intent i = new Intent(this, Rentpage.class);
        startActivity(i);
        openlock();
    }

    public void gotorecord(View view) {

        //closelock();
        Intent i = new Intent(this, Rentalrecord.class);
        startActivity(i);
    }

    public void gotomanage(View view) {
        Intent i = new Intent(this, Manageaccount.class);
        startActivity(i);
    }

    public void gotorepair(View view){
        Intent i = new Intent(this, Report.class);
        startActivity(i);
    }
}