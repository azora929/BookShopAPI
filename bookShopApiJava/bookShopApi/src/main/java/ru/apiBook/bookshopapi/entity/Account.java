package ru.apiBook.bookshopapi.entity;

import java.util.ArrayList;

public class Account {
    private double money;
    private ArrayList<Books> purchaseHistory;

    public Account(double money, ArrayList<Books> purchaseHistory) {
        this.money = money;
        this.purchaseHistory = purchaseHistory;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public ArrayList<Books> getPurchaseHistory() {
        return purchaseHistory;
    }

    public void setPurchaseHistory(ArrayList<Books> purchaseHistory) {
        this.purchaseHistory = purchaseHistory;
    }

    public boolean checkIdBook(int id){
        for(Books book:purchaseHistory){
            if(book.getId() == id){
                return true;
            }
        }
        return false;
    }
    public Books getBookByIdAccount(int id) {
        for(Books book: purchaseHistory) {
            if(book.getId() == id) {
                return book;
            }
        }
        return null;
    }

    public boolean topUpBalance(double money){
        if(money > 0){
            this.money += money;
            return true;
        }
        return false;
    }

}