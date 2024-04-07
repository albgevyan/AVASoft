package AvaBank.UserManagment;

import AvaBank.cli.MoneyTransferService;

// represents Transaction between two customers or just balance changes of the customer's account
public class Transaction {
    public enum Type{
        DEPOSIT, WITHDRAW, TRANSFER
    }
    private Type type;
    private double amount;
    private Customer customer1;
    private Customer customer2;
    private MoneyTransferService moneyTransferService;
    public Transaction(Type type, double amount, Customer customer1, Customer customer2, MoneyTransferService service) {
        this.type = type;
        this.amount = amount;
        this.customer1 = customer1;
        this.customer2 = customer2;
        this.moneyTransferService = service;
        if(type == Type.DEPOSIT){
            deposit(amount, customer1);
        }
        else if(type == Type.WITHDRAW){
            withdraw(amount, customer1);
        }
        else {
            if(customer2 == null){
                System.out.println("Customer2 can not be null (BalanceManipulator)");
                System.exit(0);
            }
            withdraw(amount, customer1);
            deposit(amount, customer2);
        }
    }

    private static void deposit(double amount, Customer actor){
        actor.setBalance(actor.getBalance() + amount);
    }

    private static void withdraw(double amount, Customer actor){
        actor.setBalance(actor.getBalance() - amount);
    }

    public Customer getCustomer1() {
        return new Customer(customer1);
    }

    public Customer getCustomer2() {
        return new Customer(customer2);
    }

    public double getAmount() {
        return amount;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "type='" + type + '\'' +
                ", amount=" + amount +
                ", sender=" + customer1 +
                ", receiver=" + customer2 +
                ", through " + moneyTransferService.getClass() +
                ", serialNumber=" + moneyTransferService.getSerialNumber() +
                '}';
    }
}