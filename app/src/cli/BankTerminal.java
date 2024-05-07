package AvaBank.cli;

import AvaBank.core.Customer;
import AvaBank.core.Transaction;
import AvaBank.login.CustomerAuthenticationManager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BankTerminal extends MoneyTransferService {
    boolean logined;


    public BankTerminal(int serialNumber){
        super(serialNumber);
        logined = false;
    }

    public void start() {
        CustomerAuthenticationManager authenticationManager = new CustomerAuthenticationManager();
        System.out.println("Welcome to Bank Terminal");
        System.out.println("Before doing operations, you must login into your account");
        System.out.print("Please write your phone number: ");
        Scanner sc = new Scanner(System.in);
        String phoneNumber = sc.nextLine();
        if(authenticationManager.isUserRegistered(phoneNumber)) {

            System.out.println("Welcome " + authenticationManager.getUserFullName(phoneNumber));
            String code;
            do{
                System.out.println("Enter your bank account code");
                code = sc.nextLine();
                if(authenticationManager.authenticateUser(phoneNumber, code)){
                    logined = true;
                }
            } while (authenticationManager.authenticateUser(phoneNumber, code));
            if(logined){
                //Customer customer = new Customer(); DATABASE. It must identify user by phoneNumber and create a new object
                System.out.println("Type 1 if you want to deposit");
                System.out.println("Type 2 if you want to withdraw");
                System.out.println("Type 3 if you want to transfer to another person");
                System.out.println("Type any other number if you want to log out");
                int x = sc.nextInt();
                switch (x) {
                    case 1:
                        //deposit(customer, amount);
                        logined = false;
                        break;
                    case 2:
                        // Withdraw logic
                        logined = false;
                        break;
                    case 3:
                        // Transfer logic
                        logined = false;
                        break;
                    default:
                        System.out.println("Logging out...");
                        logined = false;
                        break;
                }
            }
        }
        else {
            System.out.println("Wrong phone number");
        }
    }
}
