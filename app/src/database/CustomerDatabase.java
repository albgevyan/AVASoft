package AvaBank.database;

import AvaBank.core.Customer;
import AvaBank.core.Transaction;
import AvaBank.core.User;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class CustomerDatabase {
    private static final String PATH = "/files.CustomerDatabase.txt";
    private ArrayList<Customer> customers;
    public CustomerDatabase(){
        load();
    }

    private void load(){
        try {
            Scanner sc = new Scanner(new FileInputStream(PATH));
            int count = sc.nextInt();
            customers = new ArrayList<Customer>(count);
            sc.nextLine();
            for (int i = 0; i < count; i++) {
                String parts[] = sc.nextLine().split(",");
                String parts1[] = sc.nextLine().split(" ");
                BigDecimal decimal = new BigDecimal(parts1[0]);
                Transaction transaction = new Transaction(parts1[1]);
                Customer current = new Customer(parts, );
                if (contains(current) == -1)
                    customers.add(current);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("Cannot open the database file.");
            System.exit(0);
        } catch (Exception e) {
            System.out.println(e);
            System.exit(0);
        }
    }

    public void save() {
        try {
            PrintWriter pw = new PrintWriter(PATH);
            pw.println(customers.size());
            for (int i = 0; i < customers.size(); i++)
                pw.println(customers.get(i));
            pw.close();
        } catch (FileNotFoundException e) {
            System.out.println("Cannot save into the database file.");
            System.exit(0);
        }
    }

    public void addCustomer(Customer customer) {
        if(contains(customer) == -1){
            customers.add(customer);
            save();
        }
    }
    public int getSize() {
        return customers.size();
    }
    public Customer getCustomer(int i) {
        return customers.get(i);
    }
    public int contains(Customer c) {
        for (int i = 0; i < customers.size(); i++)
            if (customers.get(i).equals(c))
                return i;
        return -1;
    }
}
