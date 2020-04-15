package com.example.rkbank;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import static android.content.Context.MODE_PRIVATE;


public class bankFragment extends Fragment {
    FragmentManager fragmentManager ;
    FragmentTransaction fragmentTransaction;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        SharedPreferences sf = getContext().getSharedPreferences("login", MODE_PRIVATE);

        TextView detail,detail_s,detail_w;
        View view = inflater.inflate(R.layout.fragment_bank, container, false);
        detail = (TextView) view.findViewById(R.id.detail);
        detail.setText("Account No: " +sf.getString("no", "")+
                "\nName:" +sf.getString("name", "")+
                "\nIFC Code:RSB44444444");
        detail_s = (TextView) view.findViewById(R.id.detail_s);
        detail_s.setText("Saving bal: " +
                "\nAmount:"+sf.getInt("saving", 0));
        detail_w = (TextView) view.findViewById(R.id.detail_w);
        detail_w.setText("Wallet  " +
                "\nAmount:"+sf.getInt("wallet", 0));

        return view;
    }

}