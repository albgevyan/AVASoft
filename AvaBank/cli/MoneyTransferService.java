package AvaBank.cli;

import AvaBank.UserManagment.Customer;
import AvaBank.UserManagment.Transaction;

import java.util.ArrayList;
import java.util.List;

// this class represents Money Transfer Service. This includes transfer through Bank Terminal, Mobile Bank Application etc.
// this may include Bank Worker as well
public abstract class MoneyTransferService {
    private int serialNumber;
    private List<Transaction> transactions;

    public MoneyTransferService(int serialNumber, List <Transaction> transactions){
        this.serialNumber = serialNumber;
        this.transactions = new ArrayList<>(transactions);
    }

    public MoneyTransferService(MoneyTransferService service){
        this(service.getSerialNumber(), service.getTransactions());
    }
    private void deposit(Customer customer, double amount){
        if(amount > 0) {
            Transaction transaction = new Transaction(
                    Transaction.Type.DEPOSIT, amount, customer, null, this);

        } else {
            System.out.println("Deposit error");
        }
    }
    private void withdraw(Customer customer, double amount){
        if(customer.getBalance() - amount > 0) {
            Transaction transaction = new Transaction(
                    Transaction.Type.WITHDRAW, amount, customer, null, this);

        } else {
            System.out.println("Withdraw error");
        }
    }
    // transfer between two customers must be implemented later there
    public static boolean customerLogin(String phoneNumber, String password, Customer[] customers){
        for(int i = 0; i < customers.length; i++){
            if(customers[i].getPhoneNumber() == phoneNumber && customers[i].getPassword() == password){
                // a customer should be able to do bank operations if returns true
                // should be implemented in GUI
                return true;
            }
        }
        return false;
    }

    public int getSerialNumber() {
        return serialNumber;
    }
    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public List<Transaction> getTransactions() {
        return new ArrayList<>(transactions); // I am not sure whether List automatically deep copies this way, needs to be checked later. I have no time at this moment
    }
}
