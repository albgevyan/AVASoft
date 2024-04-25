package admin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import core.DBUser;
import core.DBUserAdapter;
import exceptions.InvalidInputFormatException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

// CHECK EXCEPTIONS
// YOU SHOULD REMOVE THEM
// maybe we can make this calls an inner class

final class Initializer {
    private final File ADMIN_PATH = new File("C:/Users/alber/Documents/GitHub/AVASoft/database/SCHEMAS/USERS/ADMIN.json");
    private BufferedWriter bufferedWriter;
    private GsonBuilder builder;
    private DBUser ADMIN;
    private Gson save;

    public Initializer() throws IOException {
        if (!ADMIN_PATH.exists()){
            Scanner input = new Scanner(System.in);
            this.bufferedWriter = new BufferedWriter(new FileWriter(ADMIN_PATH));
            this.builder = new GsonBuilder();
            this.builder.registerTypeHierarchyAdapter(DBUser.class, new DBUserAdapter());
            this.save = this.builder.create();

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

            this.insertUser();
        }
    }

    private void insertUser() throws IOException{
        String json = this.save.toJson(this.ADMIN);
        this.bufferedWriter.write(json);
        this.bufferedWriter.close();
    }
}
