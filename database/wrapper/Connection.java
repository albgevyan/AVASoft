package wrapper;

import core.Authenticator;
import core.DBUser;
import exceptions.IdentificationFailed;

public class Connection{
    private static Connection connection = new Connection();
    public static DBUser USER;

    private Connection(){}

    public static Connection getConnection(String username, String password) throws IdentificationFailed {
        USER = Authenticator.identification(username, password);
        return connection;
    }
}
