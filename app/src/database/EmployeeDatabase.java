package AvaBank.database;

import AvaBank.core.Customer;
import AvaBank.core.Employee;
import AvaBank.core.User;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;

public class EmployeeDatabase {
    private static final String PATH = "/files.EmployeeDatabase.txt";
    private ArrayList<Employee> employees;
    public EmployeeDatabase(){
        load();
    }

    private void load(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(PATH));
            String line;
            while((line = reader.readLine()) != null){
                String str[] = line.split(",");
                employees.add(new Employee(str[0], User.Gender.valueOf(str[1]), str[2], str[3], str[4], str[5], str[6]));
            }
        }
        catch (FileNotFoundException e){
            System.out.println("Could not find the file");
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public void save() {
        try {
            PrintWriter pw = new PrintWriter(PATH);
            pw.println(employees.size());
            for (int i = 0; i < employees.size(); i++)
                pw.println(employees.get(i));
            pw.close();
        } catch (FileNotFoundException e) {
            System.out.println("Cannot save into the database file.");
            System.exit(0);
        }
    }

    public void addCustomer(Employee employee) {
        for(int i = 0; i < employees.size(); i++){
            if(!contains(employee)){
                employees.add(employee);
                save();
            }
            else
                System.out.println("Such employee already exists");
        }
    }
    public int getSize() {
        return employees.size();
    }

    private boolean contains(Employee e) {
        for (int i = 0; i < employees.size(); i++)
            if (employees.get(i).equals(e))
                return true;
        return false;
    }


}
