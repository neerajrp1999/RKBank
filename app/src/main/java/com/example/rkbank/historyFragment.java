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
import android.widget.TextView;

import static android.content.Context.MODE_PRIVATE;


public class historyFragment extends Fragment {
    FragmentManager fragmentManager ;
    FragmentTransaction fragmentTransaction;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        TextView h1;
        View view = inflater.inflate(R.layout.fragement_history, container, false);
        h1 = (TextView) view.findViewById(R.id.h);
        SharedPreferences pref1 = getActivity().getSharedPreferences("login", MODE_PRIVATE);
        String h=pref1.getString("history",null);
        h1.setText(h);
        return view;
    }
}