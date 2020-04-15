package com.example.rkbank;

import android.content.SharedPreferences;
import android.graphics.Color;
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
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

public class offerFragment extends Fragment {
    FragmentManager fragmentManager ;
    FragmentTransaction fragmentTransaction;
    TextView offer1,offer2,offer3,offer4;
    Button b1,b2,b3,b4,b5;
    int offerA;
    int offerD,offerM;
    String no;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        View view = inflater.inflate(R.layout.fragement_offer, container, false);

        offer1=(TextView) view.findViewById(R.id.offer1);
        offer2=(TextView) view.findViewById(R.id.offer2);
        offer3=(TextView) view.findViewById(R.id.offer3);
        offer4=(TextView) view.findViewById(R.id.offer4);

        b1=(Button)view.findViewById(R.id.offer1b);
        b2=(Button)view.findViewById(R.id.offer2b);
        b3=(Button)view.findViewById(R.id.offer3b);
        b4=(Button)view.findViewById(R.id.offer4b);
        b5=(Button)view.findViewById(R.id.delOffer);

        b1.setOnClickListener(OfferButton);
        b2.setOnClickListener(OfferButton);
        b3.setOnClickListener(OfferButton);
        b4.setOnClickListener(OfferButton);
        b5.setOnClickListener(OfferDeActivedButton);
        SharedPreferences sf = getContext().getSharedPreferences("login", MODE_PRIVATE);
        no=sf.getString("no", "");
        offerA=sf.getInt("offer", 0);
        offerD=sf.getInt("offerDone", 0);
        offerM=sf.getInt("offerMoney", 0);
        if(offerA==1){
            String s=offer1.getText()+"\n"+(10-offerD)+" away from get "+offerM+"Rs";
            offer1.setText(s);
            b1.setText("Activeted");
            b1.setBackgroundColor(Color.GREEN);
        }else if(offerA==2){
            String s=offer2.getText()+"\n"+(10-offerD)+" away from get "+offerM+"Rs";
            offer2.setText(s);
            b2.setText("Activeted");
            b2.setBackgroundColor(Color.GREEN);
        }else if(offerA==3){
            String s=offer3.getText()+"\n"+(10-offerD)+" away from get "+offerM+"Rs";
            offer3.setText(s);
            b3.setText("Activeted");
            b3.setBackgroundColor(Color.GREEN);
        }else if(offerA==4){
            String s=offer4.getText()+"\n"+(10-offerD)+" away from get "+offerM+"Rs";
            offer4.setText(s);
            b4.setText("Activeted");
            b4.setBackgroundColor(Color.GREEN);
        }
        return view;
    }
    View.OnClickListener OfferButton=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SharedPreferences sf = getContext().getSharedPreferences("login", MODE_PRIVATE);
            int offerAc=sf.getInt("offer", 0);
           if(offerAc==0){
               int i=0;
               int MoneyToT=0;
               int CaseBack=0;
               switch (v.getId()){
                   case R.id.offer1b:
                       i=1;
                       CaseBack=5;
                       MoneyToT=200;
                       b1.setText("Activeted");
                       b1.setBackgroundColor(Color.GREEN);
                       break;
                   case R.id.offer2b:
                       MoneyToT=500;
                       CaseBack=15;
                       i=2;
                       b2.setText("Activeted");
                       b2.setBackgroundColor(Color.GREEN);
                       break;
                   case R.id.offer3b:
                       i=3;
                       CaseBack=20;
                       MoneyToT=700;
                       b3.setText("Activeted");
                       b3.setBackgroundColor(Color.GREEN);
                       break;
                   case R.id.offer4b:
                       CaseBack=25;
                       i=4;
                       MoneyToT=2000;
                       b4.setText("Activeted");
                       b4.setBackgroundColor(Color.GREEN);
                       break;
               }
               new firebaseDB(getContext()).UpdateOffer(no,i);
               new firebaseDB(getContext()).UpdateOfferDone(no,0);
               new firebaseDB(getContext()).UpdateOfferMoney(no,CaseBack);
               new firebaseDB(getContext()).UpdateOfferTransfer(no,MoneyToT);
           }else{
               Toast.makeText(getContext(),"1 offer is already Active",Toast.LENGTH_LONG).show();
               return;
           }
        }
    };
    View.OnClickListener OfferDeActivedButton=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            b1.setText("Active");
            b1.setBackgroundColor(Color.WHITE);
            b2.setText("Active");
            b2.setBackgroundColor(Color.WHITE);
            b3.setText("Active");
            b3.setBackgroundColor(Color.WHITE);
            b4.setText("Active");
            b4.setBackgroundColor(Color.WHITE);
            new firebaseDB(getContext()).UpdateOffer(no,0);
            new firebaseDB(getContext()).UpdateOfferDone(no,0);
            new firebaseDB(getContext()).UpdateOfferMoney(no,0);
            new firebaseDB(getContext()).UpdateOfferTransfer(no,0);

        }
    };
}
