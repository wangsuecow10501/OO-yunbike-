package com.guidance.YunBike.GUI;

import android.os.Bundle;
import androidx.constraintlayout.widget.Group;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.guidance.YunBike.Class.User;
import com.guidance.YunBike.R;
import com.guidance.YunBike.Strategy.Controller;
import com.guidance.YunBike.Strategy.RentalSearchController;


public class Rentalrecord extends AppCompatActivity {

    TextView name,record,record2,record3,dis,cost;
    TextView method,method2,method3;
    Group records;
    ImageView emp;
    String username,userpwd;
    int userid,bvalue;
    String[][] rec=new String[3][2];
    Controller controller;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rentalrecord);
        userid = getSharedPreferences("UserInfo", MODE_PRIVATE)
                .getInt("Uid", 0);
        username = getSharedPreferences("UserInfo", MODE_PRIVATE)
                .getString("User", "");
        userpwd = getSharedPreferences("UserInfo", MODE_PRIVATE)
                .getString("Password", "");
        dis=findViewById(R.id.remind);
        dis.setGravity(Gravity.CENTER);
        name=findViewById(R.id.tx_name);
        emp=findViewById(R.id.empty);
        record=findViewById(R.id.tx_record);
        record2=findViewById(R.id.tx_record2);
        record3=findViewById(R.id.tx_record3);
        method=findViewById(R.id.tx_payment);
        method2=findViewById(R.id.tx_payment2);
        method3=findViewById(R.id.tx_payment3);
        records=findViewById(R.id.records);

        this.chooseController(new RentalSearchController());

    }
    public void chooseController(Controller c)
    {
        this.controller=c;
    }

    protected void onResume() {
        super.onResume();

        User u = controller.getUser(this, username, userpwd);

        RentalSearchController controller = new RentalSearchController();

        name.setText("This is "+username+"'s rental records.");

        rec=controller.getRecord(Rentalrecord.this,username,userpwd);

        if(rec[2][0]!=null)
        {
            record.setText(rec[0][1]);
            method.setText(rec[0][0]);
            record2.setText(rec[1][1]);
            method2.setText(rec[1][0]);
            record3.setText(rec[2][1]);
            method3.setText(rec[2][0]);
        }
        else{
            records.setVisibility(Group.INVISIBLE);
            emp.setVisibility(View.VISIBLE);
            name.setText("There are no rental records now.");
        }

    }

    public void gotomain(View view){
        /*Intent i = new Intent(this, Mainpage.class);
        startActivity(i);*/
        finish();
    }

}
