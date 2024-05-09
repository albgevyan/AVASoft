package AvaBank.database;

import AvaBank.core.Customer;
import AvaBank.core.Transaction;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;

public class TransactionDatabase {
    private static final String PATH = "files/TransactionDatabase.txt";
    private ArrayList<Transaction> transactions;

    public TransactionDatabase() {
        transactions = new ArrayList<>();
        load();
    }

    private void load() {
        try (Scanner scanner = new Scanner(new File(PATH))) {
            int count = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            for (int i = 0; i < count; i++) {
                String line = scanner.nextLine();
                String[] parts = line.split(";");
                // Parse transaction details
                if(parts.length == 6){
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
        } catch (FileNotFoundException e) {
            System.out.println("Cannot open the database file.");
            System.exit(0);
        } catch (Exception e) {
            System.out.println("Error loading transactions: " + e.getMessage());
            System.exit(0);
        }
    }

    public void save() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(PATH))) {
            pw.println(transactions.size());
            for (Transaction transaction : transactions) {
                pw.println(transaction.toString());
            }
        } catch (IOException e) {
            System.out.println("Error saving transactions: " + e.getMessage());
            System.exit(0);
        }
    }

    public void addTransaction(Transaction transaction) {
        if(contains(transaction) == -1){
            transactions.add(transaction);
            save();
        }
    }

    public int getSize() {
        return transactions.size();
    }
    public Transaction getCustomer(int i) {
        return transactions.get(i);
    }
    public int contains(Transaction transaction) {
        for (int i = 0; i < transactions.size(); i++)
            if (transactions.get(i).equals(transaction))
                return i;
        return -1;
    }
}
