package com.example.rkbank;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.TimeUnit;


public class register extends AppCompatActivity {
    Button register;
    EditText name,  email,  pan, aadar, addr, pin,otp;
    RadioButton radioButton, radioButton2;
    FirebaseAuth mAuth;
    String codeSent;
    FirebaseFirestore db;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        SharedPreferences settings = getSharedPreferences("login", MODE_PRIVATE);
        String mobileno = settings.getString("no", "");
        sendVerificationCode(mobileno);
        mAuth = FirebaseAuth.getInstance();
        db= FirebaseFirestore.getInstance();
        otp = (EditText) findViewById(R.id.otpR);
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        pan = (EditText) findViewById(R.id.pan);
        aadar = (EditText) findViewById(R.id.aadar);
        addr = (EditText) findViewById(R.id.addr);
        pin = (EditText) findViewById(R.id.pincode);
        radioButton = (RadioButton) findViewById(R.id.radioButton);
        radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
        register = (Button) findViewById(R.id.register);
        register.setOnClickListener(rg);
    }

    View.OnClickListener rg = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String otpS=otp.getText().toString().trim();
            if(otpS.length()<6){
                Toast.makeText(getApplicationContext(),"Otp lenght is 6", Toast.LENGTH_LONG).show();
                return;
            }
            verifySignInCode(otp);
        }
    };
    private void sendVerificationCode( String phone){

        String pPattern="[7-9][0-9]{9}";
        if(phone.isEmpty() ||phone.length() < 10 || !(phone.matches(pPattern))){
            Toast.makeText(this,"Mobile no is invalide",Toast.LENGTH_SHORT).show();
            return;
        }

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91"+phone,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                TaskExecutors.MAIN_THREAD,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            codeSent = s;
        }
    };
    private void verifySignInCode(EditText editTextCode){
        String code = editTextCode.getText().toString();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent, code);
        signInWithPhoneAuthCredential(credential);
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),
                                    "Login Successfull", Toast.LENGTH_LONG).show();
                            firebaseDB f=new firebaseDB(getApplicationContext());
                            String  name1, emailid,  panNo, aadarNo, Addr, pinno;
                            name1 = name.getText().toString().trim();
                            emailid = email.getText().toString().trim();
                            panNo = pan.getText().toString().trim();
                            aadarNo = aadar.getText().toString().trim();
                            Addr = addr.getText().toString().trim();
                            pinno = pin.getText().toString().trim();
                            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                            if ( TextUtils.isEmpty(name1) || TextUtils.isEmpty(emailid) ||
                                    TextUtils.isEmpty(panNo) || TextUtils.isEmpty(aadarNo) || TextUtils.isEmpty(Addr)) {
                                Toast.makeText(getApplicationContext(), "Fill every Field:", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            if (!(emailid.matches(emailPattern) && emailid.length() > 0)) {
                                Toast.makeText(getApplicationContext(), "not valid email address", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (aadarNo.length() < 12 && panNo.length() < 12) {
                                Toast.makeText(getApplicationContext(), "Invalid aadar & pan no.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (pinno.length() < 4) {
                                Toast.makeText(getApplicationContext(), "lenght must be 4.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            String g = "Other";
                            if (radioButton.isChecked()) {
                                g = "Male";
                            } else if (radioButton2.isChecked()) {
                                g = "Female";
                            }
                            f.addAll(name1,emailid,panNo,aadarNo,Addr,pinno,0,0,0,"",0,g,0,0);
                            startActivity(new Intent(register.this,MainActivity.class));
                            finish();

                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(getApplicationContext(),
                                        "Incorrect Verification Code ", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }

}
