package com.example.rkbank;

class dataGetterSetter {
    String name,history,emailid,panNo,aadarNo,addr,pinno;
    int saving,wallet,offer,offerDone,offerTransfer,offerMoney;
    String gender;
    public dataGetterSetter(String name, String emailid, String panNo, String aadarNo,
                            String addr, String pinno, int saving, int wallet, int offer,
                            String history,int offerDone,String gender,int offerTransfer,int offerMoney) {
        this.name = name;
        this.emailid = emailid;
        this.panNo = panNo;
        this.aadarNo = aadarNo;
        this.addr = addr;
        this.pinno = pinno;
        this.saving = saving;
        this.wallet = wallet;
        this.offer = offer;
        this.history = history;
        this.offerDone=offerDone;
        this.gender=gender;
        this.offerMoney=offerMoney;
        this.offerTransfer=offerTransfer;
    }

    public int getOfferTransfer() {
        return offerTransfer;
    }

    public void setOfferTransfer(int offerTransfer) {
        this.offerTransfer = offerTransfer;
    }

    public int getOfferMoney() {
        return offerMoney;
    }

    public void setOfferMoney(int offerMoney) {
        this.offerMoney = offerMoney;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public int getOfferDone() {
        return offerDone;
    }

    public void setOfferDone(int offerDone) {
        this.offerDone = offerDone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getPanNo() {
        return panNo;
    }

    public void setPanNo(String panNo) {
        this.panNo = panNo;
    }

    public String getAadarNo() {
        return aadarNo;
    }

    public void setAadarNo(String aadarNo) {
        this.aadarNo = aadarNo;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getPinno() {
        return pinno;
    }

    public void setPinno(String pinno) {
        this.pinno = pinno;
    }

    public int getSaving() {
        return saving;
    }

    public void setSaving(int saving) {
        this.saving = saving;
    }

    public int getWallet() {
        return wallet;
    }

    public void setWallet(int wallet) {
        this.wallet = wallet;
    }

    public int getOffer() {
        return offer;
    }

    public void setOffer(int offer) {
        this.offer = offer;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }
}