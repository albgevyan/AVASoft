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

    public void createSchema(){

    }

//    public void createTable(String tableName, String[] columns, String[] dtypes) throws SQLException {

    public void createTable(String tableName, DatabaseObjectCharacteristics[] params) throws SQLException {
        StringBuilder sql = new StringBuilder("CREATE TABLE ");
        sql.append(tableName).append(" (");

        for (int i = 0; i < params.length;i++) {
            sql.append(params[i].toStringBuilder());
            if (i < params.length - 1)
                sql.append(",");
        }

        sql.append(")");

        this.statement.executeQuery(sql.toString());
    }

    public void insertRow(String tableName, DatabaseObjectCharacteristics[] params){
        StringBuilder sql = new StringBuilder("INSERT ");
    }

    public void directExecution(String sql) throws SQLException{
        this.statement.executeQuery(sql);
    }

//    public void append(String tableName){}
}
