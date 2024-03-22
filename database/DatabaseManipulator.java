import java.sql.*;

/** Implements sql commands, providing a Java API
 * Warning! Be careful with sql injrctions!
 * @author Albert Gevorgyan
 * @version 1.0
 */

public class DatabaseManipulator extends DatabaseCommunicator{
    private StringBuilder sql;

    /**
     * 
     */

    public DatabaseManipulator(String dbURL) throws SQLException{
        super(dbURL);
    }

    /**
     * Creates table within the given schema.
     * @param tableName the name of the table
     * @param params the column parameters
     * @throws SQLException
     */

    public void createTable(String tableName, DatabaseObjectCharacteristics[] params) throws SQLException {
        this.sql = new StringBuilder("CREATE TABLE ()");

        for (int i = 0; i < params.length; i++) {

            this.sql.append(params[i].toStringBuilder());

            if (i < params.length - 1)
                sql.append(",");
        }

        this.statement.executeUpdate(sql.toString());
    }

    /**
     * Inserts a row into the specified table.
     * @param tableName the name of the table
     * @param data the corresponding column values
     * @throws SQLException
     */

    public void insertRow(String tableName, DataContainer data) throws SQLException{
        StringBuilder sql = new StringBuilder("INSERT INTO  VALUES()");
        sql.insert(12, tableName);
        sql.insert(sql.length()-1, data.toConditionalColumnSet());

        this.statement.executeUpdate(sql.toString());
    }
}
