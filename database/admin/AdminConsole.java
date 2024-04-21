package admin;

import core.DBUser;
import exceptions.IdentificationFailed;
import exceptions.InvalidInputFormatException;
import exceptions.UnprivilegedActionException;
import wrapper.Connection;

import java.io.IOException;
import java.util.Scanner;

final public class AdminConsole {
    static private Connection connection;

    public static void main(String[] args) throws IOException{
        Initializer init = new Initializer();
        System.out.println("Initialisation successfully completed.");
        startConsole();

    }

    private static void startConsole() throws IOException{
        Scanner input = new Scanner(System.in);
        while (true){
            while (true) {
                System.out.println("Enter the username.");
                String username = input.nextLine();
                System.out.println("Enter the password.");
                String password = input.nextLine();

                try {
                    connection = Connection.getConnection(username, password);
                } catch (IdentificationFailed e) {
                    System.out.println(e.getMessage());
                }
                break;
            }

            try {
                createUser(input);
            } catch (UnprivilegedActionException e){
                System.out.println("The user you are signed in does not have the privilege to execute your request.\n" +
                                   "Login as root user ADMIN.");
            }
            break;
        }
    }

    private static void createUser(Scanner input) throws UnprivilegedActionException, IOException {
        while (true) {

            System.out.println("Enter the username of the new user.");
            String newUsername = input.nextLine();

            System.out.println("Enter the password of the new user.");
            String newPassword = input.nextLine();

            System.out.println("""
                    Choose the privileges of the new user
                    Separate the answers by commas
                    [1] CREATE
                    [2] INSERT
                    [3] RETRIEVE
                    [4] ALTER
                    """);
            String privilegesString = input.nextLine();
            DBUser.Privilege[] privileges = DBUser.privilegesFromString(privilegesString);

            try {
                Connection.USER.createUser(newUsername, newPassword, privileges);
                System.out.println("User " + newUsername + " successfully created.");
                break;
            } catch (InvalidInputFormatException e){
                System.out.println(e.getMessage());
            }
        }
    }

//    private static void retrieveData(Scanner input){
//        while (true){
//            System.out.println("Enter the name of the table.");
//            String tableName = input.nextLine();
//            System.out.println("");
//        }
//    }
}
