package core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exceptions.IdentificationFailed;

import java.io.FileReader;
import java.io.IOException;

/**
 * @author Albert Gevorgyan
 */
public class Authenticator {

    public static DBUser identification(String username, String password) throws IdentificationFailed{
        try {

            FileReader fileReader = new FileReader("C:/Users/alber/Documents/GitHub/AVASoft/database/SCHEMAS/USERS/" + username + ".json");
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeHierarchyAdapter(DBUser.class, new DBUserAdapter());
            Gson gson = builder.create();
            DBUser user = gson.fromJson(fileReader, DBUser.class);

            try {
                if (user.identifyUser(username, password))
                    return user;
            } catch (IdentificationFailed e){
                System.out.println(e.getMessage());
            }
            fileReader.close();

        } catch (IOException e){
            System.out.println("Warning. The specified user was not found in the database. Either the username is incorrect or the database is corrupt.");
            System.out.println(e.getMessage());
        }

        return null;
    }

//    public static User userIdentification(String username, String password){return User;}
}
