package AvaBank.cli;

import AvaBank.core.Customer;
import AvaBank.core.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// this class represents Money Transfer Service. This includes transfer through Bank Terminal, Mobile Bank Application etc.
// this may include Bank Worker as well
// please fully implement if I missed something
public abstract class MoneyTransferService {
    private int serialNumber;
    private List<Transaction> transactions;

    public MoneyTransferService(int serialNumber) {
        this.serialNumber = serialNumber;
        this.transactions = new ArrayList<>();
    }

    public void deposit(Customer customer, double amount) {
        Transaction transaction = new Transaction(Transaction.Type.DEPOSIT, amount, customer, this);
        transaction.execute();
        transactions.add(transaction);
        customer.registerTransaction(transaction);
    }

    public void withdraw(Customer customer, double amount) {
        Transaction transaction = new Transaction(Transaction.Type.WITHDRAW, amount, customer, this);
        transaction.execute();
        transactions.add(transaction);
    }

    public static Customer customerLogin(String phoneNumber, String password) {
        // to be implemented through database
        return null; // Return null if login fails
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public List<Transaction> getTransactions() {
        return new ArrayList<>(transactions);
    }
}
