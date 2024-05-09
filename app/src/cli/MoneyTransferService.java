package AvaBank.cli;

import AvaBank.core.Customer;
import AvaBank.core.Transaction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * The MoneyTransferService class represents a service for transferring money, such as through bank terminals or mobile bank applications.
 * It includes methods for depositing and withdrawing money, as well as functionality for customer login and transaction history.
 */
public abstract class MoneyTransferService {
    ArrayList<Transaction> transactions;
    private int serialNumber;

    /**
     * Constructs a MoneyTransferService object with the specified serial number.
     *
     * @param serialNumber the serial number of the money transfer service
     */
    public MoneyTransferService(int serialNumber) {
        this.serialNumber = serialNumber;
        this.transactions = new ArrayList<>();
    }

    /**
     * Deposits a specified amount of money into the customer's account.
     *
     * @param customer the customer whose account will be deposited into
     * @param amount   the amount of money to be deposited
     */
    public void deposit(Customer customer, BigDecimal amount) {
        Transaction transaction = new Transaction(Transaction.Type.DEPOSIT, amount, customer);
        transaction.execute();
        getTransactions().add(transaction);
        customer.registerTransaction(transaction);
    }

    public void withdraw(Customer customer, BigDecimal amount) {
        Transaction transaction = new Transaction(Transaction.Type.WITHDRAW, amount, customer);
        transaction.execute();
        getTransactions().add(transaction);
        customer.registerTransaction(transaction);
    }

    public void transfer(Customer customer1, BigDecimal amount, Customer customer2) {
        Transaction transaction = new Transaction(Transaction.Type.TRANSFER, amount, customer1, customer2);
        transaction.execute();
        getTransactions().add(transaction);
        customer1.registerTransaction(transaction);
        customer2.registerTransaction(transaction);
    }

    /**
     * Performs a customer login operation.
     *
     * @param phoneNumber the phone number of the customer
     * @param code        the login code of the customer
     * @return the authenticated customer if login is successful, otherwise null
     */

    /**
     * Gets the serial number of the money transfer service.
     *
     * @return the serial number of the money transfer service
     */
    public int getSerialNumber() {
        return serialNumber;
    }


    /**
     * Gets the list of transactions associated with the money transfer service.
     *
     * @return the list of transactions
     */
    public List<Transaction> getTransactions() {
        return new ArrayList<>(transactions);
    }
}
