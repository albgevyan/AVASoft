package core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exceptions.InvalidInputFormatException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

// CHECK EXCEPTIONS
// YOU SHOULD REMOVE THEM
// maybe we can make this calls an inner class

final public class Initializer {
    private final Path ADMIN_PATH = SCHEMAS.DatabaseDirectory.getDatabasePath();
    private BufferedWriter bufferedWriter;
    private GsonBuilder builder;
    private DBUser ADMIN;
    private Gson save;

    public Initializer() throws IOException {

        if (!Files.exists(ADMIN_PATH.resolve("USERS").resolve("ADMIN.json"))){
            Scanner input = new Scanner(System.in);

            while (true){
                System.out.println("Enter a password for the root user of the database.");
                String password = input.nextLine();
                try{
                    this.ADMIN = new DBUser("ADMIN", password, core.DBUser.Privilege.GRANT);
                    break;
                } catch (InvalidInputFormatException e){
                    System.out.println(e.getMessage());
                }
            }

            this.ADMIN.logout();
        }
    }
}
