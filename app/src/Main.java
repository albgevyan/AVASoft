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
        customer.registerBankAccount("Ashotik1223");
        System.out.println(customer.getBankAccount());
        System.out.println(customer);
    }
}