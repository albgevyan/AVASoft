package AvaBank.UserManagment;
import java.util.ArrayList;
import java.util.List;

public class Customer extends User {
    private Balance balance;
    private List<Transaction> transactions;

    public Customer(Gender gender, String userId, String fullName, String email, String phoneNumber, String address, String citizenship) {
        super(gender, userId, fullName, email, phoneNumber, address, citizenship);
        this.balance = new Balance();
        this.transactions = new ArrayList<>();
    }

    public void deposit(double amount){
        if(amount > 0) {
            Transaction transaction = new Transaction("Deposit", amount);
            balance.addAmount(amount);
            transactions.add(transaction);
        } else {
            System.out.println("Error: Deposit amount should be greater than 0");
        }
    }

    public void withdraw(double amount){
        if(amount > 0 && balance.getBalance() >= amount) {
            Transaction transaction = new Transaction("Withdrawal", amount);
            balance.subtractAmount(amount);
            transactions.add(transaction);
        } else {
            System.out.println("Error: Withdrawal amount should be greater than 0 and less than or equal to balance");
        }
    }

    public void transferToAnotherCustomer(double amount, Customer receiver){
        if(amount > 0 && balance.getBalance() >= amount) {
            Transaction transaction = new Transaction("Transfer", amount, this, receiver);
            balance.subtractAmount(amount);
            receiver.balance.addAmount(amount);
            transactions.add(transaction);
        } else {
            System.out.println("Error: Transfer amount should be greater than 0 and less than or equal to balance");
        }
    }

    public Balance getBalance() {
        return new Balance(balance);
    }

    public List<Transaction> getTransactions() {
        return new ArrayList<>(transactions);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}