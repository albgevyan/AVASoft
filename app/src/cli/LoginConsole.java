package cli;

import core.Employee;
import core.User;
import exceptions.IdentificationFailed;
import exceptions.InvalidInputFormatException;
import wrapper.Connection;

import java.io.IOException;
import java.util.Date;
import java.util.Scanner;

public class LoginConsole {
    private static final String DB_CLIENT_USERNAME = "LOGIN_CLIENT";
    private static final String DB_CLIENT_PASSWORD = "GtY##183927";
    static private Connection connection;

    public LoginConsole() throws IdentificationFailed {

        Scanner input = new Scanner(System.in);
        login(input);

        try {
            this.connection = Connection.getConnection(DB_CLIENT_USERNAME, DB_CLIENT_PASSWORD);
        }
        catch (IdentificationFailed e){
            System.out.println("Unable to connect to database");
            System.out.println(e.getMessage());
        }
    }

    private static void login(Scanner input){
        while (true) {
            System.out.println("Enter the username.");
            String username = input.nextLine();
            System.out.println("Enter the password.");
            String password = input.nextLine();

            try {
                connection = Connection.getConnection(DB_CLIENT_USERNAME, DB_CLIENT_PASSWORD);
            }
            catch (IdentificationFailed e) {
                System.out.println(e.getMessage());
            }

            if (connection != null) {
                System.out.println("Successfully logged in.");
                break;
            }
        }

    }

    private void createUser(Scanner input){
        while (true) {
            System.out.println("Enter you Social Service Number.");
            int SSN = input.nextInt();

            System.out.println("Enter your name and surname.");
            String name = input.next();
            String surname = input.next();

            System.out.println("Enter your email.");
            String email = input.next();

            System.out.println("Enter the date of your birth(e.g. May 15 2013).");
            Date birthDate = new Date(input.next());

            System.out.println("Are you male or female");
            User.Gender gender = User.genderFromString(input.next());

            System.out.println("Enter the amount of your salary.");
            int salary = input.nextInt();

            System.out.println("Enter the department you are working.");
            Employee.Department department = Employee.departmentFromString(input.next());

            System.out.println("Enter the password of the new user.");
            String password = input.next();

            try {
                Employee employee = new Employee(SSN, name, surname, email, birthDate, gender, salary, department, password);
                employee.logout();
                System.out.println("User for " + name + " " + surname + " successfully created.");
                break;
            }
            catch (InvalidInputFormatException e){
                System.out.println("Crap");
                System.out.println(e.getMessage());
            }
            catch (IOException e){
                System.out.println(e.getMessage());
                System.out.println("Warning the database file system may be corrupt." +
                        "Unable to execute your request.");
            }
        }
    }
}
