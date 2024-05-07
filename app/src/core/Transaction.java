package AvaBank.core;

import AvaBank.cli.MoneyTransferService;

import java.math.BigDecimal;

/**
 * The Transaction class represents a financial transaction between customers or balance changes of a customer's account.
 * It includes operations like deposit, withdraw, and transfer.
 */
public class Transaction {

    /**
     * Enum representing the type of transaction.
     */
    public enum Type {
        DEPOSIT, WITHDRAW, TRANSFER
    }

    private Type type;
    private BigDecimal amount;
    private Customer customer1;
    private Customer customer2;
    private MoneyTransferService moneyTransferService;

    /**
     * Constructs a Transaction object with the specified type, amount, customer, and money transfer service.
     *
     * @param type               the type of transaction
     * @param amount             the amount involved in the transaction
     * @param customer           the customer involved in the transaction
     * @param service            the money transfer service used for the transaction
     */
    public Transaction(Type type, BigDecimal amount, Customer customer, MoneyTransferService service){
        this.type = type;
        this.amount = amount;
        this.customer1 = customer;
        this.moneyTransferService = service;
    }

    /**
     * Constructs a Transaction object representing a transfer between two customers.
     *
     * @param type               the type of transaction
     * @param amount             the amount involved in the transaction
     * @param customer1          the sender of the transaction
     * @param customer2          the receiver of the transaction
     * @param service            the money transfer service used for the transaction
     */
    public Transaction(Type type, BigDecimal amount, Customer customer1, Customer customer2, MoneyTransferService service) {
        this(type, amount, customer1, service);
        this.customer2 = customer2;
    }

    /**
     * Executes the transaction based on its type.
     */
    public void execute() {
        switch (type) {
            case DEPOSIT:
                System.out.println("Deposited " + amount + " of money");
                customer1.depositAmount(amount);
                break;
            case WITHDRAW:
                System.out.println("Withdrew " + amount + " of money");
                customer1.withdrawAmount(amount);
                break;
            case TRANSFER:
                if (customer2 == null) {
                    throw new NullPointerException("Receiver cannot be null for transfer");
                }
                try {
                    customer1.withdrawAmount(amount);
                    customer2.depositAmount(amount);
                }
                catch (Exception e){
                    System.out.println(e);
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid transaction type");
        }
    }

    /**
     * Gets the amount involved in the transaction.
     *
     * @return the amount of the transaction
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Gets the type of the transaction.
     *
     * @return the type of the transaction
     */
    public Type getType() {
        return type;
    }

    /**
     * Returns a string representation of the transaction.
     *
     * @return a string representation of the transaction
     */
    @Override
    public String toString() {
        if(customer2 != null)
            return String.format("Transaction{type='%s', amount=%s, sender=%s, receiver=%s, through %s, serialNumber=%s}",
                    type, amount, customer1, customer2, moneyTransferService.getClass(), moneyTransferService.getSerialNumber());

        return String.format("Transaction{type='%s', amount=%s, user=%s, through %s, serialNumber=%s}",
                type, amount, customer1, moneyTransferService.getClass(), moneyTransferService.getSerialNumber());
    }
}
