package com.example.rkbank;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

class firebaseDB {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Context ctx;
    OnSuccess onSuccess;

    firebaseDB(Context context) {
        this.ctx = context;
    }

    firebaseDB(Context context, OnSuccess onSuccess) {
        this.ctx = context;
        this.onSuccess = onSuccess;
    }

    public void addAll(String name1, String emailid, String panNo, String aadarNo, String addr,
                       String pinno, int saving, int wallet, int offer, String history, int offerDone, String gender, int offerTransfer, int offerMoney) {
        dataGetterSetter gs = new dataGetterSetter(name1, emailid, panNo, aadarNo, addr, pinno, saving,
                wallet, offer, history, offerDone, gender, offerTransfer, offerMoney);

        SharedPreferences settings = this.ctx.getSharedPreferences("login", MODE_PRIVATE);
        String mobileno = settings.getString("no", "");
        db.collection("dataUser").document(mobileno).set(gs).
                addOnSuccessListener(new OnSuccessListener<Void>() {
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(firebaseDB.this.ctx, "regisetation done", Toast.LENGTH_SHORT)
                                .show();
                    }
                }).addOnCanceledListener(new OnCanceledListener() {
            @Override
            public void onCanceled() {
                Toast.makeText(firebaseDB.this.ctx, "regisetation not done", Toast.LENGTH_SHORT)
                        .show();
            }
        });
        /////////////////
        Map<String, Object> newContact = new HashMap<>();
        newContact.put(mobileno, mobileno);
        db.collection("data").document(mobileno).set(newContact);
    }

    public void ReadAllDataOfUser(final String mobileno) {
        DocumentReference user = db.collection("dataUser").document(mobileno);
        user.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    SharedPreferences.Editor editor = firebaseDB.this.ctx.getSharedPreferences("login", MODE_PRIVATE).edit();
                    editor.putString("no", mobileno);
                    editor.putString("name", doc.get("name").toString());
                    editor.putString("gender", doc.get("gender").toString());
                    editor.putString("aadarNo", doc.get("aadarNo").toString());
                    editor.putString("addr", doc.get("addr").toString());
                    editor.putString("emailid", doc.get("emailid").toString());
                    editor.putString("history", doc.get("history").toString());
                    editor.putInt("offer", Integer.parseInt(doc.get("offer").toString()));
                    editor.putInt("offerDone", Integer.parseInt(doc.get("offerDone").toString()));
                    editor.putInt("offerMoney", Integer.parseInt(doc.get("offerMoney").toString()));
                    editor.putInt("offerTransfer", Integer.parseInt(doc.get("offerTransfer").toString()));
                    editor.putString("panNo", doc.get("panNo").toString());
                    editor.putString("pinno", doc.get("pinno").toString().trim());
                    editor.putInt("saving", Integer.parseInt(doc.get("saving").toString()));
                    editor.putInt("wallet", Integer.parseInt(doc.get("wallet").toString()));
                    editor.commit();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public void UpdateData(String name, String emailid, String panNo, String addr, String aadarNo, String pinno, String gender) {
        SharedPreferences pref1 = firebaseDB.this.ctx.getSharedPreferences("login", MODE_PRIVATE);
        final String mobileno = pref1.getString("no", null);
        DocumentReference up = db.collection("dataUser").document(mobileno);
        up.update("name", name);
        up.update("emailid", emailid);
        up.update("addr", addr);
        up.update("aadarNo", aadarNo);
        up.update("gender", gender);
        up.update("pinno", pinno);
        up.update("panNo", panNo)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(firebaseDB.this.ctx, "Updated Successfully",
                                Toast.LENGTH_SHORT).show();
                        new firebaseDB(ctx).ReadAllDataOfUser(mobileno);
                    }
                });
    }

    public void UpdateSaving(final String mobileno, int saving) {
        DocumentReference up = db.collection("dataUser").document(mobileno);
        up.update("saving", saving)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        new firebaseDB(ctx).ReadAllDataOfUser(mobileno);
                    }
                });
    }

    public void UpdateWallet(final String mobileno, int wallet) {
        DocumentReference up = db.collection("dataUser").document(mobileno);
        up.update("wallet", wallet)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        new firebaseDB(ctx).ReadAllDataOfUser(mobileno);
                    }
                });
    }

    public void UpdateHistory(final String mobileno, String history) {
        DocumentReference up = db.collection("dataUser").document(mobileno);
        up.update("history", history)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        new firebaseDB(ctx).ReadAllDataOfUser(mobileno);
                    }
                });
    }

    public void UpdatePinNo(final String mobileno, String PinNo) {
        DocumentReference up = db.collection("dataUser").document(mobileno);
        up.update("pinno", PinNo)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        new firebaseDB(ctx).ReadAllDataOfUser(mobileno);
                    }
                });
    }

    public void UpdateOffer(final String mobileno, int offer) {
        DocumentReference up = db.collection("dataUser").document(mobileno);
        up.update("offer", offer)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        new firebaseDB(ctx).ReadAllDataOfUser(mobileno);
                    }
                });
    }
    public void UpdateOfferDone(final String mobileno, int offerDone) {
        DocumentReference up = db.collection("dataUser").document(mobileno);
        up.update("offerDone", offerDone)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        new firebaseDB(ctx).ReadAllDataOfUser(mobileno);
                    }
                });
    }
    public void UpdateOfferMoney(final String mobileno, int offerMoney) {
        DocumentReference up = db.collection("dataUser").document(mobileno);
        up.update("offerMoney", offerMoney)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        new firebaseDB(ctx).ReadAllDataOfUser(mobileno);
                    }
                });
    }
    public void UpdateOfferTransfer(final String mobileno, int offerTransfer) {
        DocumentReference up = db.collection("dataUser").document(mobileno);
        up.update("offerTransfer", offerTransfer)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        new firebaseDB(ctx).ReadAllDataOfUser(mobileno);
                    }
                });
    }
    public void UpdateHistoryOfOther(final String mobileno, String history) {
        DocumentReference up = db.collection("dataUser").document(mobileno);
        up.update("history", history)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                });
    }
    public void UpdateSavingOfOther(final String mobileno, int saving) {
        DocumentReference up = db.collection("dataUser").document(mobileno);
        up.update("saving", saving)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                });
    }

    public void UpdateWalletOfOther(final String mobileno, int wallet) {
        DocumentReference up = db.collection("dataUser").document(mobileno);
        up.update("wallet", wallet)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                });
    }
    public void ReadDataOfOther(final String mobileno) {
        DocumentReference user = db.collection("dataUser").document(mobileno);
        user.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    SharedPreferences.Editor editor = firebaseDB.this.ctx.getSharedPreferences("login", MODE_PRIVATE).edit();
                    editor.putString("historyOfOther", doc.get("history").toString());
                    editor.putInt("savingOfOther", Integer.parseInt(doc.get("saving").toString()));
                    editor.putInt("walletOfOther", Integer.parseInt(doc.get("wallet").toString()));
                    editor.commit();

                    onSuccess.doOnSuccess(mobileno);
                }
            }
        });
    }
}