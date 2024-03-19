import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

protected class DatabaseCommunicator{
    private Connection connection;
    private Statement statement;

    public DatabaseCommunicator(String dbURL){
        this.connection = DriverManager.getConnection(dbURL);

        if (this.connection != null)
            System.out.println("Successfully connected.");
        else
            System.out.println("Failed to connect.");

        this.statement = this.connection.createStatement();
    }
}