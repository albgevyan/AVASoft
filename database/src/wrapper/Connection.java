package wrapper;

import core.DBUser;
import exceptions.IdentificationFailed;
import exceptions.InvalidInputFormatException;
import exceptions.UnprivilegedActionException;

import java.io.IOException;

public class Connection{
    private static final Connection connection = new Connection();
    private static DBUser USER;

    private Connection(){}

    public static Connection getConnection(String username, String password) throws IdentificationFailed {
        try {
            USER = DBUser.login(username, password);
        }
        catch (IOException e){
            System.out.println("Warning. The specified user was not found in the database. The database file system may be corrupt.");
            System.out.println(e.getMessage());
            return null;
        }
        return connection;
    }

    public static void createTable(String tableName) throws UnprivilegedActionException, IOException, InvalidInputFormatException {
        USER.createTable(tableName);
    }

    public static void createUser(String username, String password, DBUser.Privilege[] privileges) throws UnprivilegedActionException, IOException, InvalidInputFormatException {
        USER.createUser(username, password, privileges);
    }

    public static void deleteUser(String username) throws UnprivilegedActionException, IOException{
        USER.deleteUser(username);
    }

    public static void deleteTable(String tableName) throws UnprivilegedActionException, InvalidInputFormatException{
        USER.deleteTable(tableName);
    }
}
