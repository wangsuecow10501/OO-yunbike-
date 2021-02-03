package com.guidance.YunBike.GUI;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.guidance.YunBike.R;
import com.guidance.YunBike.Strategy.Controller;
import com.guidance.YunBike.Strategy.SignUpController;

public class Signup extends AppCompatActivity {
    EditText accountinput,OTPinput ;
    String account;
    Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        accountinput = findViewById(R.id.ed_account);
        this.chooseController(new SignUpController());
    }


    public void signup(View view) {
        account = accountinput.getText().toString();

        if (controller.getUser(this, account,"0000") != null){
            SendOTP();
        }
        else{
            Toast.makeText(this, "Wrong Account", Toast.LENGTH_LONG).show();
        }
    }

    public void SendOTP() {
        final int otp=(int)(Math.random()*8999)+1000;
        final String otp2=Integer.toString(otp);

        OTPinput = new EditText(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("OTP Authentication")
                .setMessage(otp2)
                .setView(OTPinput)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String ans = OTPinput.getText().toString();
                        int i=0;
                        if (otp2.equals(ans) ) {
                            Toast.makeText(getApplicationContext(),"OTP Right", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "OTP Wrong", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void chooseController(Controller c)
    {
        this.controller=c;
    }

}
