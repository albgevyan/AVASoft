import java.sql.*;

/** Implements sql commands, providing a Java API
 * @author Albert Gevorgyan
 * @version 1.0
 */

public class DatabaseManipulator extends DatabaseCommunicator{
    private Connection connection;
    private Statement statement;
    private StringBuilder sql;

    public DatabaseManipulator(String dbURL) throws SQLException{

        this.connection = DriverManager.getConnection(dbURL);

        if (this.connection != null)
            System.out.println("Successfully connected.");
        else
            System.out.println("Failed to connect.");

        this.statement = this.connection.createStatement();
    }

    public void createSchema(){

    }

//    public void createTable(String tableName, String[] columns, String[] dtypes) throws SQLException {

    public void createTable(String tableName, DatabaseObjectCharacteristics[] params) throws SQLException {
        this.sql = new StringBuilder("CREATE TABLE ()");

        for (int i = 0; i < params.length; i++) {

            this.sql.append(params[i].toStringBuilder());

            if (i < params.length - 1)
                sql.append(",");
        }

        this.statement.executeQuery(sql.toString());
    }

    public void insertRow(String tableName, DataContainer data) throws SQLException{
        StringBuilder sql = new StringBuilder("INSERT INTO  VALUES()");
        sql.insert(12, tableName);
        sql.insert(data.toStringBuilder(sql.length()-1));

        this.statement.executeQuery(sql.toString());
    }

    public void getTableMetadata(String name){
        this.sql = "SELECT ANY FROM ";
        this.sql = sql.append(name);

        this.statement.executeQuery(this.sql);
    }

    public void directExecution(String sql) throws SQLException{
        this.statement.executeQuery(sql);
    }

//    public void append(String tableName){}
}
