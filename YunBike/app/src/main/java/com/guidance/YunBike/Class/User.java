package com.guidance.YunBike.Class;

import android.content.Context;

import java.sql.SQLException;

public class User {
    private String username;
    private String password;
    private String account;
    private int userid=0;

    public  User(String username,String password){
        this.username=username;
        if(password.equals(""))
        this.password=password;
    }

    public  User(Context c, int userid, String username, String password) throws SQLException {
        this.userid=userid;
        this.username=username;
        this.password=password;
        this.account =account;
    }

    public  User(Context c, int userid, String username, String password,String account) throws SQLException {
        this.userid=userid;
        this.username=username;
        this.password=password;
        this.account =account;
    }

    public String getaccount(){return this.account;}
    public String getpwd(){
        return this.password;
    }
    public int getuid(){
        return this.userid;
    }
    public String getname(){
        return this.username;
    }
}