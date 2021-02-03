package com.guidance.YunBike.Class;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.guidance.YunBike.Class.FactoryMethod.Record;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class DBManager extends SQLiteOpenHelper {
    public int counter =2;

    String DB_PATH = null;
    private static String DB_NAME = "SA.db";
    public SQLiteDatabase myDataBase;
    private final Context myContext;


    public DBManager(Context context) {
        super(context,DB_NAME, null, 10);
        this.myContext = context;
        //this.DB_PATH = "/data/data/" + context.getPackageName() + "/" + "databases/";
        this.DB_PATH = "/data/data/com.guidance.YunBike/databases/";
        Log.e("Path 1", DB_PATH);
    }

    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if (dbExist) {
            //copyDataBase();
        } else {
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }
    public void createWriteDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if (dbExist) {
        } else {
            this.getWritableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }
    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        } catch (SQLiteException e) {
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    private void copyDataBase() throws IOException {
        InputStream myInput = myContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[10];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void openDataBase() throws SQLException {
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    @Override
    public synchronized void close() {
        if (myDataBase != null)
            myDataBase.close();
        super.close();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion)
            try {
                copyDataBase();
            } catch (IOException e) {
                e.printStackTrace();

            }
    }

    public User getuser(Context context,String username,String userpassword ) throws java.sql.SQLException {
        User u=null;
        DBManager myDbManager = new DBManager(context);
        try {
            myDbManager.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        myDbManager.openDataBase();

        String sql = "SELECT * FROM User WHERE  user_name LIKE '" + username + "' AND user_password LIKE '"+userpassword+"'";
        Cursor c = myDbManager.myDataBase.rawQuery(sql, null);
        if (c.getCount() != 1) {
            return u;
        }
        else{
            c.moveToFirst();
            u = new User(context,c.getInt(0),c.getString(1),c.getString(2));
            return u;
        }
    }

    public User getuser_signup(Context context,String account) throws java.sql.SQLException {
        User u = null;
        Cursor c;
        DBManager myDbManager = new DBManager(context);
        try {
            myDbManager.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        myDbManager.openDataBase();
        String sql="";

        if (account.equals("admin")) {
            sql = "UPDATE Bike SET status= '"+ "renting" +"' WHERE bike_id ="+1+";";
            myDbManager.myDataBase.execSQL(sql);
        } else {
            sql = "SELECT * FROM User WHERE account LIKE '" + account + "'";

        c = myDbManager.myDataBase.rawQuery(sql, null);
        if (c.getCount() != 1) {
            return u;
        }
        else{
            c.moveToFirst();
            u = new User(context,c.getInt(0),c.getString(1),c.getString(2));

        }

        }
        return u;

    }



    public User getuser_login(Context context,String account,String userpassword ) throws java.sql.SQLException {
        User u=null;
        DBManager myDbManager = new DBManager(context);
        try {
            myDbManager.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        myDbManager.openDataBase();

        String sql = "SELECT * FROM User WHERE  account LIKE '" + account + "' AND user_password LIKE '"+userpassword+"'";
        Cursor c = myDbManager.myDataBase.rawQuery(sql, null);
        if (c.getCount() != 1) {
            return u;
        }
        else{
            c.moveToFirst();
            u = new User(context,c.getInt(0),c.getString(1),c.getString(2),c.getString(3));
            return u;
        }
    }



    public Bike getbike(Context context) throws java.sql.SQLException {
        Bike bk=null;
        DBManager myDbManager = new DBManager(context);
        try {
            myDbManager.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        myDbManager.openDataBase();

        String sql = "SELECT MIN(bike_id),status,station FROM Bike WHERE status LIKE '" + "available" + "'";
        Cursor c = myDbManager.myDataBase.rawQuery(sql, null);

        if (c!=null) {
            c.moveToFirst();
            bk = new Bike(c.getInt(0),c.getString(1),c.getString(2));
            return bk;
        }

        return bk;
    }

    public void bikemagic(Context context) throws java.sql.SQLException {
        DBManager myDbManager = new DBManager(context);
        try {
            myDbManager.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        myDbManager.openDataBase();

        String sql = "UPDATE Bike SET status= '"+ "available" +"' ;";
        myDbManager.myDataBase.execSQL(sql);
    }



    public void rentbike(Context context,Bike b) throws java.sql.SQLException {
        DBManager myDbManager = new DBManager(context);
        try {
            myDbManager.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        myDbManager.openDataBase();

        String sql = "UPDATE Bike SET status= '"+ "renting" +"' WHERE bike_id ="+b.getBike_id()+";";
        myDbManager.myDataBase.execSQL(sql);
    }

    public void setbiketoavailable(Context context,int bike) throws java.sql.SQLException {
        DBManager myDbManager = new DBManager(context);
        try {
            myDbManager.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        myDbManager.openDataBase();

        String sql = "UPDATE Bike SET status= '"+ "available" +"' WHERE bike_id ="+ bike +";";
        myDbManager.myDataBase.execSQL(sql);
    }


    public void setbiketodamaged(Context context,int bike) throws java.sql.SQLException {
        DBManager myDbManager = new DBManager(context);
        try {
            myDbManager.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        myDbManager.openDataBase();

        String sql = "UPDATE Bike SET status= '"+ "damaged" +"' WHERE bike_id ="+ bike +";";
        myDbManager.myDataBase.execSQL(sql);
    }

    public String[][] getRecord(Context context, int userId) throws java.sql.SQLException {
        DBManager myDbManager = new DBManager(context);

        String[][] record= new String[3][2];
        int i=0,count=0,count2=0;

        try {
            myDbManager.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        myDbManager.openDataBase();

        String sql = "SELECT * FROM Record WHERE  user_id LIKE '" + userId + "'";
        Cursor c = myDbManager.myDataBase.rawQuery(sql, null);
        c.moveToFirst();
        while(c.moveToNext())
        {
            count++;
        }
        c.moveToFirst();
        while (c.moveToNext() && i<3 ) {
            if((count-count2)<4)
            {
                record[i][0] = String.valueOf(c.getInt(2)) ;
                record[i][1] = c.getString(3);
                i++;
            }
            count2++;
        }
        return record;
    }



    public void createRecord(Context c, Record r){
        DBManager myDbManager = new DBManager(c);
        try {
            myDbManager.createWriteDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        myDbManager.openDataBase();
        String sql = "INSERT INTO Record(user_id,bike_id,time) VALUES('"+ r.getuser_id() + "','"+ r.getbike_id() + "','" + r.gettime() +"');";
        myDbManager.myDataBase.execSQL(sql);

        Log.e("","Create record");
    }

    public void createDamagedReport(Context c, DamagedReport dr){
        DBManager myDbManager = new DBManager(c);
        try {
            myDbManager.createWriteDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        myDbManager.openDataBase();
        String sql = "INSERT INTO DamagedReport(bike_id,day,description) VALUES('" + dr.getbike_id() + "','"+ dr.getdate()+"','"+dr.getdescription()+"');";
        myDbManager.myDataBase.execSQL(sql);
    }


}