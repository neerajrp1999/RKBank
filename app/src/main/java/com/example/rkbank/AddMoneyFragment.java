package com.example.rkbank;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;


public class AddMoneyFragment extends Fragment {
    FragmentManager fragmentManager ;
    FragmentTransaction fragmentTransaction;
    EditText bankname,bankno,AmountAdd,pin_code;
    Button AddMoneyButton;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        View view = inflater.inflate(R.layout.fragment_add_money, container, false);
        AddMoneyButton = (Button) view.findViewById(R.id.AddMoneyButton);
        AddMoneyButton.setOnClickListener(c);
        bankname=(EditText) view.findViewById(R.id.bankname);
        bankno=(EditText)view.findViewById(R.id.bankno);
        AmountAdd=(EditText)view.findViewById(R.id.AmountAdd);
        pin_code=(EditText)view.findViewById(R.id.pin_code);
        return view;
    }
    View.OnClickListener c=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId())
            {
                case R.id.AddMoneyButton:
                    String name=bankname.getText().toString();
                    String no=bankno.getText().toString();
                    String a=AmountAdd.getText().toString();
                    String pin=pin_code.getText().toString().trim();
                    if(TextUtils.isEmpty(name) || TextUtils.isEmpty(no)|| TextUtils.isEmpty(a)){
                        Toast.makeText(getContext(), "Fill every Field:", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(pin.length()<4){
                        Toast.makeText(getContext(),"pin code length must be greater than 4",Toast.LENGTH_LONG).show();
                        return;
                    }
                    float amaount=Float.parseFloat(a);
                    if(amaount ==0){
                        Toast.makeText(getContext(),"Zero $ not allowed",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    SharedPreferences pref1 = getContext().getSharedPreferences("login", MODE_PRIVATE);
                    String pinno=pref1.getString("pinno",null).trim();
                    String h=pref1.getString("history",null);
                    if(pinno.equals(pin)){
                        String mobileno=pref1.getString("no",null);
                        int oldSaving=pref1.getInt("saving",0);
                        int moneyToAdd=Integer.parseInt(a);
                        int newSaving=oldSaving+moneyToAdd;
                        new firebaseDB(getContext()).UpdateSaving(mobileno,newSaving);
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        Date date = new Date();
                        String newHistory="\n-----------------" +
                                moneyToAdd+
                                "\n Rs Added by \nAccount No:"
                                +no+
                                " In\nSaving Account on "
                                +formatter.format(date)+"\n"
                                +h;
                        new firebaseDB(getContext()).UpdateHistory(mobileno,newHistory);
                        Toast.makeText(getContext(),"Money Added to your Saving Account",Toast.LENGTH_SHORT).show();
                        fragmentTransaction.replace(R.id.fragment_container, new homeFragment()).addToBackStack(null).commit();
                    }else {
                        Toast.makeText(getContext(),"Invalid pin code",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    break;
            }
        }
    };
}