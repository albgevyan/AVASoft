package AvaBank.UserManagment;

import javax.print.attribute.standard.MediaSize;

public class Balance {
    private double balance;

    public Balance(){
        this.balance = 0;
    }
    public Balance(double balance){
        this.balance = balance;
    }
    public Balance(Balance other){
        this.balance = other.balance;
    }
    public boolean setBalance(double balance) {
        if(balance >= 0){
            this.balance = balance;
            return true;
        }
        System.out.println("Balance can not be negative");
        return false;
    }
    public void addAmount(double amount){
        setBalance(balance + amount);
    }
    public void subtractAmount(double amount){
        addAmount(-amount);
    }
    public double getBalance(){
        return balance;
    }
    public void printBalance(){
        System.out.println("Balance: " + toString());
    }

    public String toString(){
        return balance + "$";
    }
}
