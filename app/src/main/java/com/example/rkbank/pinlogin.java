package com.example.rkbank;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class pinlogin extends Activity {
    EditText passPin;
    Button login,forget;
    SharedPreferences settings;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pinlogin);
        passPin=(EditText)findViewById(R.id.passPin);
        login=(Button)findViewById(R.id.loginButton);
        login.setOnClickListener(rg);
        forget=(Button)findViewById(R.id.forget);
        forget.setOnClickListener(f);
        settings = getSharedPreferences("login", MODE_PRIVATE);
        String no=settings.getString("no",null).toString().trim();
        new firebaseDB(getApplicationContext()).ReadAllDataOfUser(no);
    }

    View.OnClickListener rg = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String S=passPin.getText().toString().trim();
            if(S.length()<4){
                Toast.makeText(getApplicationContext(),"Otp lenght is 6", Toast.LENGTH_LONG).show();
                return;
            }

            String pin = settings.getString("pinno", "").trim();
            if(S.equals(pin)){
                startActivity(new Intent(pinlogin.this,HomeActivity.class));
                finish();
            }else{
                Toast.makeText(getApplicationContext(),"Incorect pin No//"+pin+"// "+S, Toast.LENGTH_LONG).show();
                return;
            }

        }
    };
    View.OnClickListener f=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(pinlogin.this,ForgrtPasswordActivity.class));
            finish();
        }
    };
}