package AvaBank;

import AvaBank.cli.BankTerminal;
import AvaBank.core.Customer;
import AvaBank.core.Transaction;
import AvaBank.core.User;

import java.math.BigDecimal;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Customer customer = new Customer("Ashot", User.Gender.MALE, "MK21312", "dsads@mail.ru", "+3749830077", "dsadsa");

        BankTerminal terminal = new BankTerminal(32233);


        List<Transaction> transactions = customer.getTransactions();

        for (int i = 0; i < transactions.size(); i++){
            System.out.println(transactions.get(i));
        }
        customer.printBalance();
    }
}