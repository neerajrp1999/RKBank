package com.example.rkbank;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class homeFragment extends Fragment {
    FragmentManager fragmentManager ;
    FragmentTransaction fragmentTransaction;
    Button payButton,homePassbook,homeMoneyTransfer,homeAddMoney,AddMoneyWalletButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        payButton = (Button) view.findViewById(R.id.payhome);
        payButton.setOnClickListener(c);
        homePassbook = (Button) view.findViewById(R.id.homePassbook);
        homePassbook.setOnClickListener(c);
        homeMoneyTransfer = (Button) view.findViewById(R.id.homeMoneyTransfer);
        homeMoneyTransfer.setOnClickListener(c);
        homeAddMoney = (Button) view.findViewById(R.id.homeAddMoney);
        homeAddMoney.setOnClickListener(c);
        AddMoneyWalletButton=(Button)view.findViewById(R.id.AddMoneyWalletButton);
        return view;
    }
View.OnClickListener c=new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        switch(v.getId())// on run time get id what button os click and get id
        {
            case R.id.payhome:
                fragmentTransaction.replace(R.id.fragment_container, new MoneyTransferFragment()).addToBackStack(null).commit();
                break;
            case R.id.homePassbook:
                fragmentTransaction.replace(R.id.fragment_container, new bankFragment()).addToBackStack(null).commit();
                break;
            case R.id.homeMoneyTransfer:
                fragmentTransaction.replace(R.id.fragment_container, new MoneyTransferFragment()).addToBackStack(null).commit();
                break;
            case R.id.homeAddMoney:
                fragmentTransaction.replace(R.id.fragment_container, new AddMoneyFragment()).addToBackStack(null).commit();
                break;
            case R.id.AddMoneyWalletButton:
                fragmentTransaction.replace(R.id.fragment_container, new AddMoneyToWalletFragment()).addToBackStack(null).commit();
                break;
        }
    }
};

}