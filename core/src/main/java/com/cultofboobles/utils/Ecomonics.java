package com.cultofboobles.utils;

public class Ecomonics {

    private int moneyCount = 10;

    private int soap = 10;

    private int bubbleFavor = 10;

    public Ecomonics() {

    }

    public int getMoneyCount() {
        return this.moneyCount;
    }

    public boolean addToMoney(int amount) {
        if(this.moneyCount + amount < 0) {
            return false;
        } else {
            this.moneyCount += amount;
            return true;
        }
    }

    public int getSoap() {
        return this.soap;
    }

    public boolean addSoap(int amount) {
        if(this.soap + amount < 0) {
            return false;
        } else {
            this.soap += amount;
            return true;
        }
    }

    public int getBubbleFavor() {
        return this.bubbleFavor;
    }

    public void addBubbleFavor(int amount) {
        this.bubbleFavor += amount;
    }

}
