package AvaBank.UserManagment;

public class Transaction {
    private String type;
    private double amount;
    private Customer sender;
    private Customer receiver;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    public Transaction(String type, double amount, Customer sender, Customer receiver) {
        this.type = type;
        this.amount = amount;
        this.sender = sender;
        this.receiver = receiver;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "type='" + type + '\'' +
                ", amount=" + amount +
                ", sender=" + sender +
                ", receiver=" + receiver +
                '}';
    }
}