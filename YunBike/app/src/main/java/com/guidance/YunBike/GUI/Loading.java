package com.guidance.YunBike.GUI;

import android.os.Bundle;
import android.os.PersistableBundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.guidance.YunBike.R;

public class Loading extends AppCompatActivity implements Display{
    private Login log;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_loading);
    }

    public Loading(Login log)
    {
        this.log = log ;
    }

    public Loading(){};

    @Override
    public void display(){
        setContentView(R.layout.activity_loading);
        log.display();
    }


}
