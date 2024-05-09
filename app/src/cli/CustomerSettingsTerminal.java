package AvaBank.cli;

import AvaBank.core.Customer;
import AvaBank.database.CustomerDatabase;
import AvaBank.login.CustomerAuthenticationManager;

import java.util.Scanner;

public class CustomerSettingsTerminal {
    public void createBankAccount() {
        Scanner sc = new Scanner(System.in);
        CustomerDatabase dtb = new CustomerDatabase();
        System.out.println("Phone number: ");
        String phoneNumber = sc.nextLine();
        Customer customer = new Customer(null, null, null, null, phoneNumber, null);
        int x;
        if((x = dtb.contains(customer)) != -1){
            customer = dtb.getCustomer(x);
            try {
                customer.registerBankAccount();
            }
            catch (IllegalArgumentException e){
                System.out.println(e);
            }
        }
        else {
            System.out.println("Account not found");
        }
    }
    public void closeBankAccount(){
        Scanner sc = new Scanner(System.in);
        CustomerDatabase dtb = new CustomerDatabase();
        System.out.println("Phone number: ");
        String phoneNumber = sc.nextLine();
        Customer customer = new Customer(null, null, null, null, phoneNumber, null);
        int x;
        if((x = dtb.contains(customer)) != -1){
            CustomerAuthenticationManager authenticationManager = new CustomerAuthenticationManager();
            if(authenticationManager.isUserRegistered(phoneNumber)){
                System.out.println("Bank account password: ");
                String password = sc.nextLine();
                if(authenticationManager.authenticateUser(phoneNumber, password)){
                    customer = dtb.getCustomer(x);
                    customer.closeBankAccount();
                }
            }
            else {
                System.out.println("You do not have bank account registered");
            }
        }
        else {
            System.out.println("Person not found");
        }
    }
    public void printTransactions() {
        Scanner sc = new Scanner(System.in);
        CustomerDatabase dtb = new CustomerDatabase();
        System.out.println("Phone number: ");
        String phoneNumber = sc.nextLine();
        Customer customer = new Customer(null, null, null, null, phoneNumber, null);
        int x;
        if((x = dtb.contains(customer)) != -1){
            CustomerAuthenticationManager authenticationManager = new CustomerAuthenticationManager();
            if(authenticationManager.isUserRegistered(phoneNumber)){
                System.out.println("Bank account password: ");
                String password = sc.nextLine();
                if(authenticationManager.authenticateUser(phoneNumber, password)){
                    customer = dtb.getCustomer(x);
                    customer.closeBankAccount();
                }
            }
            else {
                System.out.println("You do not have bank account registered");
            }
        }
        else {
            System.out.println("Person not found");
        }
    }
}
