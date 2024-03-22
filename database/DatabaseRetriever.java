import java.sql.ResultSet;
import java.sql.SQLException;

/** Creates API for retrieving data from the database.
 * @author Albert Gevorgyan
 * @version 1.0
 */

public class DatabaseRetriever extends DatabaseCommunicator{

    private StringBuilder sql;
    
    public DatabaseRetriever(String dbURL) throws SQLException{
        super(dbURL);
    }

    /**
     * Executes the last executed query
     */

    public ResultSet executeLastQuery() throws SQLException{
        if (this.sql != null)
            return this.statement.executeQuery(this.sql.toString());
        return null;
    }

    /**
     * Gives all the rows from the specified table
     * @param name the name of the table
     * @return all the rows in the table
     * @throws SQLException
     */

    public ResultSet getTableData(String name) throws SQLException{
        this.sql = new StringBuilder("SELECT * FROM ");
        this.sql = sql.append(name);

        return this.statement.executeQuery(this.sql.toString());
    }

    /**
     * Gives all the rows from the specified table that contain the specified column values
     * @param name the name of the table
     * @param conditionColumns the names of the specified columns
     * @param conditionValues the demanded values od the values
     * @return all the rows satisfing the conditions
     * @throws SQLException
     */

    public ResultSet getTableData(String name, DataContainer conditionColumns, DataContainer conditionValues) throws SQLException{
        this.sql = new StringBuilder("SELECT * FROM  WHERE ");
        this.sql = sql.insert(14, name);
        this.sql = sql.append(conditionColumns.toConditionalColumnSet());

        for (int i = 0, index = 14; i < conditionValues.length(); i++) {
            for (; sql.charAt(index) != '='; index++);

            //
            // CORRECT THE BRACKETS
            //

            sql.insert(++index, "''");
            sql.insert(++index, conditionValues.valAt(i));
        }
        
        return this.statement.executeQuery(this.sql.toString());
    }

    /**
     * Gives all the values of the specified columns in the given table
     * @param name the name of the table
     * @param selectedColumns the names of the columns
     * @return all the values of the specified columns
     * @throws SQLException
     */
    
    public ResultSet getTableData(String name, DataContainer selectedColumns) throws SQLException{
        this.sql = new StringBuilder("SELECT  FROM ");
        this.sql.append(name);
        this.sql.insert(7, selectedColumns.toCommaSeparatedSet());
        return this.statement.executeQuery(this.sql.toString());
    }

    /**
     * Gives the values of the specified columns of rows, which contain separately specified column values.
     * @param name the name of the table
     * @param selectedColumns the names of the selected columns
     * @param conditionColumns the names of the speicified columns
     * @param conditionValues the values of the specified columns
     * @return the values of the demanded columns
     * @throws SQLException
     */
    
    public ResultSet getTableData(String name, DataContainer selectedColumns, DataContainer conditionColumns, DataContainer conditionValues) throws SQLException{
        this.sql = new StringBuilder("SELECT  FROM  WHERE ");
        this.sql.insert(13, name);
        this.sql.insert(7, selectedColumns.toCommaSeparatedSet());
        this.sql = sql.append(conditionColumns.toConditionalColumnSet());
    
        for (int i = 0, index = 20; i < conditionValues.length(); i++) {
            for (; sql.charAt(index) != '='; index++);
    
            //
            // CORRECT THE BRACKETS
            //
    
            sql.insert(++index, "''");
            sql.insert(++index, conditionValues.valAt(i));
        }

        return this.statement.executeQuery(this.sql.toString());
    }
}