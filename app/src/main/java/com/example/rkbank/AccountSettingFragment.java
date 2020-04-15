package com.example.rkbank;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

public class AccountSettingFragment extends Fragment {
    FragmentManager fragmentManager ;
    FragmentTransaction fragmentTransaction;
    Button update;
    EditText uname,uemail,upan,uaadar,uaddr,upincode;
    RadioButton updateradioButton,updateradioButton2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        View view = inflater.inflate(R.layout.fragement_account_setting, container, false);
        uname=(EditText)view.findViewById(R.id.updatename);
        uemail=(EditText)view.findViewById(R.id.updateemail);
        upan=(EditText)view.findViewById(R.id.updatepan);
        uaadar=(EditText)view.findViewById(R.id.updateaadar);
        uaddr=(EditText)view.findViewById(R.id.updateaddr);
        upincode=(EditText)view.findViewById(R.id.updatepincode);
        updateradioButton=(RadioButton)view.findViewById(R.id.updateradioButton);
        updateradioButton2=(RadioButton)view.findViewById(R.id.updateradioButton2);
        update = (Button) view.findViewById(R.id.updateregister);
        update.setOnClickListener(up);

        SharedPreferences sf = getContext().getSharedPreferences("login", MODE_PRIVATE);
        uname.setText(sf.getString("name", ""));
        uemail.setText(sf.getString("emailid", ""));
        upan.setText(sf.getString("panNo", ""));
        uaadar.setText(sf.getString("aadarNo", ""));
        uaddr.setText(sf.getString("addr", ""));
        upincode.setText(sf.getString("pinno", ""));
        String g=sf.getString("pinno", "");
        if(g=="male"|| g=="Male"){
            updateradioButton.setSelected(true);
        }else if(g=="female"|| g=="Female"){
            updateradioButton2.setSelected(true);
        }

        return view;
    }
    View.OnClickListener up=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String name1,emailid,panNo,aadarNo,Addr,pinno,Gender="Other";
            name1=uname.getText().toString().trim();
            emailid=uemail.getText().toString().trim();
            panNo=upan.getText().toString().trim();
            aadarNo=uaadar.getText().toString().trim();
            Addr=uaddr.getText().toString().trim();
            pinno=upincode.getText().toString().trim();
            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
            if( TextUtils.isEmpty(name1) || TextUtils.isEmpty(emailid) ||
                    TextUtils.isEmpty(panNo) || TextUtils.isEmpty(aadarNo) || TextUtils.isEmpty(Addr)){
                Toast.makeText(getContext(), "Fill every Field:", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!(emailid.matches(emailPattern) && emailid.length() > 0))
            {
                Toast.makeText(getContext(),"not valid email address",Toast.LENGTH_SHORT).show();
                return;
            }
            if(aadarNo.length() <12 && panNo.length()<12 ){
                Toast.makeText(getContext(),"Invalid aadar & pan no.",Toast.LENGTH_SHORT).show();
                return;
            }
            if(pinno.length() <4 ){
                Toast.makeText(getContext(),"lenght must be 4.",Toast.LENGTH_SHORT).show();
                return;
            }
            if( updateradioButton.isChecked()){
                Gender="Male";
            }else if(updateradioButton2.isChecked()){
                    Gender="Female";
            }
            new firebaseDB(getContext()).UpdateData(name1,emailid,panNo,Addr,aadarNo,pinno,Gender);
            fragmentTransaction.replace(R.id.fragment_container,new homeFragment()).commit();
        }
    };
}