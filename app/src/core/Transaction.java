package AvaBank.core;

import AvaBank.cli.MoneyTransferService;

// represents Transaction between two customers or just balance changes of the customer's account
public class Transaction {

    public enum Type {
        DEPOSIT, WITHDRAW, TRANSFER
    }

    private Type type;
    private double amount;
    private Customer customer1;
    private Customer customer2;
    private MoneyTransferService moneyTransferService;

    public Transaction(Type type, double amount, Customer customer, MoneyTransferService service){
        this.type = type;
        this.amount = amount;
        this.customer1 = customer;
        this.moneyTransferService = service;
    }

    public Transaction(Type type, double amount, Customer customer1, Customer customer2, MoneyTransferService service) {
        this(type, amount, customer1, service);
        this.customer2 = customer2;
    }

    public void execute() {
        switch (type) {
            case DEPOSIT:
                System.out.println("deposited" + amount + " of money");
                customer1.depositAmount(amount);
                break;
            case WITHDRAW:
                System.out.println("withdrew" + amount + " of money");
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


    public double getAmount() {
        return amount;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        if(customer2 != null)
            return String.format("Transaction{type='%s', amount=%s, sender=%s, receiver=%s, through %s, serialNumber=%s}",
                    type, amount, customer1, customer2, moneyTransferService.getClass(), moneyTransferService.getSerialNumber());

        return String.format("Transaction{type='%s', amount=%s, user=%s, through %s, serialNumber=%s}",
                type, amount, customer1, moneyTransferService.getClass(), moneyTransferService.getSerialNumber());
    }
}
