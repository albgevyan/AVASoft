package AvaBank.core;
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
                return "Balance: " + balance;
            }
        }

        private Balance balance;
        private String code;

        /**
         * Constructs a BankAccount object with the specified code.
         *
         * @param code the code of the bank account
         * @throws IllegalArgumentException if the length of the code is not between 5 and 20 characters
         */

        public BankAccount(String code) {
            balance = new Balance();
            if(code.length() < 5 || code.length() > 20){
                throw new IllegalArgumentException("code length should be between 5 and 20");
            }
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
            // save account data in one file
            // we do that if we suspect that account is hacked
        }

        @Override
        protected Object clone() {
            try {
                BankAccount clonedAccount = (BankAccount) super.clone();
                return clonedAccount;
            } catch (CloneNotSupportedException e) {
                System.err.println("Clone not supported: " + e.getMessage());
                return null;
            }
        }
    }

    private BankAccount bankAccount; // maybe later create list of bank accounts to have more than 1 bank account
    private List<Transaction> transactions;

    /**
     * Constructs a Customer object with the specified details.
     *
     * @param fullName the full name of the customer
     * @param gender the gender of the customer
     * @param userId the user ID of the customer
     * @param email the email address of the customer
     * @param phoneNumber the phone number of the customer
     * @param address the address of the customer
     */

    public Customer(String fullName, Gender gender, String userId, String email, String phoneNumber, String address) {
        super(fullName, gender, userId, email, phoneNumber, address);
        this.transactions = new ArrayList<>();
        registerBankAccount();
    }

    public void registerBankAccount(){
        System.out.println("Select bank account code");
        Scanner sc = new Scanner(System.in);
        bankAccount = new BankAccount(sc.nextLine());
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


    public BankAccount getBankAccount() {
        return (BankAccount) bankAccount.clone();
    }

    public void printBalance() {
        System.out.println(bankAccount.getBalance());
    }
    public void closeBankAccount() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("account_data.txt"));
            // Save account data

        } catch (IOException e) {
            System.err.println("Error writing account data: " + e.getMessage());
        }
        bankAccount.closeAccount();
        bankAccount = null;
    }

    public void registerTransaction(Transaction transaction){
        transactions.add(transaction);
    }
    public List<Transaction> getTransactions() {
        return new ArrayList<>(transactions);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
