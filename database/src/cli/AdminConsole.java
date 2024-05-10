package cli;

import core.DBUser;

import core.Initializer;
import exceptions.IdentificationFailed;
import exceptions.InvalidInputFormatException;
import exceptions.UnprivilegedActionException;
import wrapper.Connection;

import java.io.IOException;
import java.util.Scanner;

final public class AdminConsole {

    static private Connection connection;

    public static void main(String[] args) throws IOException {
        Initializer init = new Initializer();
        System.out.println("Initialisation successfully completed.");
        startConsole();
    }

    private static void startConsole() throws IOException {

        Scanner input = new Scanner(System.in);

        login(input);

        while (true) {
            System.out.println("""
                    Choose the action you want to perform
                    [1] Create user
                    [2] Create table
                    [3] Delete user
                    [4] Delete table
                    [5] Close console
                    """);

            try{
                switch (input.nextInt()) {
                    case 1:
                        createUser(input);
                        break;
                    case 2:
                        createTable(input);
                        break;
                    case 3:
                        deleteUser(input);
                        break;
                    case 4:
                        deleteTable(input);
                        break;
                    case 5:
                        System.exit(1);
                    default:
                        System.out.println("Choose your action(e.g. enter 1 for \"Create User\").");
                        break;
                }
            }
            catch (UnprivilegedActionException e){
                System.out.println("The user you are signed in does not have the privilege to execute your request.");
            }
        }
    }

    private static void login(Scanner input){
        while (true) {
            System.out.println("Enter the username.");
            String username = input.nextLine();
            System.out.println("Enter the password.");
            String password = input.nextLine();

            try {
                connection = Connection.getConnection(username, password);
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

    private static void createUser(Scanner input) throws UnprivilegedActionException, IOException {
        while (true) {

            System.out.println("Enter the username of the new user.");
                 String newUsername = input.next();

            System.out.println("Enter the password of the new user.");
            String newPassword = input.next();

            System.out.println("""
                    Choose the privileges of the new user
                    Separate the answers by commas
                    [1] CREATE
                    [2] INSERT
                    [3] RETRIEVE
                    [4] ALTER
                    [5] DELETE
                    """);
            String privilegesString = input.next();
            DBUser.Privilege[] privileges = DBUser.privilegesFromString(privilegesString);

            try {
                Connection.createUser(newUsername, newPassword, privileges);
                System.out.println("User " + newUsername + " successfully created.");
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

    private static void createTable(Scanner input) throws UnprivilegedActionException, IOException{
        while (true){
            System.out.println("Enter the name of the table you want to create.");
            String tableName = input.nextLine();

            try{
                Connection.createTable(tableName, Employee.class);
                System.out.println("Table " + tableName + " successfully created.");
                break;
            }
            catch (InvalidInputFormatException e){
                System.out.println(e.getMessage());
            }
            catch (IOException e){
                System.out.println("Warning the database file system may be corrupt. " +
                        "Unable to execute your request.");
                System.out.println(e.getMessage());
            }
        }
    }

    private static void deleteUser(Scanner input) throws UnprivilegedActionException{
        while (true) {
            System.out.println("Enter the username of the user you want to delete.");
            String username = input.next();

            try{
                Connection.deleteUser(username);
                break;
            }
            catch (IOException e){
                System.out.println("Warning. The database file system may be corrupt." +
                        "Unable to delete " + username);
            }
        }
    }

    private static void deleteTable(Scanner input) throws UnprivilegedActionException{
        while (true) {
            System.out.println("Enter the name of the table you want to delete.");
            String tableName = input.next();

            try{
                Connection.deleteTable(tableName);
                break;
            }
            catch (InvalidInputFormatException e){
                System.out.println("The entered table does not exist " +
                        "Failed to execute your request.");
            }
        }
    }
}