package com.example.rkbank;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;


public class MoneyTransferFragment extends Fragment implements OnSuccess {
    FragmentManager fragmentManager ;
    FragmentTransaction fragmentTransaction;
    RadioButton radioButtonSav,radioButton2wall;
    Button MoneyPay;
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    Date date = new Date();
    EditText Mobileno,AmountTra,pincodeConformation2;
    FirebaseFirestore db;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        View view = inflater.inflate(R.layout.fragment_money_transfer, container, false);
        MoneyPay = (Button) view.findViewById(R.id.MoneyPay);
        MoneyPay.setOnClickListener(c);
        Mobileno = (EditText) view.findViewById(R.id.Mobileno);
        AmountTra = (EditText) view.findViewById(R.id.AmountTra);
        radioButtonSav=(RadioButton)view.findViewById(R.id.radioButtonSav);
        radioButton2wall=(RadioButton)view.findViewById(R.id.radioButton2wall);
        pincodeConformation2= (EditText) view.findViewById(R.id.pincodeConformation2);
        db = FirebaseFirestore.getInstance();
        return view;
    }
    View.OnClickListener c=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId())
            {
                case R.id.MoneyPay:
                    String mobileS;
                    mobileS=Mobileno.getText().toString().trim();
                    String pPattern="[7-9][0-9]{9}";
                    if(mobileS.isEmpty() ||mobileS.length() < 10 || !(mobileS.matches(pPattern))){
                        Toast.makeText(getContext(),"Mobile no is invalide",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    ExistMobileNo(mobileS);
            }
        }
    };
    private void ExistMobileNo(final String e) {
        DocumentReference user = db.collection("data").document(e);
        user.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    if (doc.get(e) == null) {
                        Toast.makeText(getContext(),"Mobile no doesnt exist ",Toast.LENGTH_SHORT).show();
                    } else {
                        new firebaseDB(getContext(), MoneyTransferFragment.this).ReadDataOfOther(e);

                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }

    private void updateUsers(String e){
        SharedPreferences sf = getContext().getSharedPreferences("login", MODE_PRIVATE);
        String no=sf.getString("no", "");
        String history=sf.getString("history", "");
        String pinno=sf.getString("pinno", "");
        int saving=sf.getInt("saving", 0);
        int wallet=sf.getInt("wallet", 0);
        String historyOfOther= sf.getString("historyOfOther", "");
        int savingOfOther= sf.getInt("savingOfOther", 0);
        int walletOfOther= sf.getInt("walletOfOther",0);
        int i=0;
        String amount;
        amount=AmountTra.getText().toString().trim();
        if(TextUtils.isEmpty(amount)) {
            Toast.makeText(getContext(), "Fill every Field:", Toast.LENGTH_SHORT).show();
            return;
        }
        int amountn=Integer.parseInt(amount);
        if(amountn ==0){
            Toast.makeText(getContext(),"Zero $ not alove",Toast.LENGTH_SHORT).show();
            return;
        }
        if( radioButtonSav.isChecked()){
            i=1;
        }else{
            if(radioButton2wall.isChecked()){
                i=2;
            }
        }
        String pass=pincodeConformation2.getText().toString().trim();
        if(pass.length() <4 ){
            Toast.makeText(getContext(),"lenght must be 4.",Toast.LENGTH_SHORT).show();
            return;
        }
        if(pass.equals(pinno)){

            if(i==0){
                Toast.makeText(getContext(),"Something is wong try again later.",Toast.LENGTH_SHORT).show();
                return;
            }
            else if(i==1) {
                int amountI=Integer.parseInt(amount);
                if(saving>=amountI) {
                    String HistoryFrom="-----------------" +
                            "\n "+amountI+"Rs Sended from Saving \n to Account No:"
                            +e+" on "
                            +formatter.format(date)+"\n"
                            +history;
                    new firebaseDB(getContext()).UpdateHistory(no,HistoryFrom);
                    new firebaseDB(getContext()).UpdateSaving(no,(saving-amountI));

                    String newHistoryTo="-----------------" +
                            "\n "+amountI+"Rs Received from Saving \n to Account No:"
                            +no+" on "
                            +formatter.format(date)+"\n"
                            +historyOfOther;
                    new firebaseDB(getContext()).UpdateHistoryOfOther(e,newHistoryTo);
                    new firebaseDB(getContext()).UpdateSavingOfOther(e,(savingOfOther+amountI));
                    UpdateSavingOfferDone(amountI);
                    Toast.makeText(getContext(),"Done",Toast.LENGTH_LONG).show();
                    fragmentTransaction.replace(R.id.fragment_container, new homeFragment()).addToBackStack(null).commit();
                }
                else{
                    Toast.makeText(getContext(),"Not that much bal you have first add money",Toast.LENGTH_LONG).show();
                    return;
                }
            }
            else if(i==2) {
                int amountI=Integer.parseInt(amount);
                if(wallet>=amountI){
                    String HistoryFrom="-----------------" +
                            "\n "+amountI+"Rs Sended from Wallet \n to Account No:"
                            +e+" on "
                            +formatter.format(date)+"\n"
                            +history;
                    new firebaseDB(getContext()).UpdateHistory(no,HistoryFrom);
                    new firebaseDB(getContext()).UpdateWallet(no,(wallet-amountI));

                    String newHistoryTo="-----------------" +
                            "\n "+amountI+"Rs Received from Wallet \n to Account No:"
                            +no+" on "
                            +formatter.format(date)+"\n"
                            +historyOfOther;
                    new firebaseDB(getContext()).UpdateHistoryOfOther(e,newHistoryTo);
                    new firebaseDB(getContext()).UpdateWalletOfOther(e,(walletOfOther+amountI));
                    Toast.makeText(getContext(),"Done",Toast.LENGTH_LONG).show();
                    fragmentTransaction.replace(R.id.fragment_container, new homeFragment()).addToBackStack(null).commit();

                }
                else{
                    Toast.makeText(getContext(),"Not that much bal you have first add money",Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }
    }

    private void UpdateSavingOfferDone(int SendedAmond){
        SharedPreferences sf = getContext().getSharedPreferences("login", MODE_PRIVATE);
        String no=sf.getString("no", "");
        String history=sf.getString("history", "");
        int offerD=sf.getInt("offerDone", 0);
        int wallet=sf.getInt("wallet", 0);
        int offerM=sf.getInt("offerMoney", 0);
        int offerT=sf.getInt("offerTransfer", 0);
        if(SendedAmond>=offerT){
            offerD+=1;
            if(offerD==10){
                offerD=0;
                wallet+=offerM;
                new firebaseDB(getContext()).UpdateWallet(no,wallet);
                new firebaseDB(getContext()).UpdateOffer(no,0);
                new firebaseDB(getContext()).UpdateOfferDone(no,0);
                new firebaseDB(getContext()).UpdateOfferMoney(no,0);
                new firebaseDB(getContext()).UpdateOfferTransfer(no,0);
                String h="-----------------" +
                        "\n "+offerM+"Rs Added to yor Wallet on "
                        +formatter.format(date)+"\n"
                        +history;
                new firebaseDB(getContext()).UpdateHistory(no,h);
            }
             new firebaseDB(getContext()).UpdateOfferDone(no,offerD);
        }
    }
    @Override
    public void doOnSuccess(String mobileNo) {
        MoneyTransferFragment.this.updateUsers(mobileNo);
    }
}