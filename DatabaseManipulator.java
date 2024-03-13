import java.sql.*;

public class DatabaseManipulator{
    private Connection connection;
    private Statement statement;

    public DatabaseManipulator(String dbURL) throws SQLException{

        this.connection = DriverManager.getConnection(dbURL);

        if (this.connection != null)
            System.out.println("Successfully connected.");
        else
            System.out.println("Failed to connect.");

        this.statement = this.connection.createStatement();
    }

    public void createTable(String tableName) throws SQLException {
        String sql = "create table";

        this.statement.executeQuery(sql);
    }
}