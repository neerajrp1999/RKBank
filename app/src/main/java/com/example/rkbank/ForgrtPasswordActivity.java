package com.example.rkbank;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class ForgrtPasswordActivity extends Activity {
    EditText addarC,newPincode,CPanNo;
    FirebaseAuth mAuth;
    String codeSent,newPincodes;
    Button reset;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password);
        addarC=(EditText)findViewById(R.id.addarC);
        newPincode=(EditText)findViewById(R.id.newPincode);
        CPanNo=(EditText)findViewById(R.id.CPanNo);
        reset=(Button)findViewById(R.id.reset);
        reset.setOnClickListener(change);
    }
    View.OnClickListener change=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String CPanNoS,addarS;
            newPincodes=newPincode.getText().toString().trim();
            CPanNoS=CPanNo.getText().toString().trim();
            addarS=addarC.getText().toString().trim();
            if(TextUtils.isEmpty(newPincodes)||TextUtils.isEmpty(CPanNoS)||TextUtils.isEmpty(addarS)){
                Toast.makeText(getApplicationContext(), "Fill every Field:", Toast.LENGTH_SHORT).show();
                return;
            }
            if(newPincodes.length()<4||CPanNoS.length()<12||addarS.length()<12){
                Toast.makeText(getApplicationContext(), "Check the length Fields", Toast.LENGTH_SHORT).show();
                return;
            }
            SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);
            String panNo = sp.getString("panNo", "").trim();
            String aadarNo = sp.getString("aadarNo", "").trim();
            if (!(panNo.equals(CPanNoS))){
                Toast.makeText(getApplicationContext(), "Pan No is InCorrect", Toast.LENGTH_SHORT).show();
                return;
            }
            if(!(aadarNo.equals(addarS))){
                Toast.makeText(getApplicationContext(), "Aadar No is InCorrect", Toast.LENGTH_SHORT).show();
                return;
            }
            String no = sp.getString("no", "");
            new firebaseDB(getApplicationContext()).UpdatePinNo(no,newPincodes);
            startActivity(new Intent(ForgrtPasswordActivity.this,MainActivity.class));
            finish();
        }
    };
}
