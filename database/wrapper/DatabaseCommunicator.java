package wrapper;

import core.DBUser;
import exceptions.DatabaseException;

import java.io.File;

/** Creates a connection with the database
 * @author Albert Gevorgyan
 * @version 1.0
 */

public class DatabaseCommunicator{
    protected static File connection;
    private static DBUser ADMIN = null;

    public DatabaseCommunicator(String username, String password, String dbDIR) throws DatabaseException{

        if (connection == null){

            connection = new File(dbDIR);
            if (connection.exists())
                System.out.println("Successfully connected.");

            connection = null;
            throw new DatabaseException();
        }
    }

    private void startUp(String username, String password){
//        this.ADMIN = File.
    }

    /**
     * Executes the provided SQL command. If you don't know SQL, avoid using this method. Does not work properly with all af the sql commands, may raise errors.
     * @param sql the command
     * @throws SQLException
     */

    // public ResultSet directExecution(String sql) throws SQLException {
    //     return this.statement.execute(sql);
    // }
}