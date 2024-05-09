package AvaBank.core;
import AvaBank.database.CustomerDatabase;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.math.BigDecimal;

/**
 * The Customer class represents a bank customer with various functionalities related to banking operations.
 * This class extends the User class and implements the TransactionOperations interface.
 */

public class Customer extends User implements TransactionOperations {

    /**
     * Inner class representing a bank account for the customer.
     * Each customer can have one or more bank accounts.
     */

    private class BankAccount implements Cloneable, TransactionOperations {

        /**
         * Inner class representing the balance of the bank account.
         */

        private class Balance {
            private BigDecimal balance;

            /**
             * Constructs a Balance object with an initial balance of zero.
             */

            public Balance() {
                this.balance = BigDecimal.valueOf(0);
            }
            public Balance(BigDecimal balance) {
                this.balance = balance;
            }

            /**
             * Adds the specified amount to the balance.
             *
             * @param amount the amount to be added to the balance
             * @throws IllegalArgumentException if the amount is negative
             */

            public void addAmount(BigDecimal amount) {
                if(amount.compareTo(new BigDecimal("0")) > 0){
                    setBalance(balance.add(amount));
                }
                else {
                    throw new IllegalArgumentException();
                }
            }



            /**
             * Subtracts the specified amount from the balance.
             *
             * @param amount the amount to be subtracted from the balance
             * @throws IllegalArgumentException if the amount is negative
             */
            public void subtractAmount(BigDecimal amount) {
                if(amount.compareTo(new BigDecimal("0")) >= 0){
                    setBalance(balance.subtract(amount));
                }
                else {
                    throw new IllegalArgumentException();
                }
            }

            /**
             * Returns the current balance.
             *
             * @return the current balance
             */

            public BigDecimal getBalance() {
                return balance;
            }

            /**
             * Sets the balance to the specified value.
             *
             * @param balance the new balance value
             * @throws IllegalArgumentException if the specified balance is negative
             */

            public void setBalance(BigDecimal balance) {
                if (balance.compareTo(new BigDecimal("0")) > 0) {
                    this.balance = balance;
                } else {
                    throw new IllegalArgumentException("Balance cannot be negative");
                }
            }

            /**
             * Returns a string representation of the balance.
             *
             * @return a string representation of the balance
             */

            @Override
            public String toString() {
                return balance.toString();
            }
        }

        private Balance balance;
        private String password;

        /**
         * Constructs a BankAccount object with the specified password.
         *
         * @param password the code of the bank account
         * @throws IllegalArgumentException if the length of the code is not between 5 and 20 characters
         */

        public BankAccount(String password) {
            balance = new Balance();
            if(password.length() < 5 || password.length() > 20){
                throw new IllegalArgumentException("code length should be between 5 and 20");
            }
            this.password = password;
        }
        public BankAccount(BigDecimal number, String password){
            this(password);
            this.balance = new Balance(number);
        }
        @Override
        public void depositAmount(BigDecimal amount) {
            balance.addAmount(amount);
        }

        @Override
        public void withdrawAmount(BigDecimal amount) {
            balance.subtractAmount(amount);
        }

        public Balance getBalance() {
            return balance;
        }

        public void closeAccount() {
            balance.setBalance(BigDecimal.valueOf(0));
            // we do that if we suspect that account is hacked
        }

        @Override
        public String toString() {
            return getBalance() + "," + password;
        }
    }

    private BankAccount bankAccount;
    private List<Transaction> transactions;


    public Customer(String fullName, Gender gender, String userId, String email, String phoneNumber, String address) {
        super(fullName, gender, userId, email, phoneNumber, address);
        this.transactions = new ArrayList<>();
    }
    public Customer(String fullName, Gender gender, String userId, String email, String phoneNumber, String address, BankAccount bankAccount, List<Transaction> transactions) {
        super(fullName, gender, userId, email, phoneNumber, address);
        this.bankAccount = bankAccount;
        this.transactions = transactions;

    }
    public Customer(String[] args, BankAccount bankAccount, List<Transaction> transactions) {
        this(args[0], Gender.valueOf(args[1]), args[2], args[3], args[4], args[5]);
    }
    public void registerBankAccount(){
        System.out.println("Write bank account password(the length should be no less than 5 and no more than 20): ");
        Scanner sc = new Scanner(System.in);
        bankAccount = new BankAccount(sc.nextLine());
    }
    public void registerBankAccount(String password){
        bankAccount = new BankAccount(password);
    }
    @Override
    public void depositAmount(BigDecimal amount) {
        bankAccount.depositAmount(amount);
    }

    @Override
    public void withdrawAmount(BigDecimal amount) {
        bankAccount.withdrawAmount(amount);
    }

    public void transferMoneyToAnotherPerson(BigDecimal amount, Customer receiver) {
        this.bankAccount.withdrawAmount(amount);
        receiver.depositAmount(amount);
    }

    public void printBalance() {
        System.out.println(bankAccount.getBalance());
    }
    public void closeBankAccount() {
        bankAccount.closeAccount();
        bankAccount = null;
    }

    public void registerTransaction(Transaction transaction){
        transactions.add(transaction);
    }
    public List<Transaction> getTransactions() {
        return new ArrayList<>(transactions);
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public String getBankAccountPassword(){
        return bankAccount.password;
    }

    @Override
    public int hashCode() {
        return getPhoneNumber().hashCode();
    }

    @Override
    public String toString() {
        return super.toString() + "\n" + bankAccount + " " + transactions;
    }
}
/*this(args[0], Gender.valueOf(args[1]), args[2], args[3], args[4], args[5]);
        if(args.length == 8) {
            this.bankAccount = new BankAccount(new BigDecimal(args[6]), args[7]);
            this.transactions = new ArrayList<>();
            for (int i = 0; i < args[8].length(); i++) {
                String[] parts = args[8].split(";");
                // Parse transaction details
                if (parts.length == 6) {
                    Transaction transaction = new Transaction(
                            Transaction.Type.valueOf(parts[1]),
                            new BigDecimal(parts[2]),
                            new Customer(parts[3].split(","))
                    );
                    transactions.add(transaction);
                }
                else {
                    Transaction transaction = new Transaction(
                            Transaction.Type.valueOf(parts[1]),
                            new BigDecimal(parts[2]),
                            new Customer(parts[3].split(",")),
                            new Customer(parts[4].split(","))
                    );
                    transactions.add(transaction);
                }
            }
        }

         */