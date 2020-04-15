package com.example.rkbank;

import android.content.Intent;
import android.net.Uri;
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


public class nearshopFragment extends Fragment {
    FragmentManager fragmentManager ;
    FragmentTransaction fragmentTransaction;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

       Button shop1,shop2,shop3,shop4;
        View view = inflater.inflate(R.layout.fragement_nearshop, container, false);
        shop1 = (Button) view.findViewById(R.id.shop1);
        shop1.setText("Balaji " +
                "\nBhandup PN Road");
        shop1.setOnClickListener(v);
        shop2 = (Button) view.findViewById(R.id.shop2);
        shop2.setText("Omprakash " +
                "\nThane Kisan Nagar");
        shop2.setOnClickListener(v);
        shop3 = (Button) view.findViewById(R.id.shop3);
        shop3.setText("Parle  " +
                "\nTulsi Pada");
        shop3.setOnClickListener(v);
        shop4 = (Button) view.findViewById(R.id.shop4);
        shop4.setText("KaKa  " +
                "\nMulund:");
        shop4.setOnClickListener(v);
        return view;
    }
    View.OnClickListener v = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i=null;
            switch(v.getId()) {
                case R.id.shop1:
                    i=new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse("geo:0,0?q=Balaji+Bhandup+PN+Road"));
                    startActivity(Intent.createChooser(i,"lunch no"));
                case R.id.shop2:
                   i=new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse("geo:0,0?q=Omprakash+Thane+Kisan+Nagar"));
                    startActivity(Intent.createChooser(i,"lunch no"));
                case R.id.shop3:
                    i=new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse("geo:0,0?q=Parle+Tulsi+Pada"));
                    startActivity(Intent.createChooser(i,"lunch no"));
                case R.id.shop4:
                    i=new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse("geo:0,0?q=KaKa+Mulund"));
                    startActivity(Intent.createChooser(i,"lunch no"));
            }
        }
    };
}