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

            System.out.println(SCHEMAS.DatabaseDirectory.getDatabaseDirectory() + "/USERS/" + username + ".json");
            FileReader fileReader = new FileReader(SCHEMAS.DatabaseDirectory.getDatabaseDirectory() + "/USERS/" + username + ".json");
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeHierarchyAdapter(DBUser.class, new DBUserAdapter());
            Gson gson = builder.create();
            DBUser user = gson.fromJson(fileReader, DBUser.class);

            if (user.identifyUser(username, password))
                return user;
            fileReader.close();

        }
        catch (IOException e){
            System.out.println("Warning. The specified user was not found in the database. The database file system may be corrupt.");
            System.out.println(e.getMessage());
        }

        return null;
    }
}
