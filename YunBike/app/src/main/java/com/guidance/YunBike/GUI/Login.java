package com.guidance.YunBike.GUI;

import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.guidance.YunBike.Strategy.Controller;
import com.guidance.YunBike.Strategy.LoginController;
import com.guidance.YunBike.R;
import com.guidance.YunBike.Class.User;

public class Login extends AppCompatActivity implements Display {
    EditText name,password;
    SharedPreferences pref;
    User u;
   Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        name=findViewById(R.id.login_user);
        password=findViewById(R.id.login_pwd);
        Stetho.initializeWithDefaults(this);
        this.chooseController(new LoginController());
    }

    public void chooseController(Controller c)
    {
        this.controller=c;
    }

    public void display()
    {
        setContentView(R.layout.activity_login);
    }


    public void gotomain(View view) {
        String username=name.getText().toString();
        String userpawd=password.getText().toString();

        u=controller.getUser(Login.this,username,userpawd);
        if(u!=null){
            pref = getSharedPreferences("UserInfo", MODE_PRIVATE);
            pref.edit()
                    .putInt("Uid", u.getuid()).putString("User", u.getname()).putString("Password", u.getpwd()).putString("Account",u.getaccount())
                    .commit();

        Intent intent = new Intent(this, Mainpage.class);
        startActivity(intent);

        }else{
            Toast.makeText(this, "Wrong Account or Password", Toast.LENGTH_LONG).show();
        }
    }

    public void gotosignup(View view) {
        Intent intent = new Intent(this, Signup.class);
        startActivity(intent);
    }

}
