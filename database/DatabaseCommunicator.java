import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/** Creates a connection with the database
 * @author Albert Gevorgyan
 * @version 1.0
 */

public class DatabaseCommunicator{
    protected static Connection connection;
    protected Statement statement;

    public DatabaseCommunicator(String dbURL) throws SQLException{

        if (connection == null){

            System.out.println("------------------\nWARNING!\n------------------\nBe careful with your input. Be careful of SQL injections. Avoid typing special characters(e.g. =, ', \") and SQL keywords(e.g. SELECT, ALTER, FROM, etc.)");
            connection = DriverManager.getConnection(dbURL);
    
            if (connection != null) {
                System.out.println("Successfully connected.");
                this.statement = connection.createStatement();
            }
            else
                System.out.println("Failed to connect.");
            
        }
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