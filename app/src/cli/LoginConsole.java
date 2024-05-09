package cli;

import exceptions.IdentificationFailed;
import wrapper.Connection;

public class LoginConsole {
    private static final String DB_CLIENT_USERNAME = "LOGIN_CLIENT";
    private static final String DB_CLIENT_PASSWORD = "GtY##183927";
    Connection connection;

    public LoginConsole() throws IdentificationFailed {

        try {
            this.connection = Connection.getConnection(DB_CLIENT_USERNAME, DB_CLIENT_PASSWORD);
        }
        catch (IdentificationFailed e){
            System.out.println("Unable to connect to database");
            System.out.println(e.getMessage());
        }
    }
}
