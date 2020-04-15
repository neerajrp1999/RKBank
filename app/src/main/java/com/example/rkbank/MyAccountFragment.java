package com.example.rkbank;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import static android.content.Context.MODE_PRIVATE;

public class MyAccountFragment extends Fragment {
    FragmentManager fragmentManager ;
    FragmentTransaction fragmentTransaction;
    Button update;
    TextView Detail;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        View view = inflater.inflate(R.layout.my_account, container, false);
        Detail =(TextView)view.findViewById(R.id.DetailShow);
        SharedPreferences sf = getContext().getSharedPreferences("login", MODE_PRIVATE);
        StringBuffer d=new StringBuffer("");
        d.append("\nName\t").append(sf.getString("name", "")).
                append("\nAccount No\t").append(sf.getString("no", "")).
                append("\nEmail\t").append(sf.getString("emailid", "")).
                append("\nPan No\t").append(sf.getString("panNo", "")).
                append("\nAadar No\t").append(sf.getString("aadarNo", "")).
                append("\nAddress\t").append(sf.getString("addr", ""));
        Detail.setText(d);
        return view;
    }
}