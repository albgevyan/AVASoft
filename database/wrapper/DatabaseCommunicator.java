package wrapper;

import admin.Connection;
import exceptions.IdentificationFailed;

/** Creates a connection with the database
 * @author Albert Gevorgyan
 * @version 1.0
 */

public class DatabaseCommunicator{
    private Connection connection;
    public DatabaseCommunicator(String username, String password){
        try {
            this.connection = Connection.getConnection(username, password);
        } catch (IdentificationFailed e){
            System.out.println("Warning, the database system may be corrupt.");
            System.out.println(e.getMessage());
        }
    }
}