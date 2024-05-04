package AvaBank.core;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Customer extends User implements TransactionOperations {

    private class BankAccount implements Cloneable, TransactionOperations {
        private class Balance {
            private double balance;

            public Balance() {
                this.balance = 0;
            }

            public void addAmount(double amount) {
                if(amount > 0){
                    setBalance(balance + amount);
                }
                else {
                    throw new IllegalArgumentException();
                }
            }

            public void subtractAmount(double amount) {
                if(amount > 0){
                    setBalance(balance - amount);
                }
                else {
                    throw new IllegalArgumentException();
                }
            }

            public double getBalance() {
                return balance;
            }

            public void setBalance(double balance) {
                if (balance >= 0) {
                    this.balance = balance;
                } else {
                    throw new IllegalArgumentException("Balance cannot be negative");
                }
            }

            @Override
            public String toString() {
                return "Balance: " + balance;
            }
        }

        private Balance balance;
        private String code;
        public BankAccount(String code) {
            balance = new Balance();
            if(code.length() < 5 || code.length() > 20){
                throw new IllegalArgumentException("bank account code is too long");
            }
        }

        @Override
        public void depositAmount(double amount) {
            balance.addAmount(amount);
        }

        @Override
        public void withdrawAmount(double amount) {
            balance.subtractAmount(amount);
        }

        public Balance getBalance() {
            return balance;
        }

        public void closeAccount() {
            balance.setBalance(0);
        }

        @Override
        protected Object clone() {
            try {
                BankAccount clonedAccount = (BankAccount) super.clone();
                return clonedAccount;
            } catch (CloneNotSupportedException e) {
                System.err.println("Clone not supported: " + e.getMessage());
                return null;
            }
        }
    }

    private BankAccount bankAccount; // maybe later create list of bank accounts to have more than 1 bank account
    private List<Transaction> transactions;

    public Customer(String fullName, Gender gender, String userId, String email, String phoneNumber, String address) {
        super(fullName, gender, userId, email, phoneNumber, address);
        this.transactions = new ArrayList<>();
        registerBankAccount();
    }

    public void registerBankAccount(){
        Scanner sc = new Scanner(System.in);
        bankAccount = new BankAccount(sc.nextLine());
    }
    @Override
    public void depositAmount(double amount) {
        bankAccount.depositAmount(amount);
    }

    @Override
    public void withdrawAmount(double amount) {
        bankAccount.withdrawAmount(amount);
    }

    public void transferMoneyToAnotherPerson(double amount, Customer receiver) {
        this.bankAccount.withdrawAmount(amount);
        receiver.depositAmount(amount);
    }

    public void registerTransaction(Transaction transaction){
        transactions.add(transaction);
    }

    public BankAccount getBankAccount() {
        return (BankAccount) bankAccount.clone();
    }

    public void printBalance() {
        System.out.println(bankAccount.getBalance());
    }
    public void closeBankAccount() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("account_data.txt"));
            // Save account data

        } catch (IOException e) {
            System.err.println("Error writing account data: " + e.getMessage());
        }
        bankAccount.closeAccount();
        bankAccount = null;
    }

    public List<Transaction> getTransactions() {
        return new ArrayList<>(transactions);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
